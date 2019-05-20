package ru.geekbrains.pool;


import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;


public class EnemyShipPool
		extends SpritesPool<EnemyShip>
{

  private Rect worldBounds;
  private BulletPool bulletPool;
  private Sound shootSound;
  private ExplosionPool explosionPool;


  public EnemyShipPool(BulletPool bulletPool, Sound shootSound,
					   ExplosionPool explosionPool,
					   Rect worldBounds)
  {
	this.bulletPool = bulletPool;
	this.shootSound = shootSound;
	this.explosionPool = explosionPool;
	this.worldBounds = worldBounds;
  }


  @Override
  protected EnemyShip newObject()
  {
	EnemyShip ship = new EnemyShip(worldBounds, bulletPool, shootSound, explosionPool);

	return ship;
  }

}