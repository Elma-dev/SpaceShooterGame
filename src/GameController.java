import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    Sphere Balle;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*TranslateTransition transition=new TranslateTransition();

        transition.setDuration(Duration.millis(10000));
        transition.setByX(transition.getByX()+100);
        transition.setByY(transition.getByY()-100);
        transition.setAutoReverse(true);
        //transition.setByX(transition.getByX()-200);
        transition.setNode(Balle);

        transition.play();*/
        Balle.setOnKeyPressed(keyEvent -> {
            System.out.println(keyEvent);
            TranslateTransition transition=new TranslateTransition();
            transition.setByX(transition.getByX()+10);
            transition.setNode(Balle);
            transition.play();
        });

    }

}
