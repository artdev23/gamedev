package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.ScaledTouchUpButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.utils.Font;

import static com.badlogic.gdx.Gdx.app;


public class ExitButton
		extends ScaledTouchUpButton
{

  private static final float height = 0.2f;
  private static final float FontSize = 0.04f;


  public ExitButton(BaseScreen scr, Texture txr)
  {
	super(new TextureRegion(txr), scr);

	text = "QUIT";
	font = new Font("font/font.fnt", "font/font.png");
	font.setFontSize(FontSize);
	setHeightProportion(height);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);
  }


  @Override
  protected void action()
  {
	app.exit();
  }


}