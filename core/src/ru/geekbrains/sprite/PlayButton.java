package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.ScaledTouchUpButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.utils.Font;


public class PlayButton
		extends ScaledTouchUpButton
{

  private StarGame game;
  private static final float height = 0.2f;
  private static final float FontSize = 0.03f;


  public PlayButton(BaseScreen scr, Texture txr, StarGame game)
  {
	super(new TextureRegion(txr), scr);

	this.game = game;
	text = "PLAY GAME";
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
	game.switchToGame();
  }

}