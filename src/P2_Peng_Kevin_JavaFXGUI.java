import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Kevin Peng
 * Period 2
 * Feb 28, 2017
 * took 20 minutes
 * 
 * I spent some time researching how to do anything before starting. I didn't really understand is insets overalpped at first but they don't. I couldn't find the right node type to take up space
 * in the group initially.
 */

public class P2_Peng_Kevin_JavaFXGUI extends Application {
	
	private ColorPicker bodyColorPicker;
	private ColorPicker wheelColorPicker;

	public void start(Stage stage) throws Exception {
		stage.setTitle("GUI");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(20, 20, 0, 20));
		hBox.setSpacing(10);
		Label label = new Label("Hello");
		Button button = new Button("Button");
		CheckBox checkbox = new CheckBox("I have read and agree to the terms");
		hBox.getChildren().addAll(label, button, checkbox);
		root.setTop(hBox);
		
		Slider slider = new Slider(0, 100, 50);
		slider.setPadding(new Insets(10));
		root.setBottom(slider);
		
		bodyColorPicker = new ColorPicker(Color.RED);
		root.setLeft(bodyColorPicker);
		
		wheelColorPicker = new ColorPicker(Color.BLACK);
		root.setRight(wheelColorPicker);
		
		Group group = new Group();
		Region region = new Region();
		region.setMinSize(300, 300);
		drawCar(group, 200, bodyColorPicker.getValue(), wheelColorPicker.getValue());
		group.getChildren().add(region);
		root.setCenter(group);
		
		stage.show();
	}
	
	private void drawCar(Group group, int carWidth, Color bodyColor, Color wheelColor){
		int carHeight = carWidth/2;
		int wheelRadius = carWidth/8;
		int wheelOffsetX = carWidth/3;
		int wheelOffsetY = carWidth/4;
		int windowOffset = carWidth/16;
		int windowSeperatorWidth = carWidth/16;
		
		Color windowColor = Color.LIGHTBLUE;
		
		//bottom part
		Rectangle bodyBottom = new Rectangle(group.prefWidth()/2 - CAR_WIDTH/2, group.getHeight()/2, CAR_WIDTH, CAR_HEIGHT/2);
		bodyBottom.setFill(BODY_COLOR);
		group.getChildren().add(bodyBottom);
		
		//top part
		Arc bodyTop = new Arc(group.getWidth()/2 - CAR_WIDTH*0.125, group.getHeight()/2, CAR_WIDTH*0.375, CAR_HEIGHT/2, 0, 180);
		bodyTop.setFill(BODY_COLOR);
		group.getChildren().add(bodyTop);
		
		//window
		Arc window = new Arc(group.getWidth()/2 - CAR_WIDTH*0.125, group.getHeight()/2, CAR_WIDTH*0.375 - WINDOW_OFFSET, CAR_HEIGHT/2 - WINDOW_OFFSET, 0, 180);
		window.setFill(WINDOW_COLOR);
		group.getChildren().add(window);
		
		//seperate the front and back window
		Rectangle windowSeperator = new Rectangle(group.getWidth()/2 - CAR_WIDTH*0.125 - WINDOW_SEPARATOR_WIDTH, group.getHeight()/2 - CAR_HEIGHT/2 + WINDOW_OFFSET/2, WINDOW_SEPARATOR_WIDTH, CAR_HEIGHT/2 - WINDOW_OFFSET/2);
		windowSeperator.setFill(BODY_COLOR);
		group.getChildren().add(windowSeperator);
		
		//left wheel
		Circle wheelLeft = new Circle(group.getWidth()/2 - WHEEL_OFFSET_X, group.getHeight()/2 + WHEEL_OFFSET_Y, WHEEL_RADIUS);
		wheelLeft.setFill(WHEEL_COLOR);
		group.getChildren().add(wheelLeft);
		
		//right wheel
		Circle wheelRight = new Circle(group.getWidth()/2 + WHEEL_OFFSET_X, group.getHeight()/2 + WHEEL_OFFSET_Y, WHEEL_RADIUS);
		wheelRight.setFill(WHEEL_COLOR);
		group.getChildren().add(wheelRight);
		
	}

	public static void main(String[] args) {
		launch();
	}

}
