package fr.insa.guerchongranier.infos2.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BarreHaut extends BorderPane {
    
    private static HBox hautGauche;
    private static Button boutonSave;
    private static Button boutonOuvrir;
    private static Button boutonClose;
    
    public BarreHaut(){
        hautGauche = new HBox();
        initialiseHBox();
        this.setLeft(hautGauche);
        this.setRight(boutonClose);
        
    }
    
    //permet de placer les bouton en liste a gauche
    public static void initialiseHBox(){
        initialisationDesBoutons();
        hautGauche.getChildren().addAll(boutonSave, boutonOuvrir);
    }
    
    public static void initialisationDesBoutons(){
        boutonSave = creerBouton("Sauvegarder");
        boutonSave.setOnAction((e) -> Controller.savePartOne());
        boutonOuvrir = creerBouton("Ouvrir");
        boutonOuvrir.setOnAction((e) -> Controller.open());
        boutonClose = new Button("X");
        
        //ferme la fenetre quand cliqué
        boutonClose.setStyle("-fx-font: 22 arial; -fx-base: #c60000;");
        boutonClose.setOnAction((ActionEvent e) ->  Controller.closeScreen());
    }
    
    public static Button creerBouton(String nom){
        Button btn = new Button(nom);
        btn.getStyleClass().add("boutonControleHaut");
        return btn;
    }
}
