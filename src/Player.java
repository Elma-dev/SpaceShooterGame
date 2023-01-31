import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Player {
    private Image playerImg;
    private ImageView playerImgV;
    private Bombs bombs;
    private int score;

    private int death;
    private boolean destroyPlayer=false;
    private ImageView deathSmook;

    public Player(int x,int y){
        playerImg=new Image("Images/spaceship.png");
        playerImgV=new ImageView(playerImg);
        playerImgV.setX(x);
        playerImgV.setY(y);
        playerImgV.setFitWidth(70);
        playerImgV.setFitHeight(70);
        score=0;
        death=30;
        deathSmook=null;
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
        bombs=new Bombs(playerImgV.getX()+25,playerImgV.getY(),"Images/rocket.png",1000);
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
        if(playerImgV.getX()<=920)
            playerImgV.setX(playerImgV.getX()+20);
    }
    public void toLeft(){
        if(playerImgV.getX()>=20)
            playerImgV.setX(playerImgV.getX()-20);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int s) {
        score +=s;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death -= death;
    }

    public boolean isDestroyPlayer() {
        return destroyPlayer;
    }

    public void setDestroyPlayer() {
        this.destroyPlayer = true;
    }
    public ImageView rocketDist(){
        deathSmook=new ImageView("Images/rocketFire.png");
        deathSmook.setFitHeight(50);
        deathSmook.setFitWidth(50);
        deathSmook.setX(playerImgV.getX()+10);
        deathSmook.setY(playerImgV.getY()-20);
        return deathSmook;
    }

    public ImageView getDeathSmook() {
        return deathSmook;
    }
}
