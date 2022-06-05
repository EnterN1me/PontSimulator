/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.guerchongranier.infos2.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author EnterN1me
 */
@SuppressWarnings("FieldMayBeFinal") // on ne prefere pas
public class SavePopUp extends Stage {
    
    private Button btn;
    private TextArea textArea;
    
    public static void lancer(String[] args){
    }
    
    public SavePopUp() {
        this.setTitle("Entrez le nom du treillis");
        btn = new Button("Sauvegarde avec ce nom");
        textArea = new TextArea();
        textArea.setText("");
        textArea.setPrefSize(20,20);
        btn.setOnAction((e) -> {
            Controller.savePartTwo(textArea.getText().replaceAll(" ", "_"));
            this.close();
                });
        VBox boiteV = new VBox();
        boiteV.getChildren().addAll(textArea,btn);
        this.setScene(new Scene(boiteV));
        this.show();
    }
    
    public String getText(){
        return this.textArea.getText();
    }
}
