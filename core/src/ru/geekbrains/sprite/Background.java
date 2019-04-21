package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;


public class Background
		extends Sprite
{

  public Background(TextureRegion region)
  {
	super(region);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	float w = worldBounds.getWidth();
	float h = worldBounds.getHeight();
	setHeightProportion(h);

	boolean aspectLess = (getWidth() / getHeight()) < (w / h);

	if (aspectLess)
	  setWidthProportion(w);

	pos.set(worldBounds.pos);
  }


  @Override
  public void draw(SpriteBatch batch)
  {
	batch.disableBlending();
	super.draw(batch);
	batch.enableBlending();
  }

}