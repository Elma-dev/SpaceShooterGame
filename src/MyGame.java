import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


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

        //stopGame
        boolean stop=false;

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


        //
        Label GameOver1=new Label("Game Over.");
        GameOver1.setTextFill(Color.RED);
        GameOver1.setFont(Font.font("Calibri",100));
        /*GameOver1.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(GameOver1,0.0);
        AnchorPane.setRightAnchor(GameOver1,0.0);
        AnchorPane.set
        GameOver1.setAlignment(Pos.CENTER);
        GameOver1.setTextAlignment(TextAlignment.CENTER);
        VBox box=new VBox(GameOver1);
        box.setAlignment(Pos.CENTER);*/

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        Text gameOver=new Text("Game Over ...");
        gameOver.setEffect(ds);
        gameOver.setX(250);
        gameOver.setY(300);
        gameOver.setFont(Font.font("Calibri", FontWeight.BOLD,100));
        gameOver.setFill(Color.RED);
        gameOver.setSelectionFill(Color.BLACK);



        //create players
        Player p1=new Player(500,500);
        ImageView p=p1.newPlayer();
        pane.getChildren().add(p);


        //add pane
        Scene scene=new Scene(pane,1000,600);

        //SmokOfdeath
        ImageView rocketDist=null;

        //detectEvents

        scene.setOnKeyPressed(keyEvent -> {
            pane.getChildren().remove(p1.getDeathSmook());
            if(!p1.isDestroyPlayer()) {
                //create enimies fire randomly
                if (Math.random() <= 0.3) {
                    Random random = new Random();
                    int index = random.nextInt(bigEnimies.size());
                    if (deatInd.indexOf(index) == -1) {
                        BigEnimy Enimy = bigEnimies.get(index);
                        pane.getChildren().add(Enimy.getFire(520 - Enimy.getEnimy().getY()));
                        ImageView bombs = Enimy.getFireObjectView();
                        Enimy.getBombs().getTransition().setOnFinished(actionEvent -> {
                            pane.getChildren().remove(bombs);
                            if (Enimy.getEnimy().getX() <= p.getX() + 60 && Enimy.getEnimy().getX() >= p.getX()-20) {
                                p1.setScore(-10);
                                p1.setDeath(10);

                                pane.getChildren().add(p1.rocketDist());

                                if (p1.getDeath() < 10) {
                                    pane.getChildren().remove(p);
                                    p1.setDestroyPlayer();
                                    pane.getChildren().add(gameOver);
                                }


                            }
                        });
                    }
                }
                String key = keyEvent.getCode().toString();
                switch (key) {
                    case "A":
                        p1.toLeft();
                        break;
                    case "D":
                        p1.toRight();
                        break;
                }
            }
        });
        scene.setOnMouseClicked(mouseEvent -> {
            pane.getChildren().remove(p1.getDeathSmook());
            if(!p1.isDestroyPlayer()) {
                //create enimies fire
                if (Math.random() < 0.3) {
                    Random random = new Random();
                    int index = random.nextInt(bigEnimies.size());
                    if (deatInd.indexOf(index) == -1) {
                        BigEnimy Enimy = bigEnimies.get(index);
                        pane.getChildren().add(Enimy.getFire(520 - Enimy.getEnimy().getY()));
                        ImageView bombs = Enimy.getFireObjectView();
                        Enimy.getBombs().getTransition().setOnFinished(actionEvent -> {
                            pane.getChildren().remove(bombs);
                            if (Enimy.getEnimy().getX() <= p.getX() + 60 && Enimy.getEnimy().getX() >= p.getX()-20) {
                                p1.setScore(-10);
                                p1.setDeath(10);
                                System.out.println(p1.getDeath());
                                if (p1.getDeath() < 10) {
                                    pane.getChildren().remove(p);
                                    p1.setDestroyPlayer();
                                }
                            }
                        });
                    }
                }

                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    double goal = -500 + mouseEvent.getSceneY();
                    ImageView bombsP = p1.getBombs(goal);
                    pane.getChildren().add(bombsP);
                    TranslateTransition tr = p1.tranBPlayer();
                    int index = -1;
                    boolean destroy = false;
                    for (int i = 0; i < bigEnimies.size(); i++) {
                        if (p1.isTarget(goal, bigEnimies.get(i).getEnimy().getX(), bigEnimies.get(i).getEnimy().getY()) && deatInd.indexOf(i) == -1) {
                            if (bigEnimies.get(i).getDeath() <= 0) {
                                destroy = true;
                            }
                            index = i;
                            p1.setScore(10);
                        }
                        //System.out.println(p1.getScore());
                    }
                    if (index != -1) {
                        int finalIndex = index;
                        if (destroy == true && deatInd.indexOf(index) == -1)
                            tr.setOnFinished(actionEvent -> {
                                pane.getChildren().remove(bombsP);
                                pane.getChildren().remove(bigEnimies.get(finalIndex).destroyDeathView());
                                pane.getChildren().remove(bigEnimies.get(finalIndex).getEnimy());
                                //add death enimy
                                deatInd.add(finalIndex);
                                //create new enimies
                                Random random = new Random();
                                int y = random.nextInt(50);
                                int x = random.nextInt(900);
                                BigEnimy e = new BigEnimy(x, y);
                                pane.getChildren().add(e.getEnimy());
                                bigEnimies.add(e);
                            });
                        else {
                            tr.setOnFinished(actionEvent -> {
                                pane.getChildren().remove(bombsP);
                                if (bigEnimies.get(finalIndex).destroyDeathView() != null) {
                                    pane.getChildren().remove(bigEnimies.get(finalIndex).destroyDeathView());
                                }
                                pane.getChildren().add(bigEnimies.get(finalIndex).setDeath());
                            });
                        }
                    } else {
                        tr.setOnFinished(actionEvent -> {
                            pane.getChildren().remove(bombsP);
                        });
                    }
                }
            }
        });


        stage.setScene(scene);
        stage.show();
    }
}
