import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Player {
    Image playerImg;
    ImageView playerImgV;
    Bombs bombs;
    public Player(int x,int y){
        playerImg=new Image("Images/spaceship.png");
        playerImgV=new ImageView(playerImg);
        playerImgV.setX(x);
        playerImgV.setY(y);
        playerImgV.setFitWidth(70);
        playerImgV.setFitHeight(70);
    }

    public ImageView newPlayer(){
        TranslateTransition transition=new TranslateTransition(Duration.millis(1000),playerImgV);
        transition.setByY(10);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
        return playerImgV;
    }

    public ImageView getBombs(double to) {
        bombs=new Bombs(playerImgV.getX()+25,playerImgV.getY(),"Images/rocket.png");

        return bombs.newBombsTo(to);
    }

    public Boolean isTarget(double to,double x,double y){
        if(bombs.newBombsTo(to).getX()>=x && bombs.newBombsTo(to).getX()<=x+50 && to+500>=y && to+500<=y+70){
            return true;
        };
        return false;
    }

    public TranslateTransition tranBPlayer(){
        return bombs.getTransition();
    }

    public void toRight(){
        playerImgV.setX(playerImgV.getX()+20);
    }
    public void toLeft(){
        playerImgV.setX(playerImgV.getX()-20);
    }


}
