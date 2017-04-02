/**
 * Kevin Peng
 * Period 2
 * April 2, 2017
 * Took 3 hours
 * 
 * THe main problem I ran into was forgetting to not include myself when looking for intersecting objects. I also initially used local bounds instead of parent bounds. Everything else was fine
 */
package p2_Peng_Kevin_GraphicsEngine;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView {
	public Actor(){
	}
	
	public abstract void act(long now);
	
	public void addedToWorld(){
		
	}
	
	public double getHeight(){
		return prefHeight(-1);
	}
	
	public List<Actor> getIntersectingObjects(){
		return getIntersectingObjects(Actor.class);
	}
	
	public <A extends Actor>List<A> getIntersectingObjects(Class<A> cls){
		ArrayList<A> list = new ArrayList<>();
		if(getWorld() == null) return list;
		for(A actor : getWorld().getTouchableObjects(cls)){
			if(actor != this && actor.intersects(getBoundsInLocal())){
				list.add(actor);
			}
		}
		return list;
	}
	
	public <A extends Actor>A getOneIntersectingObject(Class<A> cls){
		if(getWorld() == null) return null;
		for(A actor : getWorld().getTouchableObjects(cls)){
			if(actor != this && actor.getBoundsInParent().intersects(getBoundsInParent())){
				return actor;
			}
		}
		return null;
	}
	
	public double getWidth(){
		return prefWidth(-1);
	}
	
	public World getWorld(){
		if(getParent() instanceof World){
			return (World)getParent();
		}
		return null;
	}
	
	public void move(double dx, double dy){
		setLayoutX(getLayoutX() + dx);
		setLayoutY(getLayoutY() + dy);
	}
	
	public void setIsTouchable(boolean touchable){
		if(getWorld() == null){
			throw new RuntimeException("Error: Can't set touchable when the actor is not in a world");
		}
		getWorld().setTouchable(this, touchable);
	}
}
