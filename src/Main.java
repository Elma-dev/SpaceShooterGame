
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.midi.Soundbank;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application implements Initializable {

    public static void main(String[] args) {
        launch(args);
        //System.out.println("Hello world!");
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(getClass().getResource("gameView.fxml"));
        AnchorPane root = FXMLLoader.load(getClass().getResource("gameView.fxml"));
        root.setPrefSize(400, 600);


        Scene scene = new Scene(root, Color.WHITE);


        Rectangle rectangle = new Rectangle();
        rectangle.setStroke(Color.WHEAT);
        rectangle.setFill(Color.GOLD);
        rectangle.setHeight(50);
        rectangle.setWidth(50);
        rectangle.setX(10);
        rectangle.setY(-10);
        root.getChildren().add(rectangle);
        //create Rec
        ArrayList<Circle> bullets = new ArrayList<>();
        boolean game=true;


        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();


        TranslateTransition transRect=new TranslateTransition(Duration.millis(1500));
        transRect.setByY(80);
        transRect.setNode(rectangle);
        transRect.play();


        int nbrEnemy=3;
        AtomicInteger i = new AtomicInteger();






        scene.setOnKeyPressed(keyEvent -> {





            TranslateTransition transition=new TranslateTransition(Duration.millis(100));
            transition.setNode(root.getChildren().get(0));
            String btnStr=keyEvent.getCode().toString();

            ArrayList<Circle> enemyBullets=new ArrayList<>();
            //enemy Bullets
            if(Math.random()<0.3) {
                Circle enemyBullet = new Circle(15, Color.RED);
                //System.out.println(rectangle.getLayoutX());
                enemyBullet.setLayoutX(rectangle.getLayoutX() + rectangle.getTranslateX()+(rectangle.getHeight()/2)+10);
                //System.out.println(rectangle.getLayoutY()+rectangle.getTranslateY());
                enemyBullet.setLayoutY(rectangle.getLayoutY()+rectangle.getTranslateY()+(rectangle.getHeight()/2)+10);
                TranslateTransition tranEnemy = new TranslateTransition(Duration.millis(10000));
                tranEnemy.setToY(root.getChildren().get(0).getLayoutY());
                tranEnemy.setNode(enemyBullet);
                tranEnemy.play();
                root.getChildren().add(enemyBullet);


                enemyBullets.add(enemyBullet);




                tranEnemy.setOnFinished(actionEvent ->{
                    double r=((Circle)root.getChildren().get(0)).getRadius();
                    Circle player=(Circle) root.getChildren().get(0);
                    enemyBullets.remove(enemyBullet);

                    if(enemyBullet.getTranslateX()>=(player.getLayoutX()+player.getTranslateX()-r) && enemyBullet.getTranslateX()<=(player.getLayoutX()+player.getTranslateX()+r)) {
                        root.getChildren().remove(enemyBullet);
                        //root.getChildren().remove(player);
                    }else
                        root.getChildren().remove(enemyBullet);

                });

            }
            //new

            switch (btnStr){
                case "LEFT":
                    transition.setByX(transition.getByX()-20);
                    transition.play();
                    break;
                case "RIGHT":
                    transition.setByX(transition.getByX()+20);
                    transition.play();
                    break;
                case "SPACE":
                    Circle bullet=new Circle(5,Color.BLUE);
                    bullet.setTranslateX(root.getChildren().get(0).getLayoutX()+root.getChildren().get(0).getTranslateX());
                    bullet.setTranslateY(root.getChildren().get(0).getLayoutY()-50);
                    TranslateTransition transition1=new TranslateTransition(Duration.millis(1000));
                    //System.out.println(rectangle.getLayoutY()+rectangle.getHeight()+rectangle.getTranslateY());
                    transition1.setToY(rectangle.getLayoutY()+rectangle.getHeight()+rectangle.getTranslateY());
                    transition1.setNode(bullet);
                    transition1.play();



                    root.getChildren().add(bullet);

                    //System.out.println(enemyBullets.isEmpty());
                    if(!enemyBullets.isEmpty())
                        System.out.println(enemyBullets.get(0).getLayoutX());
                    System.out.println(bullet.getLayoutY());



                    




                    if(bullet.getTranslateX()>=rectangle.getTranslateX() && bullet.getTranslateX()<=rectangle.getTranslateX()+rectangle.getHeight()){
                        transition1.setOnFinished(actionEvent ->{
                            root.getChildren().remove(bullet);
                            //root.getChildren().remove(rectangle);
                        });

                    }
                    else
                        transition1.setOnFinished(actionEvent ->root.getChildren().remove(bullet));

            }


        });





    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}