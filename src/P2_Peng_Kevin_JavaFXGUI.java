import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.VBox;
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
 * I spent some time researching how to do anything before starting. I didn't really understand is insets overlapped at first but they don't. I couldn't find the right node type to take up space
 * in the group initially.
 */

public class P2_Peng_Kevin_JavaFXGUI extends Application {
	
	private ColorPicker bodyColorPicker;
	private ColorPicker wheelColorPicker;
	private Slider slider;
	//place where the car is drawn
	Group group;

	public void start(Stage stage) throws Exception {
		stage.setTitle("GUI");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(20, 20, 5, 20));
		hBox.setSpacing(10);
		Button button = new Button("Reset");
		button.setOnAction(new ButtonHandler());
		hBox.getChildren().add(button);
		root.setTop(hBox);
		
		slider = new Slider(0, 300, 50);
		slider.setValue(200);
		slider.valueProperty().addListener(new SliderHandler());
		slider.setPadding(new Insets(10));
		root.setBottom(slider);
		
		VBox bodyColorPickerBox = new VBox();
		bodyColorPickerBox.setAlignment(Pos.TOP_CENTER);
		Label bodyColorPickerLabel = new Label("Body Color");
		bodyColorPicker = new ColorPicker(Color.RED);
		bodyColorPicker.valueProperty().addListener(new ColorHandler());
		bodyColorPickerBox.getChildren().addAll(bodyColorPickerLabel, bodyColorPicker);
		root.setLeft(bodyColorPickerBox);
		
		VBox wheelColorPickerBox = new VBox();
		wheelColorPickerBox.setAlignment(Pos.TOP_CENTER);
		Label wheelColorPickerLabel = new Label("Wheel Color");
		wheelColorPicker = new ColorPicker(Color.BLACK);
		wheelColorPicker.valueProperty().addListener(new ColorHandler());
		wheelColorPickerBox.getChildren().addAll(wheelColorPickerLabel, wheelColorPicker);
		root.setRight(wheelColorPickerBox);
		
		group = new Group();
		drawCar(group, 200, bodyColorPicker.getValue(), wheelColorPicker.getValue());
		root.setCenter(group);
		
		stage.show();
		
		
		}
	
	private void drawCar(Group group, int carWidth, Color bodyColor, Color wheelColor){
		group.getChildren().clear();
		
		int carHeight = carWidth/2;
		int wheelRadius = carWidth/8;
		int wheelOffsetX = carWidth/3;
		int wheelOffsetY = carWidth/4;
		int windowOffset = carWidth/16;
		int windowSeperatorWidth = carWidth/16;
		
		Color windowColor = Color.LIGHTBLUE;
		
		//set the size of the group
		Region region = new Region();
		region.setMinSize(carWidth + 30, carHeight/2 + wheelOffsetY + wheelRadius + 30);
		group.getChildren().add(region);
		
		//bottom part
		Rectangle bodyBottom = new Rectangle(group.prefWidth(-1)/2 - carWidth/2, group.prefHeight(-1)/2, carWidth, carHeight/2);
		bodyBottom.setFill(bodyColor);
		group.getChildren().add(bodyBottom);
		
		//top part
		Arc bodyTop = new Arc(group.prefWidth(-1)/2 - carWidth*0.125, group.prefHeight(-1)/2, carWidth*0.375, carHeight/2, 0, 180);
		bodyTop.setFill(bodyColor);
		group.getChildren().add(bodyTop);
		
		//window
		Arc window = new Arc(group.prefWidth(-1)/2 - carWidth*0.125, group.prefHeight(-1)/2, carWidth*0.375 - windowOffset, carHeight/2 - windowOffset, 0, 180);
		window.setFill(windowColor);
		group.getChildren().add(window);
		
		//seperate the front and back window
		Rectangle windowSeperator = new Rectangle(group.prefWidth(-1)/2 - carWidth*0.125 - windowSeperatorWidth, group.prefHeight(-1)/2 - carHeight/2 + windowOffset/2, windowSeperatorWidth, carHeight/2 - windowOffset/2);
		windowSeperator.setFill(bodyColor);
		group.getChildren().add(windowSeperator);
		
		//left wheel
		Circle wheelLeft = new Circle(group.prefWidth(-1)/2 - wheelOffsetX, group.prefHeight(-1)/2 + wheelOffsetY, wheelRadius);
		wheelLeft.setFill(wheelColor);
		group.getChildren().add(wheelLeft);
		
		//right wheel
		Circle wheelRight = new Circle(group.prefWidth(-1)/2 + wheelOffsetX, group.prefHeight(-1)/2 + wheelOffsetY, wheelRadius);
		wheelRight.setFill(wheelColor);
		group.getChildren().add(wheelRight);
		
	}
	
	private class SliderHandler implements ChangeListener<Number>{

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			drawCar(group, (int)slider.getValue(), bodyColorPicker.getValue(), wheelColorPicker.getValue());
		}
		
	}
	
	private class ColorHandler implements ChangeListener<Color>{

		@Override
		public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
			drawCar(group, (int)slider.getValue(), bodyColorPicker.getValue(), wheelColorPicker.getValue());
		}
	}
	
	private class ButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent e) {
			bodyColorPicker.setValue(Color.RED);
			wheelColorPicker.setValue(Color.BLACK);
			slider.setValue(200);
		}
		
	}

	public static void main(String[] args) {
		launch();
	}

}
