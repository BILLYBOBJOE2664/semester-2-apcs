/**
 * Kevin Peng
 * Period 2
 * April 2, 2017
 * Took 3 hours
 * 
 * This was the class I used for testing, mainly to test collision handling.
 */
package p2_Peng_Kevin_Demo;

import p2_Peng_Kevin_GraphicsEngine.World;

public class BallWorld extends World {
	public BallWorld(){
		for(int i = 0; i < 5; i++){
			Ball b = new Ball();
			b.setLayoutX(Math.random()*500);
			b.setLayoutY(Math.random()*500);
			add(b);
		}
		
		Ball b = new Ball(2, -.5);
		b.setLayoutX(200);
		b.setLayoutY(400);
		add(b);
		
		Ball b2 = new Ball(-2, 2);
		b2.setLayoutX(400);
		b2.setLayoutY(240);
		add(b2);
		
		Ball b3 = new Ball(0, -.5);
		b3.setLayoutX(50);
		b3.setLayoutY(250);
		add(b3);
		
		Ball b4 = new Ball(0, -3);
		b4.setLayoutX(50);
		b4.setLayoutY(450);
		add(b4);
	}
}
