package fr.insa.guerchongranier.infos2.gui;

import javafx.scene.layout.BorderPane;

@SuppressWarnings("FieldMayBeFinal") // on ne prefere pas
public class MainBorderPane extends BorderPane{
    
    private Centre centre = new Centre();
    
    public MainBorderPane() {
        this.setTop(new BarreHaut());
        this.setLeft(new BarreGauche());
        this.setRight(new BarreDroite());
        this.setBottom(new BarreBas());
        this.setCenter(centre);
    }
    
    public Centre getCentre(){
        return this.centre;
    }

}
