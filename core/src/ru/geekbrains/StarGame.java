package ru.geekbrains;


import com.badlogic.gdx.Game;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.screen.MenuScreen;


public class StarGame
		extends Game
{

  private String name;
  private BaseScreen menu;
  private BaseScreen game;


  @Override
  public void create()
  {
	name = "Space Invaders";
	menu = new MenuScreen(this);
	game = new GameScreen(this);

	switchToMenu();
  }


  public void switchToMenu()
  {
	setScreen(menu);
  }


  public void switchToGame()
  {
	setScreen(game);
  }


  public String getName()
  {
	return name;
  }

}