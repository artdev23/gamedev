package ru.geekbrains.utils;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.sprite.EnemyShip;

import static java.lang.Math.random;
import static ru.geekbrains.math.Rnd.nextFloat;


public class EnemyGenerator
{

  private final EnemyShipPool enemyPool;
  private final TextureRegion bulletRegion;
  private Rect worldBounds;
  private float generateInterval = 4;
  private float timer;

  private static final EnemyParams enemy1 = new EnemyParams();
  private static final EnemyParams enemy2 = new EnemyParams();
  private static final EnemyParams enemy3 = new EnemyParams();
  private static final EnemyParams enemy4 = new EnemyParams();


  static
  {
	enemy1.V = new Vector2(0, -0.15f);
	enemy1.BULLET_VY = -0.2f;
	enemy1.HEIGHT = 0.08f;
	enemy1.BULLET_HEIGHT = 0.005f;
	enemy1.RELOAD_INTERVAL = 2;
	enemy1.DAMAGE = 4;
	enemy1.HP = 4;

	enemy2.V = new Vector2(0, -0.1f);
	enemy2.BULLET_VY = -0.25f;
	enemy2.HEIGHT = 0.10f;
	enemy2.BULLET_HEIGHT = 0.008f;
	enemy2.RELOAD_INTERVAL = 1;
	enemy2.DAMAGE = 8;
	enemy2.HP = 8;

	enemy3.V = new Vector2(0, -0.08f);
	enemy3.BULLET_VY = -0.3f;
	enemy3.HEIGHT = 0.15f;
	enemy3.BULLET_HEIGHT = 0.01f;
	enemy3.RELOAD_INTERVAL = 0.5f;
	enemy3.DAMAGE = 16;
	enemy3.HP = 16;

	enemy4.V = new Vector2(0, -0.05f);
	enemy4.BULLET_VY = -0.4f;
	enemy4.HEIGHT = 0.2f;
	enemy4.BULLET_HEIGHT = 0.02f;
	enemy4.RELOAD_INTERVAL = 0.2f;
	enemy4.DAMAGE = 32;
	enemy4.HP = 32;
  }


  public EnemyGenerator(TextureAtlas atlas, TextureRegion bulletRegion, EnemyShipPool enemyPool,
						Rect worldBounds)
  {
	enemy1.Region = atlas.findRegion("enemy1");
	enemy2.Region = atlas.findRegion("enemy2");
	enemy3.Region = atlas.findRegion("enemy3");
	enemy4.Region = atlas.findRegion("enemy4");

	this.bulletRegion = bulletRegion;
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

	double type = random();
	if (type < 0.5)
	  enemy.set(bulletRegion, enemy1);
	else if (type < 0.75)
	  enemy.set(bulletRegion, enemy2);
	else if (type < 0.88)
	  enemy.set(bulletRegion, enemy3);
	else
	  enemy.set(bulletRegion, enemy4);

	enemy.pos.x = nextFloat(worldBounds.getLeft(), worldBounds.getRight());
	enemy.setBottom(worldBounds.getTop());
  }


  public static class EnemyParams
  {

	public TextureRegion Region;
	public Vector2 V;
	public float HEIGHT;
	public float BULLET_VY;
	public float BULLET_HEIGHT;
	public float RELOAD_INTERVAL;
	public int HP;
	public int DAMAGE;
  }

}