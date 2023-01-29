import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Bombs {
    private Image fireImage;
    private ImageView fireImageV;
    private TranslateTransition transition;
    public Bombs(double x, double y,String icon){
        fireImage=new Image(icon);
        fireImageV=new ImageView(fireImage);
        fireImageV.setFitWidth(20);
        fireImageV.setFitHeight(20);
        fireImageV.setX(x);
        fireImageV.setY(y);
        transition=new TranslateTransition(Duration.millis(1000),fireImageV);
    }
    public ImageView newBombsTo(double y){
        transition.setToY(y);
        transition.play();
        return fireImageV;
    }

    public TranslateTransition getTransition() {
        return transition;
    }
}
