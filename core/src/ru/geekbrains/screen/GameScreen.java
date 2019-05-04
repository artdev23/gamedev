package ru.geekbrains.screen;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.GameBackground;
import ru.geekbrains.sprite.HealthBar;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.utils.EnemyGenerator;
import ru.geekbrains.utils.Font;

import static com.badlogic.gdx.Gdx.audio;
import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.Input.Keys.ENTER;
import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static com.badlogic.gdx.utils.Align.center;


public class GameScreen
		extends BaseScreen
{

  private State state;

  private final TextureAtlas atlas;
  private final TextureAtlas atlasShips;
  private final GameBackground background;
  private final Font font;
  private final Font fontGameOver;

  private MainShip ship;
  private HealthBar hp;
  private BulletPool bulletPool;
  private EnemyShipPool enemyPool;
  private ExplosionPool explosionPool;
  private EnemyGenerator enemyGenerator;

  private Sound bulletSound;
  private Sound explosionSound;

  private static final float FontSize = 0.015f;
  private static final String GAME_OVER = "GAME\nOVER";
  private static final String PRESS_ENTER = "PRESS ENTER";


  public GameScreen(StarGame game)
  {
	super(game);

	atlas = new TextureAtlas("textures/mainAtlas.tpack");
	atlasShips = new TextureAtlas("textures/shipsAtlas.pack");
	background = new GameBackground(atlas);

	font = new Font("font/font.fnt", "font/font.png");
	font.setFontSize(FontSize);

	fontGameOver = new Font("font/font.fnt", "font/font.png");
	fontGameOver.setFontSize(0.1f);
	fontGameOver.setColor(new Color(0.8f, 0.8f, 0.1f, 1));

	initGameObjects();
  }


  private void initGameObjects()
  {
	AtlasRegion bulReg = atlas.findRegion("bulletMainShip");
	AtlasRegion bulEnemyReg = atlas.findRegion("bulletEnemy");
	bulletSound = audio.newSound(files.internal("audio/laser.wav"));
	explosionSound = audio.newSound(files.internal("audio/explosion.wav"));

	bulletPool = new BulletPool();
	explosionPool = new ExplosionPool(atlas, explosionSound);
	enemyPool = new EnemyShipPool(bulletPool, bulletSound, explosionPool, worldBounds);
	enemyGenerator = new EnemyGenerator(atlasShips, bulEnemyReg, enemyPool, worldBounds);

	ship = new MainShip(atlasShips, bulReg, bulletPool, bulletSound, explosionPool);
	hp = new HealthBar(this, ship.getHp());
  }


  @Override
  public void dispose()
  {
	super.dispose();

	atlas.dispose();
	atlasShips.dispose();
	background.dispose();
	font.dispose();
	fontGameOver.dispose();
	bulletSound.dispose();
	explosionSound.dispose();
	bulletPool.dispose();
	explosionPool.dispose();
	enemyPool.dispose();
  }


  @Override
  public void show()
  {
	super.show();

	hp.setTop(worldBounds.getTop() - 0.004f);
	hp.setAlignHoriz(center);

	state = State.PLAYING;
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
	background.update(delta);

	explosionPool.updateActiveSprites(delta);

	if (state == State.PLAYING)
	{
	  ship.update(delta);
	  hp.update(delta, ship.getHp());
	  enemyGenerator.generate(delta);
	  bulletPool.updateActiveSprites(delta);
	  enemyPool.updateActiveSprites(delta);
	}
  }


  private void draw()
  {
	batch.begin();

	background.draw(batch);

	if (state == State.PLAYING)
	{
	  bulletPool.drawActiveSprites(batch);
	  ship.draw(batch);
	  hp.draw(batch);
	  enemyPool.drawActiveSprites(batch);
	}
	else if (state == State.GAME_OVER)
	{
	  float x = worldBounds.pos.x;
	  float y = worldBounds.pos.y + fontGameOver.getXHeight() * 2;
	  fontGameOver.draw(batch, GAME_OVER, x, y, center);

	  y = worldBounds.getBottom() + 0.1f;
	  font.draw(batch, PRESS_ENTER, x, y, center);
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

	if (state == State.PLAYING)
	  ship.resize(worldBounds);
  }


  @Override
  public void pause()
  {
	super.pause();

	if (state == State.PLAYING)
	{
	  state = State.PAUSE;
	  game.switchToMenu();
	}
  }


  @Override
  public void resume()
  {
	super.resume();

	if (state == State.PAUSE)
	  state = State.PLAYING;
  }


  @Override
  public boolean keyDown(int keycode)
  {
	if (state == State.PLAYING)
	  switch (keycode)
	  {
		case ESCAPE:
		  pause();
		  break;

		default:
		  ship.keyDown(keycode);
	  }
	else if (state == State.GAME_OVER)
	{
	  if (keycode == ENTER)
		reset();
	}

	return false;
  }


  @Override
  public boolean keyUp(int keycode)
  {
	if (state == State.PLAYING)
	  ship.keyUp(keycode);

	return false;
  }


  @Override
  public boolean touchDown(Vector2 touch, int pointer)
  {
	if (state == State.PLAYING)
	  ship.touchDown(touch, pointer);

	return false;
  }


  @Override
  public boolean touchUp(Vector2 touch, int pointer)
  {
	if (state == State.PLAYING)
	  ship.touchUp(touch, pointer);

	return false;
  }


  public void reset()
  {
	ship.reset();

	bulletPool.freeAllActiveSprites();
	enemyPool.freeAllActiveSprites();
	explosionPool.freeAllActiveSprites();

	state = State.PLAYING;
  }


  private enum State
  {
	PLAYING,
	PAUSE,
	GAME_OVER
  }

}