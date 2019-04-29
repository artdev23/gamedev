package ru.geekbrains.utils;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.sprite.EnemyShip;

import static ru.geekbrains.math.Rnd.nextFloat;


public class EnemyGenerator
{

  private final Texture txrSmall = new Texture("textures/enemy1.png");
  private final TextureRegion[] enemySmallRegion = {new TextureRegion(txrSmall)};

  private final TextureRegion[] enemyMediumRegion;
  private final TextureRegion[] enemyBigRegion;

  private final Vector2 enemySmallV = new Vector2(0, -0.2f);
  private final Vector2 enemyMediumV = new Vector2(0, -0.03f);
  private final Vector2 enemyBigV = new Vector2(0, -0.005f);

  private final TextureRegion bulletRegion;

  private final EnemyShipPool enemyPool;

  private static final float ENEMY_SMALL_HEIGHT = 0.1f;
  private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
  private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
  private static final int ENEMY_SMALL_DAMAGE = 10;
  private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
  private static final int ENEMY_SMALL_HP = 100;

  private static final float ENEMY_MEDIUM_HEIGHT = 0.1f;
  private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
  private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
  private static final int ENEMY_MEDIUM_DAMAGE = 5;
  private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
  private static final int ENEMY_MEDIUM_HP = 5;

  private static final float ENEMY_BIG_HEIGHT = 0.2f;
  private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
  private static final float ENEMY_BIG_BULLET_VY = -0.3f;
  private static final int ENEMY_BIG_DAMAGE = 10;
  private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
  private static final int ENEMY_BIG_HP = 10;

  private Rect worldBounds;

  private float generateInterval = 4;
  private float timer;


  public EnemyGenerator(TextureAtlas atlas, EnemyShipPool enemyPool, Rect worldBounds)
  {
//	TextureRegion enemy0 = atlas.findRegion("enemy0");
//	this.enemySmallRegion = Regions.split(enemy0, 1, 2, 2);
	TextureRegion enemy1 = atlas.findRegion("enemy1");
	this.enemyMediumRegion = Regions.split(enemy1, 1, 2, 2);
	TextureRegion enemy2 = atlas.findRegion("enemy2");
	this.enemyBigRegion = Regions.split(enemy2, 1, 2, 2);

	this.bulletRegion = atlas.findRegion("bulletEnemy");
	this.enemyPool = enemyPool;
	this.worldBounds = worldBounds;
  }


  public void generate(float delta)
  {
	timer += delta;
	if (timer >= generateInterval)
	{
	  timer = 0;
	  createEnemy();
	}
  }


  private void createEnemy()
  {
	EnemyShip enemy = enemyPool.obtain();
	enemy.pos.x = nextFloat(worldBounds.getLeft(), worldBounds.getRight());
	enemy.setBottom(worldBounds.getTop());
	enemy.set(
			enemySmallRegion,
			enemySmallV,
			bulletRegion,
			ENEMY_SMALL_BULLET_HEIGHT,
			ENEMY_SMALL_BULLET_VY,
			ENEMY_SMALL_DAMAGE,
			ENEMY_SMALL_RELOAD_INTERVAL,
			ENEMY_SMALL_HEIGHT,
			ENEMY_SMALL_HP
	);
  }


  public void dispose()
  {
	txrSmall.dispose();
  }

}