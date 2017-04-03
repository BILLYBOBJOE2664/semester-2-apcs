/**
 * Kevin Peng
 * Period 2
 * April 2, 2017
 * Took 3 hours
 * 
 * Making the collision between different objects work properly was easily the most difficult part. Mistakes include using local bounds instead of parent bounds, returning itself as an
 * intersecting object, not being able to differentiate between hits from the side versus top and bottom, and measuring distance incorrectly. My working implementation measures the distance
 * between the center of the two nodes and checks to see if it's farther left and right or top and bottom. Then it uses the sign to figure out the opposite directions. I also couldn't
 * directly modify dx and dy in my eventHandler so I made got around it by making a new method.
 */
package p2_Peng_Kevin_Demo;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import p2_Peng_Kevin_GraphicsEngine.Actor;

public class Ball extends Actor {
	double dx;
	double dy;
	
	public Ball(){
		setUp(Math.random()*6 - 3, Math.random()*6 - 3);
	}
	
	public Ball(double dx, double dy){
		setUp(dx, dy);
	}
	
	private void setUp(double dx, double dy){
		setImage(new Image("file:minesweeper/images/bomb_wrong.gif"));
		this.dx = dx;
		this.dy = dy;
		setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent e) {
				randomizeSpeed();
			}
			
		});
	}
	private void randomizeSpeed(){
		dx = Math.random()*6 - 3;
		dy = Math.random()*6 - 3;
	}
	@Override
	public void act(long now) {
		move(dx, dy);
		handleCollisions();
		
	}
	
	public void handleCollisions(){
		//left
		if(getLayoutX() < 0){
			dx = -dx;
			setLayoutX(0);
		}
		//right
		if(getLayoutX() + getWidth() > getWorld().getWidth()){
			dx = -dx;
			setLayoutX(getWorld().getWidth() - getWidth());
		}
		//top
		if(getLayoutY() < 0){
			dy = -dy;
			setLayoutY(0);
		}
		//bottom
		if(getLayoutY() + getHeight() > getWorld().getHeight()){
			dy = -dy;
			setLayoutY(getWorld().getHeight() - getHeight());
		}
		
		Actor a = getOneIntersectingObject(Actor.class);
		if(a != null){
			double leftDist = getLayoutX() + getWidth()/2 - a.getLayoutX() - a.getWidth()/2;
			double topDist = getLayoutY() + getHeight()/2 - a.getLayoutY() - a.getHeight()/2;
			//left
			if(Math.abs(leftDist) > Math.abs(topDist) && leftDist >= 0){
				if(dx < 0) dx = -dx;
			}//right
			else if(Math.abs(leftDist) > Math.abs(topDist) && leftDist <= 0){
				if(dx > 0) dx = -dx;
			}
			//top
			else if(Math.abs(topDist) > Math.abs(leftDist) && topDist >= 0){
				if(dy < 0) dy = -dy;
			}//bottom
			else if(Math.abs(topDist) > Math.abs(leftDist) && topDist <= 0){
				if(dy > 0 ) dy = -dy;
			}
		}
	}

}
