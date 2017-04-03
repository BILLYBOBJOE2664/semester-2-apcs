/**
 * Kevin Peng
 * Period 2
 * April 2, 2017
 * Took 3 hours
 * 
 * I wasn't sure which set implementation to use so I just went with the linked hashset so that getOneIntersectingObject() in the actor class is consistent. I also forgot to check if the
 * key event handler is null.
 */
package p2_Peng_Kevin_GraphicsEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public abstract class World extends Pane{
	private AnimationTimer timer;
	private Set<Actor> touchableActors;
	
	public World(){
		touchableActors = new LinkedHashSet<Actor>();
		timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				act(now);
			}
			
		};
		
		sceneProperty().addListener(new ChangeListener<Scene>(){

			@Override
			public void changed(ObservableValue<? extends Scene> observable, Scene oldVal, Scene newVal) {
				if(newVal != null){
					newVal.setOnKeyPressed(new EventHandler<KeyEvent>(){
						
						@Override
						public void handle(KeyEvent e){
							if(getOnKeyPressed() != null){
								getOnKeyPressed().handle(e);
							}
							for(Node node : getChildren()){
								if(node.getOnKeyPressed() != null){
									node.getOnKeyPressed().handle(e);
								}
							}
						}
						
					});
					
					newVal.setOnKeyReleased(new EventHandler<KeyEvent>(){
						
						@Override
						public void handle(KeyEvent e){
							if(getOnKeyReleased() != null){
								getOnKeyReleased().handle(e);
							}
							for(Node node : getChildren()){
								if(node.getOnKeyReleased() != null){
									node.getOnKeyReleased().handle(e);
								}
							}
						}
					});
				}
			}
			
		});
	}
	
	public void act(long now){
		for(Node node : getChildren()){
			if(node instanceof Actor){
				((Actor) node).act(now);
			}
		}
	}
	
	public void add(Actor actor){
		getChildren().add(actor);
		touchableActors.add(actor);
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
		ArrayList<A> list = new ArrayList<>();
		for(Node node : getChildren()){
			if(cls.isInstance(node)){
				list.add((A)node);
			};
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <A extends Actor>List<A> getTouchableObjects(Class<A> cls){
		ArrayList<A> list = new ArrayList<>();
		for(Node node : touchableActors){
			if(cls.isInstance(node)){
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
		if(!touchable){
			touchableActors.remove(actor);
		}else if(!touchableActors.contains(actor)){
			touchableActors.add(actor);
		}
	}
	
	public void start(){
		timer.start();
	}
	
	public void stop(){
		timer.stop();
	}
}
