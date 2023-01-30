import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BigEnimy {
    private Image image;
    private ImageView imageView;
    private Bombs fire;
    private int death;
    private ImageView deathView;
    private ImageView fireObjectView;
    public BigEnimy(int x,int y){
        image=new Image("Images/space-ship.png");
        imageView=new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setX(x);
        imageView.setY(y);
        death=50;
        deathView=null;
        this.flyTo();
    }

    public ImageView getEnimy(){
        return imageView;

    }

    private void flyTo(){
        TranslateTransition transition=new TranslateTransition(Duration.millis(1000),imageView);
        //transition.setToX(imageView.getX()+5);
        transition.setByY(10);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.play();
    }

    public ImageView getFire(double to) {
        fire=new Bombs(imageView.getX()+10,imageView.getY()+25,"Images/fire.png",2000);
        fireObjectView=fire.newBombsTo(to);
        return fireObjectView;
    }
    public Bombs getBombs(){
        return fire;
    }
    public int getDeath(){
        return death;
    }
    public ImageView setDeath(){

        deathView=Life.getLife(imageView.getX()-50,imageView.getY()-10,death);
        this.death-=10;
        return deathView;
    }
    public ImageView destroyDeathView(){
        return deathView;
    }

    public ImageView getFireObjectView() {
        return fireObjectView;
    }
}
