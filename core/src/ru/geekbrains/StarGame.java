package ru.geekbrains;


import com.badlogic.gdx.Game;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.screen.MenuScreen;


public class StarGame
		extends Game
{



  @Override
  public void create()
  {
	BaseScreen scr = new MenuScreen(this);
	setScreen(scr);
  }

}