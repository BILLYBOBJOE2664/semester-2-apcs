package p2_Peng_Kevin_GraphicsEngine;

public class P2_Peng_Kevin_Ball extends Actor {
	
	public P2_Peng_Kevin_Ball(){
		setImage(new Image())
	}
	@Override
	public void act(long now) {
		move(1, 0.5);
	}

}
