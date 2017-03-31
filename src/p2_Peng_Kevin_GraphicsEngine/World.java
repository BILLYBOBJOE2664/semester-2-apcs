/**
 * Kevin Peng
 * Period 2
 * Mar 29, 2017
 * Took
 * 
 */
package p2_Peng_Kevin_GraphicsEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class World extends Pane{
	private AnimationTimer timer;
	private Set<Actor> touchableActors;
	
	public World(){
		timer = new AnimationTimer(){
			
			@Override
			public void handle(long now){
				for(Node node : getChildren()){
					if(node instanceof Actor){
						((Actor) node).act(now);
					}
				}
			}
			
		};
	}
	
	public void add(Actor actor){
		getChildren().add(actor);
	}
	
	public void add(Actor actor, boolean isTouchable){
		getChildren().add(actor);
		if(isTouchable){
			touchableActors.add(actor);
		}
	}
	
	public List<Actor> getObjects(){
		return getObjects(Actor.class);
	}
	
	@SuppressWarnings("unchecked")
	public <A extends Actor>List<A> getObjects(Class<A> cls){
		ArrayList<A> list = new ArrayList<A>();
		for(Node node : getChildren()){
			if(cls.isInstance(node)){
				list.add((A)node);
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <A extends Actor>List<A> getTouchableObjects(Class<A> cls){
		ArrayList<A> list =  new ArrayList<>();
		for(Node node : getChildren()){
			if(cls.isInstance(node) && touchableActors.contains(node)){
				list.add((A)node);
			}
		}
		return list;
	}
	
	public void remove(Actor actor){
		getChildren().remove(actor);
		touchableActors.remove(actor);
	}
	
	public void remove(Collection<? extends Actor> actors){
		getChildren().removeAll(actors);
		touchableActors.removeAll(actors);
	}
	
	public void setTouchable(Actor actor, boolean touchable){
		if(touchable && !touchableActors.contains(actor)){
			touchableActors.add(actor);
		}else if(!touchable){
			touchableActors.remove(actor);
		}
	}
	
	public void start(){
		timer.start();
	}
	
	public void stop(){
		timer.stop();
	}
}
