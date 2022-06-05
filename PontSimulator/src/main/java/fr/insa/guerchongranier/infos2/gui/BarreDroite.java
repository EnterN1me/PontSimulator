package fr.insa.guerchongranier.infos2.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class BarreDroite extends VBox {
    
    private static final int LARGEUR = 200;
    private static final String PseudoClassSimulation = "btnSimulation";
    private static final String PseudoClassDelete = "btnDelete";
    
    private static Button boutonLancerSimulation;
    private static Button boutonDeleteAll;
    
    public BarreDroite(){
        
        initialisationDesBoutons();
        
        this.setPrefWidth(LARGEUR + 50);
        this.getChildren().addAll(boutonLancerSimulation, boutonDeleteAll);
        
    }
    
    public static void initialisationDesBoutons(){
        
        //bouton pour Lancer les calculs de forces du treilli
        boutonLancerSimulation = creerBouton("Lancer\nLa Simulation");
        boutonLancerSimulation.getStyleClass().add(PseudoClassSimulation);
        //action du bouton
        boutonLancerSimulation.setOnAction((ActionEvent e) -> Controller.startSimulation());
        
        //bouton pour remettre le canva de zone de dessin blanc
        boutonDeleteAll = creerBouton("TOUT\nSUPPRIMER");
        boutonDeleteAll.getStyleClass().add(PseudoClassDelete);
        //on ajoute l'action de nettoyer la zone de dessin au bouton
        boutonDeleteAll.setOnAction((ActionEvent e) -> Controller.deleteAllAction());
        
    } 
    
    //methode pour creer facilement un bouton avec la classe de style et la bonne taille 
    //(pour eviter de se répéter)
    public static Button creerBouton(String nom) {
        Button btn = new Button(nom);
        btn.getStyleClass().add("boutonActionForteDroite");
        btn.setPrefWidth(LARGEUR);
        return btn;
    }
}