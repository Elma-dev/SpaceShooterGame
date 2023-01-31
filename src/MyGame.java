import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
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

        //background

        BackgroundImage backgroundImage=new BackgroundImage(new Image("Images/deep_blue.png"), BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,null);
        pane.setBackground(new Background(backgroundImage));

        //ScoreLabel
        Text score=new Text("Score : ");
        score.setFill(Color.GREEN);
        score.setX(10);
        score.setY(20);
        score.setFont(Font.font("Calibri",FontWeight.BOLD,20));
        pane.getChildren().add(score);

        //dynamicScore
        Text dyScore=new Text("0");
        dyScore.setFill(Color.GREEN);
        dyScore.setX(80);
        dyScore.setY(20);
        dyScore.setFont(Font.font("Calibri",FontWeight.BOLD,20));
        pane.getChildren().add(dyScore);

        //playerHeart
        ArrayList<ImageView> hearts=new ArrayList<>(3);
        for(int i=0;i<3;i++){
            ImageView heart=new ImageView("Images/heart.png");
            heart.setFitWidth(30);
            heart.setFitHeight(30);
            heart.setX(120+30*i);
            heart.setY(-1);
            hearts.add(heart);
        }
        pane.getChildren().addAll(hearts);

        //deathEnimy
        ArrayList<Integer> deatInd=new ArrayList<>();





        //GameOverShadow
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        //GameOver Text
        Text gameOver=new Text("Game Over ...");
        gameOver.setEffect(ds);
        gameOver.setX(250);
        gameOver.setY(300);
        gameOver.setFont(Font.font("Calibri", FontWeight.BOLD,100));
        gameOver.setFill(Color.RED);

        //Your Score Label
        Text YS=new Text("Your Score : ");
        YS.setEffect(ds);
        YS.setX(350);
        YS.setY(350);
        YS.setFont(Font.font("Calibri", FontWeight.BOLD,50));
        YS.setFill(Color.WHITE);
        //replayButton

        Button replay=new Button();
        ImageView r=new ImageView("Images/replay.png");
        r.setFitWidth(50);
        r.setFitHeight(50);
        replay.setGraphic(r);
        replay.setBackground(null);
        replay.setLayoutX(450);
        replay.setLayoutY(360);

        //exitButton
        Button exit=new Button();
        ImageView ex=new ImageView("Images/exit.png");
        ex.setFitWidth(50);
        ex.setFitHeight(50);
        exit.setGraphic(ex);
        exit.setBackground(null);
        exit.setLayoutX(510);
        exit.setLayoutY(360);

        //menuButton
        Button menu=new Button();
        ImageView m=new ImageView("Images/settings.png");
        m.setFitWidth(40);
        m.setFitHeight(40);
        menu.setGraphic(m);
        menu.setBackground(null);
        menu.setLayoutX(950);
        menu.setLayoutY(-1);
        pane.getChildren().add(menu);


        //create Enimies
        ArrayList<BigEnimy> bigEnimies=new ArrayList<>();
        int w=0;
        for(int i=0;i<9;i++){
            w+=100;
            int h=50;
            Random randomW=new Random();
            Random randomH=new Random();

            if(w<1000) {
                int y=randomH.nextInt(h);
                BigEnimy e = new BigEnimy(randomW.nextInt(w - i * 100) + i * 100, y+20);
                pane.getChildren().add(e.getEnimy());
                bigEnimies.add(e);
            }
        }

        //EnimiesExplosion
        ImageView explosion=new ImageView("Images/explosionsE.png");
        explosion.setFitHeight(50);
        explosion.setFitWidth(50);


        //create players
        Player p1=new Player(500,500);
        ImageView p=p1.newPlayer();
        pane.getChildren().add(p);


        //add pane
        Scene scene=new Scene(pane,1000,600);



        //detectEvents


        scene.setOnMouseMoved(mouseEvent -> {
            pane.getChildren().remove(explosion);
            scene.setCursor(new ImageCursor(new Image("Images/target.png")));
            pane.getChildren().remove(p1.getDeathSmook());
            if(!p1.isDestroyPlayer()) {
                if (Math.random() < 0.1) {
                    Random random = new Random();
                    int index = random.nextInt(bigEnimies.size());
                    if (deatInd.indexOf(index) == -1) {
                        BigEnimy Enimy = bigEnimies.get(index);
                        pane.getChildren().add(Enimy.getFire(520 - Enimy.getEnimy().getY()));
                        ImageView bombs = Enimy.getFireObjectView();
                        Enimy.getBombs().getTransition().setOnFinished(actionEvent -> {
                            pane.getChildren().remove(bombs);
                            if (Enimy.getEnimy().getX() <= p.getX() + 60 && Enimy.getEnimy().getX() >= p.getX() - 20) {
                                if(p1.getScore()>0)
                                    p1.setScore(-10);
                                p1.setDeath(10);
                                try {
                                    hearts.get(p1.getDeath() / 10).setImage(new Image("Images/wheart.png"));
                                }catch(Exception e){}
                                pane.getChildren().add(p1.rocketDist());

                                if (p1.getDeath() < 10) {
                                    pane.getChildren().remove(p);
                                    p1.setDestroyPlayer();
                                    YS.setText("Your Score : "+p1.getScore());
                                    pane.getChildren().add(gameOver);
                                    pane.getChildren().add(YS);
                                    pane.getChildren().add(replay);
                                    pane.getChildren().add(exit);



                                }

                            }
                        });
                    }
                }
            }

        });

        scene.setOnKeyPressed(keyEvent -> {
            pane.getChildren().remove(explosion);
            pane.getChildren().remove(p1.getDeathSmook());
            if(!p1.isDestroyPlayer()) {
                //create enimies fire randomly
                if (Math.random() <= 0.5) {
                    Random random = new Random();
                    int index = random.nextInt(bigEnimies.size());
                    if (deatInd.indexOf(index) == -1) {
                        BigEnimy Enimy = bigEnimies.get(index);
                        pane.getChildren().add(Enimy.getFire(520 - Enimy.getEnimy().getY()));
                        ImageView bombs = Enimy.getFireObjectView();
                        Enimy.getBombs().getTransition().setOnFinished(actionEvent -> {
                            pane.getChildren().remove(bombs);
                            if (Enimy.getEnimy().getX() <= p.getX() + 60 && Enimy.getEnimy().getX() >= p.getX()-20) {
                                if(p1.getScore()>0)
                                    p1.setScore(-10);
                                p1.setDeath(10);

                                try {
                                    hearts.get(p1.getDeath() / 10).setImage(new Image("Images/wheart.png"));
                                }catch(Exception e){}


                                pane.getChildren().add(p1.rocketDist());

                                if (p1.getDeath() < 10) {
                                    pane.getChildren().remove(p);
                                    p1.setDestroyPlayer();
                                    YS.setText("Your Score : "+p1.getScore());
                                    pane.getChildren().add(gameOver);
                                    pane.getChildren().add(YS);
                                    pane.getChildren().add(replay);
                                    pane.getChildren().add(exit);
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
            pane.getChildren().remove(explosion);
            if(!p1.isDestroyPlayer()) {
                //create enimies fire
                if (Math.random() < 0.5) {
                    Random random = new Random();
                    int index = random.nextInt(bigEnimies.size());
                    if (deatInd.indexOf(index) == -1) {
                        BigEnimy Enimy = bigEnimies.get(index);
                        pane.getChildren().add(Enimy.getFire(520 - Enimy.getEnimy().getY()));
                        ImageView bombs = Enimy.getFireObjectView();
                        Enimy.getBombs().getTransition().setOnFinished(actionEvent -> {
                            pane.getChildren().remove(bombs);
                            if (Enimy.getEnimy().getX() <= p.getX() + 60 && Enimy.getEnimy().getX() >= p.getX()-20) {
                                if(p1.getScore()>0)
                                    p1.setScore(-10);
                                p1.setDeath(10);

                                try {
                                    hearts.get(p1.getDeath() / 10).setImage(new Image("Images/wheart.png"));
                                }catch(Exception e){}
                                if (p1.getDeath() < 10) {
                                    pane.getChildren().remove(p);
                                    p1.setDestroyPlayer();
                                    YS.setText("Your Score : "+p1.getScore());
                                    pane.getChildren().add(gameOver);
                                    pane.getChildren().add(YS);
                                    pane.getChildren().add(replay);
                                    pane.getChildren().add(exit);
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

                    }
                    if (index != -1) {
                        int finalIndex = index;
                        if (destroy == true && deatInd.indexOf(index) == -1)
                            tr.setOnFinished(actionEvent -> {
                                pane.getChildren().remove(bombsP);
                                //if explosion exicted
                                pane.getChildren().remove(explosion);

                                explosion.setX(bigEnimies.get(finalIndex).getEnimy().getX());
                                explosion.setY(bigEnimies.get(finalIndex).getEnimy().getY());
                                pane.getChildren().add(explosion);

                                pane.getChildren().remove(bigEnimies.get(finalIndex).destroyDeathView());
                                pane.getChildren().remove(bigEnimies.get(finalIndex).getEnimy());
                                //add death enimy
                                deatInd.add(finalIndex);
                                //create new enimies
                                Random random = new Random();
                                int y = random.nextInt(50)+20;
                                int x = random.nextInt(900);
                                BigEnimy e = new BigEnimy(x, y);
                                pane.getChildren().add(e.getEnimy());
                                bigEnimies.add(e);
                                ((Text)pane.getChildren().get(1)).setText(p1.getScore()+"");

                            });
                        else {
                            tr.setOnFinished(actionEvent -> {
                                pane.getChildren().remove(bombsP);
                                if (bigEnimies.get(finalIndex).destroyDeathView() != null) {
                                    pane.getChildren().remove(bigEnimies.get(finalIndex).destroyDeathView());
                                }
                                pane.getChildren().add(bigEnimies.get(finalIndex).setDeath());
                                ((Text)pane.getChildren().get(1)).setText(p1.getScore()+"");
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

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
