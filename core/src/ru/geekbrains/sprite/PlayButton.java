package ru.geekbrains.sprite;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.ScaledTouchUpButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;


public class PlayButton
		extends ScaledTouchUpButton
{

  private Game game;
  private static final float height = 0.2f;
  private static final float MARGIN = 0.02f;


  public PlayButton(TextureAtlas atlas, Game game)
  {
	super(atlas.findRegion("btPlay"));

	this.game = game;
	setHeightProportion(height);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);
	setBottom(worldBounds.getBottom() + MARGIN);
	setLeft(worldBounds.getLeft() + MARGIN);
  }


  @Override
  protected void action()
  {
	BaseScreen screen = new GameScreen();
	game.setScreen(screen);
  }

}