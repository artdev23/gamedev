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

  private TextureAtlas atlas;
  private BackgroundMusic bgmusic;
  private Texture bg;
  private Background background;
  private Star[] stars;
  private Background game_over;

  private Texture txr;
  private MainShip ship;

  private BulletPool bulletPool;
  private EnemyShipPool enemyPool;
  private EnemyGenerator enemyGenerator;

  private Sound bulletSound;
  private boolean gameOver = false;

  private static final int starCount = 128;


  @Override
  public void show()
  {
	super.show();

	atlas = new TextureAtlas("textures/mainAtlas.tpack");

	game_over = new Background(atlas.findRegion("message_game_over"));

	initGameObjects();

	bg = new Texture("textures/background.png");
	background = new Background(new TextureRegion(bg));
	bgmusic = new BackgroundMusic();
	bgmusic.play();
  }


  private void initGameObjects()
  {
	bulletPool = new BulletPool();

	stars = new Star[starCount];
	for (int i = 0; i < starCount; i++)
	  stars[i] = new Star(atlas);

	txr = new Texture("textures/ship.png");
	TextureRegion reg = new TextureRegion(txr);
	AtlasRegion bulReg = atlas.findRegion("bulletMainShip");
	bulletSound = audio.newSound(files.internal("audio/laser.wav"));

	ship = new MainShip(reg, bulReg, bulletPool, bulletSound);

	enemyPool = new EnemyShipPool(bulletPool, bulletSound, worldBounds, ship);
	enemyGenerator = new EnemyGenerator(atlas, enemyPool, worldBounds);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);

	background.resize(worldBounds);
	for (Star s : stars) s.resize(worldBounds);
	ship.resize(worldBounds);
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


  private void checkCollisions()
  {
	List<Bullet> bullets = bulletPool.getActiveObjects();
	List<EnemyShip> enemies = enemyPool.getActiveObjects();

	for (EnemyShip e : enemies)
	{
	  for (Bullet b : bullets)
		checkCollisionsBullets(e, b);

	  checkCollisionsShips(e);
	}
  }


  private void checkCollisionsShips(EnemyShip e)
  {
	if (ship.distance(e) < ship.getDiagLen() - 0.04f)
	{
	  e.destroy();
	  ship.damage(e);
	}
  }


  private void checkCollisionsBullets(EnemyShip e, Bullet b)
  {
	if (b.getOwner() == e)
	{
	  if (!ship.isOutside(b))
	  {
		b.destroy();
		ship.damage(b);
	  }
	}
	else if (b.getOwner() == ship)
	{
	  if (!e.isOutside(b))
	  {
		b.destroy();
		e.damage(b);
	  }
	}
  }


  private void update(float delta)
  {
	for (Star s : stars) s.update(delta);

	if (ship.isDestroyed())
	{
	  gameOver();
	  return;
	}

	ship.update(delta);
	bulletPool.updateActiveSprites(delta);

	enemyGenerator.generate(delta);
	enemyPool.updateActiveSprites(delta);
  }


  private void freeAllDestroyedSprites()
  {
	bulletPool.freeAllDestroyedActiveSprites();
	enemyPool.freeAllDestroyedActiveSprites();
  }


  private void draw()
  {
	batch.begin();

	background.draw(batch);
	for (Star s : stars) s.draw(batch);

	if (gameOver)
	{
//	  game_over.draw(batch);
	}
	else
	{
	  bulletPool.drawActiveSprites(batch);
	  ship.draw(batch);
	  enemyPool.drawActiveSprites(batch);
	}

	batch.end();
  }


  @Override
  public void dispose()
  {
	super.dispose();

	bg.dispose();
	bgmusic.dispose();
	bulletSound.dispose();
	atlas.dispose();
	txr.dispose();
	bulletPool.dispose();
	enemyPool.dispose();
	enemyGenerator.dispose();
  }


  @Override
  public boolean keyDown(int keycode)
  {
	return ship.keyDown(keycode);
  }


  @Override
  public boolean keyUp(int keycode)
  {
	return ship.keyUp(keycode);
  }


  @Override
  public boolean touchDown(Vector2 touch, int pointer)
  {
	return ship.touchDown(touch, pointer);
  }


  @Override
  public boolean touchUp(Vector2 touch, int pointer)
  {
	return ship.touchUp(touch, pointer);
  }


  private void gameOver()
  {
	gameOver = true;
  }

}