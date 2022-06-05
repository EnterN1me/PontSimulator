/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.guerchongranier.infos2.gui;

import javafx.scene.control.Alert;

/**
 *
 * @author EnterN1me
 */
public class AlertePopUp extends Alert{
    
    public AlertePopUp(){
        super(AlertType.ERROR);
        this.setContentText("Matrice non inversible !\n erreur dans le treillis !");
        this.showAndWait();
    }
}
