package fr.insa.guerchongranier.infos2.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BarreBas extends HBox {
    
    private static Label affichageAide = new Label();
    
    public BarreBas(){
        InitialiseAffichageAide();
        this.getChildren().add(affichageAide);
    }  
    
    private static void InitialiseAffichageAide(){
        affichageAide.setMaxWidth(Double.MAX_VALUE);
        affichageAide.setPrefHeight(50);
        setText("Vous trouverez ici les indications dont vous aurez besoin\t Commencez par creer un Noeud en cliquant sur l'écran");
    }
    
    public static void setText(String texte){
        affichageAide.setText("\t"+texte+"\t");
    }
}
