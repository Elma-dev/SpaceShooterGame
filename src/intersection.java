import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.*;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class intersection extends Application implements Initializable {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane=new AnchorPane();

        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
        rectangle.setStroke(javafx.scene.paint.Color.WHEAT);
        rectangle.setFill(Color.GOLD);
        rectangle.setHeight(50);
        rectangle.setWidth(50);
        rectangle.setX(10);
        rectangle.setY(-10);
        anchorPane.getChildren().add(rectangle);

        javafx.scene.shape.Rectangle rectangle2 = new javafx.scene.shape.Rectangle();
        rectangle2.setStroke(javafx.scene.paint.Color.WHEAT);
        rectangle2.setFill(Color.BLUE);
        rectangle2.setHeight(50);
        rectangle2.setWidth(50);
        rectangle2.setX(310);
        rectangle2.setY(-10);
        anchorPane.getChildren().add(rectangle2);

        TranslateTransition tra1=new TranslateTransition(Duration.millis(1000),rectangle);
        TranslateTransition tra2=new TranslateTransition(Duration.millis(10000),rectangle2);

        //tra1.setToX(10);
        tra2.setToX(-300);


        tra2.play();

        /*rectangle.setEventDispatcher((event, eventDispatchChain) -> {
            System.out.println(event);
            return event;
        });*/
        rectangle2.setOnMouseClicked(mouseEvent -> {
            /*System.out.println("Ok");

            tra1.setByX(100);
            tra1.play();
            System.out.println(Shape.intersect(rectangle,rectangle2).getBoundsInLocal().getWidth());*/
            System.out.println(mouseEvent.getSceneX());
            //tra1.setByX(mouseEvent.getSceneX());
            //tra1.play();
            rectangle.setLayoutX(mouseEvent.getSceneX());
            if(Shape.intersect(rectangle,rectangle2).getBoundsInLocal().getWidth()!=-1){
                anchorPane.getChildren().remove(rectangle2);
            }
            System.out.println();
        });





        Scene scene=new Scene(anchorPane);
        stage.setScene(scene);
        stage.setWidth(400);
        stage.setHeight(600);
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hello");
    }
}
