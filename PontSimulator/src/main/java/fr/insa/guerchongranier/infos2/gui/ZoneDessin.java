package fr.insa.guerchongranier.infos2.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class ZoneDessin extends Pane{
    
    private static Canvas zone;
    
    public static Canvas getZone(){
        return zone;
    }
    
    public ZoneDessin(){
        zone = new Canvas(this.getWidth(), this.getHeight());
        this.getChildren().add(zone);
        this.setPrefSize(1000, 720);
        
        //capte le y du click
        zone.heightProperty().bind(this.heightProperty());
        zone.heightProperty().addListener((o) -> {});
        
        //capte le x du click
        zone.widthProperty().bind(this.widthProperty());
        zone.widthProperty().addListener((o) -> {});
        
        //reference au controller 
        zone.setOnMouseClicked((t) -> {
            Controller.clicDansZoneDessin(t);
        });  
    }
    
}
