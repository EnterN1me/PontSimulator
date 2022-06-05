package fr.insa.guerchongranier.infos2.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class BarreGauche extends VBox {
    
    private static final int LARGEUR = 250;
    
    public static ToggleButton boutonSwitchNoeud;
    public static ToggleButton boutonSwitchPoutre;
    public static ToggleButton boutonSwitchTerrain;
    public static ToggleButton boutonSwitchForce;
    public static ToggleButton boutonSwitchSupprimer;
    private static ToggleGroup groupeDeBouton;
    
    public BarreGauche(){
        
        groupeDeBouton = new ToggleGroup();
        
        InitialisationDesBoutonsSwitch();
        
        this.setPrefWidth(LARGEUR + 20);
        this.getChildren().addAll(boutonSwitchNoeud, boutonSwitchPoutre, boutonSwitchTerrain, boutonSwitchForce, boutonSwitchSupprimer);
    }
    
    //methode qui crée tout les boutons, et met celui de noeud selectionné de base
    public static void InitialisationDesBoutonsSwitch(){
        boutonSwitchNoeud = creerBoutonSwitch("Noeud");
        boutonSwitchNoeud.setOnAction((ActionEvent e) -> Controller.switchNoeudClique());
        boutonSwitchPoutre = creerBoutonSwitch("Poutre");
        boutonSwitchPoutre.setOnAction((ActionEvent e) -> Controller.switchPoutreClique());
        boutonSwitchTerrain = creerBoutonSwitch("Terrain");
        boutonSwitchForce = creerBoutonSwitch("Force");
        boutonSwitchForce.setOnAction((ActionEvent e) -> Controller.switchForceClique());
        boutonSwitchSupprimer = creerBoutonSwitch("Supprimer");
        boutonSwitchSupprimer.setOnAction((ActionEvent e) -> Controller.switchSupprimerClique());
        boutonSwitchNoeud.setSelected(true);
        Controller.switchNoeudClique();
    }
    
    //methode qui crée les boutons switch facilement pour eviter la repetition
    public static ToggleButton creerBoutonSwitch(String nom){
        ToggleButton tglBtn = new ToggleButton(nom);
        tglBtn.setToggleGroup(groupeDeBouton);
        tglBtn.setPrefWidth(LARGEUR);
        tglBtn.getStyleClass().add("boutonSelectionGauche");
        return tglBtn;
    }
}