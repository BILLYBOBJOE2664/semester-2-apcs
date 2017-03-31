/**
 * Kevin Peng
 * Period 2
 * Mar 29, 2017
 * Took
 * 
 */
package p2_Peng_Kevin_GraphicsEngine;

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
		//TODO
		return null;
	}
	
	public <A extends Actor>List<A> getIntersectionObjects(Class<A> cls){
		//TODO
		return null;
	}
	
	public <A extends Actor>A getOneIntersectingObjects(Class<A> cls){
		//TODO
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
	
	public void setIsTouchable(boolean isTouchable){
		if(getWorld() == null){
			throw new RuntimeException("Error: Can't set touchable when the actor is not in a world");
		}else{
			getWorld().setTouchable(this, isTouchable);
		}
	}
}
