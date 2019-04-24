package ru.geekbrains.audio;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import static com.badlogic.gdx.Gdx.audio;
import static com.badlogic.gdx.Gdx.files;


public class BackgroundMusic
{

  private Music music;

  private static final String PATH = "audio/music.mp3";
  private static final float VOLUME = 0.2f;


  public BackgroundMusic()
  {
	FileHandle file = files.internal(PATH);
	music = audio.newMusic(file);
	music.setVolume(VOLUME);
	music.setLooping(true);
  }


  public void play()
  {
	music.play();
  }


  public void pause()
  {
	music.pause();
  }


  public void dispose()
  {
	music.dispose();
  }


  private void stop()
  {
	music.stop();
  }

}