package ru.geekbrains.screen;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.audio.BackgroundMusic;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyGenerator;

import static com.badlogic.gdx.Gdx.audio;
import static com.badlogic.gdx.Gdx.files;


public class GameScreen
		extends BaseScreen
{

  private State state;

  private TextureAtlas atlas;
  private BackgroundMusic bgmusic;
  private Texture bg;
  private Background background;
  private Star[] stars;

  private Texture txr;
  private MainShip ship;

  private BulletPool bulletPool;
  private EnemyShipPool enemyPool;
  private ExplosionPool explosionPool;
  private EnemyGenerator enemyGenerator;

  private Sound bulletSound;
  private Sound explosionSound;

  private static final int starCount = 128;


  @Override
  public void show()
  {
	super.show();

	atlas = new TextureAtlas("textures/mainAtlas.tpack");

	initGameObjects();

	bg = new Texture("textures/background.png");
	background = new Background(new TextureRegion(bg));

	stars = new Star[starCount];
	for (int i = 0; i < starCount; i++)
	  stars[i] = new Star(atlas);

	bgmusic = new BackgroundMusic();
	bgmusic.play();

	state = State.PLAYING;
  }


  private void initGameObjects()
  {
	txr = new Texture("textures/ship.png");
	TextureRegion reg = new TextureRegion(txr);

	AtlasRegion bulReg = atlas.findRegion("bulletMainShip");

	bulletSound = audio.newSound(files.internal("audio/laser.wav"));
	explosionSound = audio.newSound(files.internal("audio/explosion.wav"));

	bulletPool = new BulletPool();
	explosionPool = new ExplosionPool(atlas, explosionSound);

	ship = new MainShip(reg, bulReg, bulletPool, bulletSound, explosionPool);

	enemyPool = new EnemyShipPool(bulletPool, bulletSound, explosionPool, worldBounds, ship);
	enemyGenerator = new EnemyGenerator(atlas, enemyPool, worldBounds);
  }


  @Override
  public void render(float delta)
  {
	super.render(delta);

	update(delta);
	checkCollisions();
	freeAllDestroyedSprites();
	draw();
  }


  private void update(float delta)
  {
	for (Star s : stars) s.update(delta);

	explosionPool.updateActiveSprites(delta);

	if (state == State.PLAYING)
	{
	  ship.update(delta);
	  enemyGenerator.generate(delta);
	  bulletPool.updateActiveSprites(delta);
	  enemyPool.updateActiveSprites(delta);
	}
  }


  private void draw()
  {
	batch.begin();

	background.draw(batch);
	for (Star s : stars) s.draw(batch);

	if (state == State.PLAYING)
	{
	  bulletPool.drawActiveSprites(batch);
	  ship.draw(batch);
	  enemyPool.drawActiveSprites(batch);
	}

	explosionPool.drawActiveSprites(batch);

	batch.end();
  }


  private void checkCollisions()
  {
	if (state != State.PLAYING) return;
	if (isGameOver()) return;

	checkHitsInEnemies();

	checkHitsInShip();
	if (isGameOver()) return;

	checkCollisionsShips();
  }


  private void checkHitsInEnemies()
  {
	List<Bullet> bullets = bulletPool.getActiveObjects();
	List<EnemyShip> enemies = enemyPool.getActiveObjects();

	for (Bullet b : bullets)
	{
	  if (b.getOwner() != ship) continue;

	  for (EnemyShip e : enemies)
		if (!e.isDestroyed() && !e.isOutside(b))
		{
		  b.destroy();
		  e.damage(b);
		  break;
		}
	}
  }


  private void checkHitsInShip()
  {
	List<Bullet> bullets = bulletPool.getActiveObjects();

	for (Bullet b : bullets)
	{
	  if (b.getOwner() == ship) continue;
	  if (ship.isDestroyed()) break;

	  if (!ship.isOutside(b))
	  {
		b.destroy();
		ship.damage(b);
	  }
	}
  }


  private void checkCollisionsShips()
  {
	double minDist = ship.getDiagLen() - 0.04f;

	List<EnemyShip> enemies = enemyPool.getActiveObjects();

	for (EnemyShip e : enemies)
	{
	  if (ship.isDestroyed()) break;

	  if (ship.pos.dst(e.pos) < minDist)
	  {
		e.boom();
		e.destroy();
		ship.damage(e);
	  }
	}
  }


  private boolean isGameOver()
  {
	if (ship.isDestroyed())
	  state = State.GAME_OVER;

	return state == State.GAME_OVER;
  }


  private void freeAllDestroyedSprites()
  {
	bulletPool.freeAllDestroyedActiveSprites();
	enemyPool.freeAllDestroyedActiveSprites();
	explosionPool.freeAllDestroyedActiveSprites();
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);

	background.resize(worldBounds);
	for (Star s : stars) s.resize(worldBounds);

	if (state == State.PLAYING)
	  ship.resize(worldBounds);
  }


  @Override
  public void pause()
  {
	super.pause();

	if (state == State.PLAYING)
	  state = State.PAUSE;
  }


  @Override
  public void resume()
  {
	super.resume();

	if (state == State.PAUSE)
	  state = State.PLAYING;
  }


  @Override
  public void dispose()
  {
	super.dispose();

	bg.dispose();
	bgmusic.dispose();
	atlas.dispose();
	txr.dispose();
	bulletPool.dispose();
	enemyPool.dispose();
	explosionPool.dispose();
	enemyGenerator.dispose();
	bulletSound.dispose();
	explosionSound.dispose();
  }


  @Override
  public boolean keyDown(int keycode)
  {
	if (state != State.PLAYING) return false;

	return ship.keyDown(keycode);
  }


  @Override
  public boolean keyUp(int keycode)
  {
	if (state != State.PLAYING) return false;

	return ship.keyUp(keycode);
  }


  @Override
  public boolean touchDown(Vector2 touch, int pointer)
  {
	if (state != State.PLAYING) return false;

	return ship.touchDown(touch, pointer);
  }


  @Override
  public boolean touchUp(Vector2 touch, int pointer)
  {
	if (state != State.PLAYING) return false;

	return ship.touchUp(touch, pointer);
  }


  private enum State
  {
	PLAYING,
	PAUSE,
	GAME_OVER
  }

}