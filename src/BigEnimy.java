import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BigEnimy {
    private Image image;
    private ImageView imageView;
    private Bombs fire;
    public BigEnimy(int x,int y){
        image=new Image("Images/space-ship.png");
        imageView=new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setX(x);
        imageView.setY(y);
    }

    public ImageView getEnimy(){
        this.flyTo();
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

    public ImageView getFire(int to) {
        fire=new Bombs(imageView.getX()+10,imageView.getY()+25,"Images/fire.png");
        return fire.newBombsTo(to);
    }
}
