package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.Texture;

import ru.geekbrains.base.Background;


public class MenuBackground
		extends Background
{

  private static final Texture img = new Texture("textures/menuBg.jpg");


  public MenuBackground()
  {
	super(img);
  }


  @Override
  public void dispose()
  {
	img.dispose();
  }

}