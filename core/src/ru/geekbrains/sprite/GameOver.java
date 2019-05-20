package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;


public class GameOver
		extends Sprite
{

  private static final float HEIGHT = 0.1f;


  public GameOver(TextureAtlas atlas)
  {
	super(atlas.findRegion("message_game_over"));
	setHeightProportion(HEIGHT);
  }

}