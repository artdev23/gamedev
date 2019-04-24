package ru.geekbrains.screen;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.audio.BackgroundMusic;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.Ship;
import ru.geekbrains.sprite.Star;


public class GameScreen
		extends BaseScreen
{

  private TextureAtlas atlas;
  private BackgroundMusic bgmusic;
  private Texture bg;
  private Background background;
  private Star[] stars;

  private Texture txr;
  private Ship ship;
  private BulletPool bulPool;

  private EnemyShipPool enemyPool;
  private final float enemyInt = 4;
  private float enemyTimer;

  private static final int starCount = 128;


  @Override
  public void show()
  {
	super.show();

	atlas = new TextureAtlas("textures/mainAtlas.tpack");

	initGameObjects();

	bg = new Texture("textures/background.png");
	background = new Background(new TextureRegion(bg));
	bgmusic = new BackgroundMusic();
	bgmusic.play();
  }


  private void initGameObjects()
  {
	bulPool = new BulletPool();
	enemyPool = new EnemyShipPool();

	stars = new Star[starCount];
	for (int i = 0; i < starCount; i++)
	  stars[i] = new Star(atlas);

	txr = new Texture("textures/ship.png");
	TextureRegion reg = new TextureRegion(txr);
	AtlasRegion bulReg = atlas.findRegion("bulletMainShip");
	ship = new Ship(reg, bulReg, bulPool);
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
	freeAllDestroyedSprites();
	draw();
  }


  private void update(float delta)
  {
	for (Star s : stars) s.update(delta);

	ship.update(delta);
	bulPool.updateActiveSprites(delta);


	enemyTimer += delta;
	if (enemyTimer >= enemyInt)
	{
	  enemyTimer = 0;
	  createEnemy();
	}
	enemyPool.updateActiveSprites(delta);
  }


  private void freeAllDestroyedSprites()
  {
	bulPool.freeAllDestroyedActiveSprites();
	enemyPool.freeAllDestroyedActiveSprites();
  }


  private void draw()
  {
	batch.begin();

	background.draw(batch);
	for (Star s : stars) s.draw(batch);
	bulPool.drawActiveSprites(batch);
	ship.draw(batch);
	enemyPool.drawActiveSprites(batch);

	batch.end();
  }


  @Override
  public void dispose()
  {
	super.dispose();

	bg.dispose();
	bgmusic.dispose();
	atlas.dispose();
	txr.dispose();
	bulPool.dispose();
	enemyPool.dispose();
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


  private void createEnemy()
  {
	EnemyShip enemy = enemyPool.obtain();
	enemy.setPosition(worldBounds);
  }

}