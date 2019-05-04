package ru.geekbrains.base;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.math.Rect;


public class Background
		extends Sprite
{

  public Background(Texture bgImage)
  {
	super(new TextureRegion(bgImage));
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


  public void dispose()
  {
  }

}