
import com.sun.scenario.animation.shared.InfiniteClipEnvelope;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        //System.out.println("Hello world!");
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(getClass().getResource("gameView.fxml"));
        AnchorPane root= FXMLLoader.load(getClass().getResource("gameView.fxml"));
        root.setPrefSize(400,600);


        Scene scene=new Scene(root, Color.WHITE);


        Rectangle rectangle=new Rectangle();
        rectangle.setStroke(Color.WHEAT);
        rectangle.setFill(Color.GOLD);
        rectangle.setHeight(50);
        rectangle.setWidth(50);
        rectangle.setX(10);
        rectangle.setY(10);
        root.getChildren().add(rectangle);


        TranslateTransition transRect=new TranslateTransition(Duration.millis(10000));
        transRect.setToY(100);
        transRect.setNode(rectangle);
        transRect.play();

        scene.setOnKeyPressed(keyEvent -> {
            TranslateTransition transition=new TranslateTransition(Duration.millis(100));
            transition.setNode(root.getChildren().get(0));
            String btnStr=keyEvent.getCode().toString();



            switch (btnStr){
                case "LEFT":
                    transition.setByX(transition.getByX()-10);
                    transition.play();
                    break;
                case "RIGHT":
                    transition.setByX(transition.getByX()+10);
                    transition.play();
                    break;
                case "UP":
                    transition.setByY(transition.getByY()-10);
                    transition.play();
                    break;
                case "DOWN":
                    transition.setByY(transition.getByY()+10);
                    transition.play();
                    break;
                case "SPACE":
                    Circle bullet=new Circle(5,Color.BLUE);
                    bullet.setTranslateX(root.getChildren().get(0).getLayoutX()+root.getChildren().get(0).getTranslateX());
                    bullet.setTranslateY(root.getChildren().get(0).getLayoutY()-50);
                    TranslateTransition transition1=new TranslateTransition(Duration.millis(1000));
                    transition1.setToY(rectangle.getY()+rectangle.getHeight());
                    transition1.setNode(bullet);
                    transition1.play();

                    root.getChildren().add(bullet);
                    //System.out.println("RectX : "+rectangle.getX()+" , "+(rectangle.getX()+rectangle.getHeight()));
                    if(bullet.getTranslateX()>=rectangle.getX() && bullet.getTranslateX()<=rectangle.getX()+rectangle.getHeight()){
                        transition1.setOnFinished(actionEvent ->{
                            root.getChildren().remove(bullet);
                            root.getChildren().remove(rectangle);
                        });
                        
                    }
                    else
                        transition1.setOnFinished(actionEvent ->root.getChildren().remove(bullet));

            }



        });




        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}