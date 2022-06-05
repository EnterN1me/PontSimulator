package fr.insa.guerchongranier.infos2.gui;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings("FieldMayBeFinal") // on ne prefere pas
public class MainFx extends Application {
    
    private static MainBorderPane ecran = new MainBorderPane();
    
    private static Scene scene;
    
    public static void main(String[] args) {
        Controller.initialisation();
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Logiciel de Simulation de Ponts");
        scene = new Scene(ecran);
        scene.getStylesheets().add("lightTheme.css");
        scene.setOnKeyPressed((KeyEvent e)-> Controller.keyPressed(e));
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        
    }
    
    public static Scene getScene(){
        return scene;
    }
    
    public static void explorateurFichier(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
    }
    
    public static MainBorderPane getEcran(){
        return ecran;
    }
    
}
