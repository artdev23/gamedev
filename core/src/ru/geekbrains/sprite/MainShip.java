package ru.geekbrains.sprite;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;


public class MainShip
		extends Ship
{

  private boolean pressedRight;
  private boolean pressedLeft;
  private int rightPointer = INVALID_POINTER;
  private int leftPointer = INVALID_POINTER;

  private static final float HEIGHT = 0.1f;
  private static final float BULLET_HEIGHT = 0.005f;
  private static final int enemyCollisionDamage = 50;
  private static final int HP = 100;
  private static final int INVALID_POINTER = -1;


  public MainShip(TextureAtlas atlasShips, TextureRegion bulletRegion, BulletPool bulletPool,
				  Sound shootSound, ExplosionPool explosionPool)
  {
	super(atlasShips.findRegion("ship"));

	this.bulletPool = bulletPool;
	this.bulletRegion = bulletRegion;
	this.shootSound = shootSound;
	this.explosionPool = explosionPool;
	bulletHeight = BULLET_HEIGHT;
	bulletV.set(0, 0.5f);
	reloadInterval = 0.1f;
	damage = 1;
	hp = HP;
	setHeightProportion(HEIGHT);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);

	setHeightProportion(HEIGHT);
	setBottom(worldBounds.getBottom() + 0.05f);
  }


  @Override
  public void update(float delta)
  {
	super.update(delta);

	reloadTimer += delta;
	if (reloadTimer >= reloadInterval)
	{
	  reloadTimer = 0f;
	  shoot();
	}

	if (getRight() > worldBounds.getRight())
	{
	  setRight(worldBounds.getRight());
	  stop();
	}

	if (getLeft() < worldBounds.getLeft())
	{
	  setLeft(worldBounds.getLeft());
	  stop();
	}
  }


  public boolean keyDown(int keycode)
  {
	switch (keycode)
	{
	  case Keys.A:
	  case Keys.LEFT:
		pressedLeft = true;
		moveLeft();
		break;

	  case Keys.D:
	  case Keys.RIGHT:
		pressedRight = true;
		moveRight();
		break;

//	  case Keys.UP:
//		shoot();
//		break;
	}

	return false;
  }


  public boolean keyUp(int keycode)
  {
	switch (keycode)
	{
	  case Keys.A:
	  case Keys.LEFT:
	  {
		pressedLeft = false;

		if (pressedRight)
		  moveRight();
		else
		  stop();

		break;
	  }

	  case Keys.D:
	  case Keys.RIGHT:
	  {
		pressedRight = false;

		if (pressedLeft)
		  moveLeft();
		else
		  stop();

		break;
	  }
	}

	return false;
  }


  @Override
  public boolean touchDown(Vector2 touch, int pointer)
  {
	if (touch.x < worldBounds.pos.x)
	{
	  if (leftPointer != INVALID_POINTER) return false;
	  leftPointer = pointer;
	  moveLeft();
	}
	else
	{
	  if (rightPointer != INVALID_POINTER) return false;
	  rightPointer = pointer;
	  moveRight();
	}

	return false;
  }


  @Override
  public boolean touchUp(Vector2 touch, int pointer)
  {
	if (pointer == leftPointer)
	{
	  leftPointer = INVALID_POINTER;

	  if (rightPointer != INVALID_POINTER)
		moveRight();
	  else
		stop();
	}
	else if (pointer == rightPointer)
	{
	  rightPointer = INVALID_POINTER;

	  if (leftPointer != INVALID_POINTER)
		moveLeft();
	  else
		stop();
	}

	return false;
  }


  private void moveRight()
  {
	v.set(0.5f, 0);
  }


  private void moveLeft()
  {
	v.set(-0.5f, 0);
  }


  private void stop()
  {
	v.setZero();
  }


  public void damage(EnemyShip e)
  {
	hp -= enemyCollisionDamage;
	checkHP();
  }


  public void reset()
  {
	stop();
	flushDestroy();
	hp = HP;
	pos.x = worldBounds.pos.x;
  }

}