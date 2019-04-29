package ru.geekbrains.pool;


import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.MainShip;


public class EnemyShipPool
		extends SpritesPool<EnemyShip>
{

  private Rect worldBounds;
  private BulletPool bulletPool;
  private Sound shootSound;
  private ExplosionPool explosionPool;
  private MainShip mainShip;


  public EnemyShipPool(BulletPool bulletPool, Sound shootSound, ExplosionPool explosionPool,
					   Rect worldBounds, MainShip mainShip)
  {
	this.bulletPool = bulletPool;
	this.shootSound = shootSound;
	this.explosionPool = explosionPool;
	this.worldBounds = worldBounds;
	this.mainShip = mainShip;
  }


  @Override
  protected EnemyShip newObject()
  {
	EnemyShip ship = new EnemyShip(mainShip, worldBounds, bulletPool, shootSound, explosionPool);

	return ship;
  }

}