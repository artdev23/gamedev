package ru.geekbrains.base;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Explosion;


public class Ship
		extends Sprite
{

  protected Sound shootSound;
  protected Rect worldBounds;
  protected Vector2 v = new Vector2();
  protected Vector2 v0 = new Vector2();
  protected BulletPool bulletPool;
  protected ExplosionPool explosionPool;
  protected TextureRegion bulletRegion;
  protected Vector2 bulletV = new Vector2();
  protected float bulletHeight;

  protected int damage;


  public int getHp()
  {
	return hp;
  }


  protected int hp;
  protected float reloadInterval;
  protected float reloadTimer;
  protected float damageAnimateInterval = 0.1f;
  protected float damageAnimateTimer = damageAnimateInterval;


  public Ship(TextureRegion region, int rows, int cols, int frames)
  {
	super(region, rows, cols, frames);
  }


  public Ship()
  {
  }


  public Ship(TextureRegion region)
  {
	super(region);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);
	this.worldBounds = worldBounds;
  }


  @Override
  public void update(float delta)
  {
	super.update(delta);

	pos.mulAdd(v, delta);

//	damageAnimateTimer += delta;
//	if (damageAnimateTimer >= damageAnimateInterval)
//	  frame = 0;
  }


  public void shoot()
  {
	Bullet bullet = bulletPool.obtain();
	bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);

	long id = shootSound.play();
	shootSound.setVolume(id, 0.1f);
  }


  public void damage(Bullet bullet)
  {
//	frame = 1;
//	damageAnimateTimer = 0;

	hp -= bullet.getDamage();
	checkHP();
  }


  protected void checkHP()
  {
	if (hp <= 0)
	{
	  boom();
	  destroy();
	}
  }


  @Override
  public void destroy()
  {
	super.destroy();
	hp = 0;
  }


  public void boom()
  {
	Explosion explosion = explosionPool.obtain();
	explosion.set(getHeight(), pos);
  }

}