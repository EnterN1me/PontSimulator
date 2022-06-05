package fr.insa.guerchongranier.infos2.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
/**
 *
 * @author EnterN1me
 */
public class Centre extends StackPane{
    
    private Image pixallan;
    private ImageView beryTheBest;
    private ZoneDessin ecranClickable = new ZoneDessin();
    
    public Centre(){
        this.getChildren().add(ecranClickable);
    }
    
    public ImageView getBeryTheBest(){
        return this.beryTheBest;
    }
    
    public void bombeGif(){
        pixallan = new Image("Bombe.gif");
        beryTheBest= new ImageView(pixallan);
        beryTheBest.setFitWidth(200);
        beryTheBest.setFitHeight(200);
        Timeline timeline = new Timeline(
        new KeyFrame(Duration.ZERO, e -> ajouteBombe()),
        new KeyFrame(Duration.millis(800), e -> {MainFx.getScene().getStylesheets().add("bombe.css");MainFx.getScene().getStylesheets().remove("lightTheme.css");}),
        new KeyFrame(Duration.millis(1000), e -> {retireBombe();Controller.endDeleteAllAction();MainFx.getScene().getStylesheets().add("lightTheme.css");MainFx.getScene().getStylesheets().remove("bombe.css");})
        );
        timeline.play();    
    }
    
    public void ajouteBombe(){
        this.getChildren().add(beryTheBest);
    }

    public void retireBombe(){
        this.getChildren().remove(beryTheBest);
    }
}
