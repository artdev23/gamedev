package ru.geekbrains.audio;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import static com.badlogic.gdx.Gdx.audio;
import static com.badlogic.gdx.Gdx.files;


public class BackgroundMusic
{

  private Music music;


  public BackgroundMusic(String path, float volume)
  {
	FileHandle file = files.internal(path);
	music = audio.newMusic(file);
	music.setVolume(volume);
	music.setLooping(true);
  }


  public void play()
  {
	music.play();
  }


  public boolean isPlaying()
  {
	return music.isPlaying();
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