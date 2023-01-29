import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class MyGame extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane pane=new AnchorPane();



        //testEnimy
        BigEnimy e1=new BigEnimy( 30,80);
        pane.getChildren().add(e1.getEnimy());



        //create Enimies
        ArrayList<BigEnimy> bigEnimies=new ArrayList<>();
        int w=0;
        for(int i=0;i<8;i++){
            w+=120;
            int h=50;
            Random randomW=new Random();
            Random randomH=new Random();

            if(w<1000) {
                System.out.println();
                int y=randomH.nextInt(h);
                System.out.println(y);
                BigEnimy e = new BigEnimy(randomW.nextInt(w - i * 120) + i * 120, y);
                pane.getChildren().add(e.getEnimy());
                bigEnimies.add(e);
            }
        }



        //create players
        Player p1=new Player(500,500);
        pane.getChildren().add(p1.newPlayer());




        //testLine:
        /*
        Line line=new Line(30,80,30+50,80+60);
        pane.getChildren().add(line);*/



        Scene scene=new Scene(pane,1000,600);


        //detectEvents
        scene.setOnKeyPressed(keyEvent -> {

            String key=keyEvent.getCode().toString();
            switch(key){
                case "LEFT":
                    p1.toLeft();
                    break;
                case "RIGHT":
                    p1.toRight();
                    break;

            }
        });
        scene.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()==MouseButton.PRIMARY){
                double goal=-500+mouseEvent.getSceneY();
                ImageView bombsP=p1.getBombs(goal);
                pane.getChildren().add(bombsP);
                TranslateTransition tr=p1.tranBPlayer();


                int index=-1;
                for(int i=0;i<bigEnimies.size();i++){
                    if(p1.isTarget(goal,bigEnimies.get(i).getEnimy().getX(),bigEnimies.get(i).getEnimy().getY())){
                        index=i;
                    }
                }

                if(index!=-1) {
                    int finalIndex = index;
                    tr.setOnFinished(actionEvent -> {
                            pane.getChildren().remove(bombsP);
                            pane.getChildren().remove(bigEnimies.get(finalIndex).getEnimy());

                    });
                }else {
                    tr.setOnFinished(actionEvent -> {
                        pane.getChildren().remove(bombsP);
                    });
                }





            }
        });



        stage.setScene(scene);
        stage.show();
    }
}
