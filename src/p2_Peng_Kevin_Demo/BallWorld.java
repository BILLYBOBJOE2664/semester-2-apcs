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
		/*for(int i = 0; i < 5; i++){
			Ball b = new Ball();
			b.setLayoutX(Math.random()*getWidth());
			b.setLayoutY(Math.random()*getHeight());
			add(b);
		}*/
		
		Ball b = new Ball(2, -.5);
		b.setLayoutX(200);
		b.setLayoutY(400);
		add(b);
		
		Ball b2 = new Ball(-2, .5);
		b2.setLayoutX(400);
		b2.setLayoutY(317);
		add(b2);
	}
}
