package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

import static ru.geekbrains.math.Rnd.nextFloat;


public class EnemyShip
		extends Sprite
{

  private final Vector2 v = new Vector2(0, -0.5f);
  private Rect worldBounds;

  private static final float HEIGHT = 0.2f;


  public EnemyShip(TextureRegion region)
  {
	super(region);

	setHeightProportion(HEIGHT);
  }


  public void setPosition(Rect worldBounds)
  {
	this.worldBounds = worldBounds;

	setBottom(worldBounds.getBottom() + 0.05f);
	float posX = nextFloat(worldBounds.getLeft(), worldBounds.getRight() - getWidth());
	setLeft(posX);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);

	setHeightProportion(HEIGHT);
  }


  @Override
  public void update(float delta)
  {
	super.update(delta);

	pos.mulAdd(v, delta);

	if (isOutside(worldBounds))
	  destroy();
  }

}