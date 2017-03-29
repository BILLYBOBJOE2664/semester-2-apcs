/**
 * Kevin Peng
 * Period 2
 * Mar 29, 2017
 * Took
 * 
 */
package p2_Peng_Kevin_GraphicsEngine;

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
		
	}
	
	public <A extends Actor>List<A> getObjects(Class<A> cls){
		for(Node node : getChildren()){
			if(node instanceof cls){
				
			}
		}
	}
}
