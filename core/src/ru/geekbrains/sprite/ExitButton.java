package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledTouchUpButton;
import ru.geekbrains.math.Rect;

import static com.badlogic.gdx.Gdx.app;


public class ExitButton
		extends ScaledTouchUpButton
{

  private static final float height = 0.15f;
  private static final float MARGIN = 0.02f;


  public ExitButton(TextureAtlas atlas)
  {
	super(atlas.findRegion("btExit"));
	setHeightProportion(height);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);
	setBottom(worldBounds.getBottom() + MARGIN);
	setRight(worldBounds.getRight() - MARGIN);
  }


  @Override
  protected void action()
  {
	app.exit();
  }


}