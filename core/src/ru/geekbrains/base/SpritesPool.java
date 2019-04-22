package ru.geekbrains.base;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.System.out;


public abstract class SpritesPool<T extends Sprite>
{

  protected final List<T> activeObjects = new ArrayList<T>();
  protected final List<T> freeObjects = new ArrayList<T>();


  protected abstract T newObject();


  public T obtain()
  {
	T obj = freeObjects.isEmpty() ?
			newObject() :
			freeObjects.remove(freeObjects.size() - 1);

	activeObjects.add(obj);
	logCounts();

	return obj;
  }


  public void updateActiveSprites(float delta)
  {
	for (T obj : activeObjects)
	{
	  if (!obj.isDestroyed())
		obj.update(delta);
	}
  }


  public void drawActiveSprites(SpriteBatch batch)
  {
	for (T obj : activeObjects)
	{
	  if (!obj.isDestroyed())
		obj.draw(batch);
	}
  }


  public void freeAllDestroyedActiveSprites()
  {
	for (Iterator<T> it = activeObjects.iterator(); it.hasNext(); )
	{
	  T obj = it.next();

	  if (obj.isDestroyed())
	  {
		it.remove();
		obj.flushDestroy();
		freeObjects.add(obj);
		logCounts();
	  }
	}
  }


  public void freeAllActiveSprites()
  {
	freeObjects.addAll(activeObjects);
	activeObjects.clear();
  }


  public void dispose()
  {
	freeObjects.clear();
	activeObjects.clear();
  }


  public List<T> getActiveObjects()
  {
	return activeObjects;
  }


  private void logCounts()
  {
	String nm = getClass().getName();
	int actCnt = activeObjects.size();
	int freeCnt = freeObjects.size();
	out.println(nm + " active/free: " + actCnt + "/" + freeCnt);
  }

}