package ru.geekbrains.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.geekbrains.StarGame;


public class DesktopLauncher
{

  public static void main(String[] arg)
  {
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	config.width = 1280;
	config.height = 720;
	config.x = 80;
	config.y = 0;
	new LwjglApplication(new StarGame(), config);
  }

}
