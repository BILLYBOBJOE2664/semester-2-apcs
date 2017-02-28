import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Kevin Peng
 * Period 2
 * Feb 27, 2017
 * Took 30 minutes
 * 
 * It took a bit for me to understand how JavaFx worked. I also didn't initially understand the Arc class specifically that length was an angle and not a actual length. Other than that I was able to put everything together without trouble.
 */

public class JavaFXPicture extends Application {
	
	private static final int CAR_WIDTH = 200;
	private static final int CAR_HEIGHT = CAR_WIDTH/2;
	private static final int WHEEL_RADIUS = CAR_WIDTH/8;
	private static final int WHEEL_OFFSET_X = CAR_WIDTH/3;
	private static final int WHEEL_OFFSET_Y = CAR_WIDTH/4;
	private static final int WINDOW_OFFSET = CAR_WIDTH/16;
	private static final int WINDOW_SEPARATOR_WIDTH = CAR_WIDTH/16;
	
	private static final Color BODY_COLOR = Color.RED;
	private static final Color WHEEL_COLOR = Color.BLACK;
	private static final Color WINDOW_COLOR = Color.LIGHTBLUE;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Car");
		stage.setResizable(false);
		stage.sizeToScene();
		
		Group root = new Group();
		Scene scene = new Scene(root, 300, 300);
		stage.setScene(scene);
		
		//bottom part
		Rectangle bodyBottom = new Rectangle(scene.getWidth()/2 - CAR_WIDTH/2, scene.getHeight()/2, CAR_WIDTH, CAR_HEIGHT/2);
		bodyBottom.setFill(BODY_COLOR);
		root.getChildren().add(bodyBottom);
		
		//top part
		Arc bodyTop = new Arc(scene.getWidth()/2 - CAR_WIDTH*0.125, scene.getHeight()/2, CAR_WIDTH*0.375, CAR_HEIGHT/2, 0, 180);
		bodyTop.setFill(BODY_COLOR);
		root.getChildren().add(bodyTop);
		
		//window
		Arc window = new Arc(scene.getWidth()/2 - CAR_WIDTH*0.125, scene.getHeight()/2, CAR_WIDTH*0.375 - WINDOW_OFFSET, CAR_HEIGHT/2 - WINDOW_OFFSET, 0, 180);
		window.setFill(WINDOW_COLOR);
		root.getChildren().add(window);
		
		//seperate the front and back window
		Rectangle windowSeperator = new Rectangle(scene.getWidth()/2 - CAR_WIDTH*0.125 - WINDOW_SEPARATOR_WIDTH, scene.getHeight()/2 - CAR_HEIGHT/2 + WINDOW_OFFSET/2, WINDOW_SEPARATOR_WIDTH, CAR_HEIGHT/2 - WINDOW_OFFSET/2);
		windowSeperator.setFill(BODY_COLOR);
		root.getChildren().add(windowSeperator);
		
		//left wheel
		Circle wheelLeft = new Circle(scene.getWidth()/2 - WHEEL_OFFSET_X, scene.getHeight()/2 + WHEEL_OFFSET_Y, WHEEL_RADIUS);
		wheelLeft.setFill(WHEEL_COLOR);
		root.getChildren().add(wheelLeft);
		
		//right wheel
		Circle wheelRight = new Circle(scene.getWidth()/2 + WHEEL_OFFSET_X, scene.getHeight()/2 + WHEEL_OFFSET_Y, WHEEL_RADIUS);
		wheelRight.setFill(WHEEL_COLOR);
		root.getChildren().add(wheelRight);
		
		stage.show();
		
	}

}
