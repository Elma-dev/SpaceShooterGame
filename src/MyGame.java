import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
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
        //deathEnimy
        ArrayList<Integer> deatInd=new ArrayList<>();


        //create Enimies
        ArrayList<BigEnimy> bigEnimies=new ArrayList<>();
        int w=0;
        for(int i=0;i<8;i++){
            w+=120;
            int h=50;
            Random randomW=new Random();
            Random randomH=new Random();

            if(w<1000) {
                int y=randomH.nextInt(h);
                BigEnimy e = new BigEnimy(randomW.nextInt(w - i * 120) + i * 120, y);
                pane.getChildren().add(e.getEnimy());
                bigEnimies.add(e);
            }
        }



        //create players
        Player p1=new Player(500,500);
        pane.getChildren().add(p1.newPlayer());


        //add pane
        Scene scene=new Scene(pane,1000,600);


        //detectEvents
        scene.setOnKeyPressed(keyEvent -> {
            //create enimies fire randomly
            if(Math.random()<=0.3){
                Random random=new Random();
                int index=random.nextInt(bigEnimies.size());
                if(deatInd.indexOf(index)==-1)
                    pane.getChildren().add(bigEnimies.get(index).getFire(600));

            }

            String key=keyEvent.getCode().toString();
            switch(key){
                case "A":
                    p1.toLeft();
                    break;
                case "D":
                    p1.toRight();
                    break;

            }
        });
        scene.setOnMouseClicked(mouseEvent -> {
            //create enimies fire
            if(Math.random()<0.3){
                Random random=new Random();
                int index=random.nextInt(bigEnimies.size());
                if(deatInd.indexOf(index)==-1)
                    pane.getChildren().add(bigEnimies.get(index).getFire(600));
            }

            if(mouseEvent.getButton()==MouseButton.PRIMARY){
                double goal=-500+mouseEvent.getSceneY();
                ImageView bombsP=p1.getBombs(goal);
                pane.getChildren().add(bombsP);
                TranslateTransition tr=p1.tranBPlayer();


                int index=-1;
                boolean destroy=false;
                for(int i=0;i<bigEnimies.size();i++){
                    if(p1.isTarget(goal,bigEnimies.get(i).getEnimy().getX(),bigEnimies.get(i).getEnimy().getY()) && deatInd.indexOf(i)==-1){
                        if(bigEnimies.get(i).getDeath()<=0){
                            destroy=true;
                        };
                        index=i;
                        p1.setScore();
                    }
                    //System.out.println(p1.getScore());
                }

                if(index!=-1) {
                    int finalIndex = index;
                    if(destroy==true)
                        tr.setOnFinished(actionEvent -> {
                            pane.getChildren().remove(bombsP);
                            pane.getChildren().remove(bigEnimies.get(finalIndex).destroyDeathView());
                            pane.getChildren().remove(bigEnimies.get(finalIndex).getEnimy());
                            //add death enimy
                            deatInd.add(finalIndex);
                            //create new enimies
                            Random random=new Random();
                            int y=random.nextInt(50);
                            int x=random.nextInt(900);
                            BigEnimy e = new BigEnimy(x, y);
                            pane.getChildren().add(e.getEnimy());
                            bigEnimies.add(e);
                        });
                    else {

                        tr.setOnFinished(actionEvent -> {
                            pane.getChildren().remove(bombsP);

                            if(bigEnimies.get(finalIndex).destroyDeathView()!=null){
                                pane.getChildren().remove(bigEnimies.get(finalIndex).destroyDeathView());
                            }
                            pane.getChildren().add(bigEnimies.get(finalIndex).setDeath());
                        });
                    }
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
