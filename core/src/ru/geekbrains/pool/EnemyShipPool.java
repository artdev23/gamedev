package ru.geekbrains.pool;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.EnemyShip;


public class EnemyShipPool
		extends SpritesPool<EnemyShip>
{

  private Texture txr = new Texture("textures/enemy1.png");
  private TextureRegion reg = new TextureRegion(txr);


  @Override
  protected EnemyShip newObject()
  {
	EnemyShip ship = new EnemyShip(reg);

	return ship;
  }


  @Override
  public void dispose()
  {
	super.dispose();

	txr.dispose();
  }

}