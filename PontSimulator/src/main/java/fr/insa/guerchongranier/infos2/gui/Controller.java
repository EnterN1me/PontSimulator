package fr.insa.guerchongranier.infos2.gui;

import fr.insa.guerchongranier.infos2.Barre;
import fr.insa.guerchongranier.infos2.Noeud;
import fr.insa.guerchongranier.infos2.NoeudAppuiDouble;
import fr.insa.guerchongranier.infos2.NoeudAppuiSimple;
import fr.insa.guerchongranier.infos2.NoeudSimple;
import fr.insa.guerchongranier.infos2.Systemes;
import fr.insa.guerchongranier.infos2.Terrain;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import fr.insa.guerchongranier.infos2.Treillis;
import fr.insa.guerchongranier.infos2.Vecteur2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller {
    
    private static Noeud noeudSelectione;
    private static Barre barreSelectione;
    
    private static Treillis treillisActuel;
    private static Terrain terrainActuel;
    
    public static void initialisation(){
        treillisActuel = new Treillis();
    }
    
    //pour ZoneDessin : fais une action lorsque l'on clique !!
    public static void clicDansZoneDessin(MouseEvent t) {
        
        int x =(int)t.getX();
        int y = (int)t.getY();
        //MainBorderPane.affichageAide.setText("\t"+x+" | "+y);
        
        /*
        *bouton Noeud
        *
        *   -selectionner un noeud
        *   -selectionner une barre    
        *   -creer un Noeud
         */
        if((BarreGauche.boutonSwitchNoeud.isSelected())) {
            
            //deselectionne la barre
            barreSelectione = null;
            
            //selectionne un Noeud
            if (treillisActuel.pointDansLaHitBoxDunDesNoeud(x,y)){
                    noeudSelectione = treillisActuel.noeudTouche(x, y);
                    BarreBas.setText("vous avez selectionné : "+noeudSelectione);
            }
            
            //selectionne une barre
            else if (treillisActuel.pointDansLaHitBoxDuneDesBarres(x, y)){
                if((terrainActuel!=null)&&(terrainActuel.pointDansLaHitBoxDuneDesBarres(x, y))){
                    treillisActuel.addNoeud(x,y,"Simple");
                    noeudSelectione = treillisActuel.getDernierNoeud();
                    BarreBas.setText("vous avez créé : "+noeudSelectione);
                }
                else{
                barreSelectione = treillisActuel.barreTouche(x,y);
                noeudSelectione = null;
                BarreBas.setText("Wow ! C'est un magnifique Treillis :) !");
                }
            }
            
            //ajoute un noeud simple
            else {
                treillisActuel.addNoeud(x,y,"");
                noeudSelectione = treillisActuel.getDernierNoeud();
                BarreBas.setText("vous avez créé : "+noeudSelectione);
            }
        }
        
        
        /*
        *bouton Poutre
        *   -selectionner un noeud
        *   -creer une barre ou selectionner un noeud
        *   -selectionner une barre    
         */
        else if(BarreGauche.boutonSwitchPoutre.isSelected()){
            
            //deselectionne la barre
            barreSelectione = null;
            
            //selectionne un noeud || cree une barre
            if (treillisActuel.pointDansLaHitBoxDunDesNoeud(x,y)){
                
                //si aucun noeud selectionnée, le selectionner
                if(noeudSelectione==null) {
                    noeudSelectione = treillisActuel.noeudTouche(x, y);
                    BarreBas.setText("vous avez selectionné : "+noeudSelectione);
                }
                //si le noeud touché est different du noeud selectionnée, crée une barre (puis selectionne le noeud touché)
                else if (!noeudSelectione.equal(treillisActuel.noeudTouche(x,y))) {
                    treillisActuel.addBarre(noeudSelectione,treillisActuel.noeudTouche(x, y));
                    treillisActuel.addNoeud(noeudSelectione);
                    treillisActuel.addNoeud(treillisActuel.noeudTouche(x, y));
                    noeudSelectione=treillisActuel.noeudTouche(x, y);
                    BarreBas.setText("vous avez crée une Barre !");
                }
            }
            
            //selectionne une barre
            else if (treillisActuel.pointDansLaHitBoxDuneDesBarres(x, y)){
                barreSelectione = treillisActuel.barreTouche(x,y);
                noeudSelectione = null;
                BarreBas.setText("vous avez selectionné : "+barreSelectione);
            }
            
            //déselectionne le noeud et la barre
            else {
                noeudSelectione = null;
                barreSelectione = null;
                BarreBas.setText("Wow ! C'est un magnifique Treillis :) !");
            }
        }
        
        /*
        *bouton Terrain
        *
        *   -selectionne un Noeud
        *   -crée une barre
        *   -selectionner une barre    
         */
        else if(BarreGauche.boutonSwitchTerrain.isSelected()){
            
            //selectionne un noeud || crée une barre de terrain
            if (treillisActuel.pointDansLaHitBoxDunDesNoeud(x,y)){
                if(noeudSelectione==null) {
                    noeudSelectione = treillisActuel.noeudTouche(x, y);
                    BarreBas.setText("vous avez selectionné : "+noeudSelectione);
                }
                //si le noeud touché est different du noeud selectionner et les 2 appartiennent au terrain, crée une barre
                else if ((terrainActuel!=null)&&(terrainActuel.noeudTouche(x,y)!=null)&&(!noeudSelectione.equal(terrainActuel.noeudTouche(x,y)))&&(terrainActuel.possedeNoeud(noeudSelectione))) {
                    terrainActuel.addBarre(noeudSelectione,terrainActuel.noeudTouche(x, y));        
                    noeudSelectione=treillisActuel.noeudTouche(x, y);
                    BarreBas.setText("vous avez crée une Barre !");
                }
                else{
                    noeudSelectione = treillisActuel.noeudTouche(x, y);
                    BarreBas.setText("vous avez selectionné : "+noeudSelectione);
                }
                
            }
            
            //selectionne une barre
            else if (treillisActuel.pointDansLaHitBoxDuneDesBarres(x, y)){
                barreSelectione = treillisActuel.barreTouche(x,y);
                noeudSelectione = null;
                BarreBas.setText("vous avez selectionné : "+barreSelectione);
            }
            
            //crée un nouveau terrain
            else if(terrainActuel==null){
                terrainActuel = new Terrain(treillisActuel);
                terrainActuel.addNoeud(x,y);
                noeudSelectione = terrainActuel.getDernierNoeud();
                BarreBas.setText("vous avez créé : "+noeudSelectione);
            }
            
            //crée un noeud
            else{
                terrainActuel.addNoeud(x,y);
                noeudSelectione = terrainActuel.getDernierNoeud();
                BarreBas.setText("vous avez créé : "+noeudSelectione);
            }
        }
        
        /*
        *bouton Force
        *
        * -cree une force
        * - selectionne un point
        */
        else if(BarreGauche.boutonSwitchForce.isSelected()){
            
            barreSelectione = null;
            
            if (treillisActuel.pointDansLaHitBoxDunDesNoeud(x,y)){
                noeudSelectione = treillisActuel.noeudTouche(x, y);
                BarreBas.setText("vous avez selectionné : "+noeudSelectione);
            }
            
            else if (noeudSelectione!=null){
                noeudSelectione.setForce(new Vecteur2D(noeudSelectione,x,y));
                BarreBas.setText("vous avez ajouté une force à votre Noeud ! Supprimez la avec la touche n ");
            }
        }
        
        /*
        *bouton Supprimer
        *
        *   -supprimer un Noeud et toute ses barres incidentes
        *   -supprime une barre
        *   
         */
        else if(BarreGauche.boutonSwitchSupprimer.isSelected()){
            
            if (treillisActuel.pointDansLaHitBoxDunDesNoeud(x,y)){
                noeudSelectione = treillisActuel.noeudTouche(x, y);
                for(Barre barreASupprimer : noeudSelectione.getListeDesBarresIncidentes()){
                    treillisActuel.removeBarre(barreASupprimer);
                    if (terrainActuel!= null) terrainActuel.removeBarre(barreASupprimer);
                }
                treillisActuel.removeNoeud(noeudSelectione);
                if (terrainActuel!=null) terrainActuel.removeNoeud(noeudSelectione);
                BarreBas.setText("vous avez supprimé un Noeud :/");
            }
            else if (treillisActuel.pointDansLaHitBoxDuneDesBarres(x, y)){
                treillisActuel.removeBarre(treillisActuel.barreTouche(x,y));
                if (terrainActuel!=null) terrainActuel.removeBarre(treillisActuel.barreTouche(x,y));
                BarreBas.setText("vous avez supprimé une Barre :/");
            }
            
            noeudSelectione = null;
        }
        
        
        /*
        *pas de bouton
        *
        *   -creer une barre ou selectionner un noeud
        *   -selectionner une barre    
        *
         */
        else {
            
            barreSelectione = null;
            noeudSelectione = null;
            
            if (treillisActuel.pointDansLaHitBoxDunDesNoeud(x,y)){
                noeudSelectione = treillisActuel.noeudTouche(x, y);
                BarreBas.setText("vous avez selectionné : "+noeudSelectione);
            }
            
            else if (treillisActuel.pointDansLaHitBoxDuneDesBarres(x, y)){
                barreSelectione = treillisActuel.barreTouche(x,y);
                BarreBas.setText("vous avez selectionné : "+barreSelectione);
            }
            
        }
       
        Dessin.clear();
        Dessin.chargerAffichage(treillisActuel);
    }
    
    public static void keyPressed(KeyEvent key){
        System.out.println(key.getCode());
        
        if(noeudSelectione!=null){
        switch (key.getCode()){
            case D :
                noeudSelectione.setPx(noeudSelectione.getPx()+3);
                break;
                    
            case Q :
                noeudSelectione.setPx(noeudSelectione.getPx()-3);
                break;
            case S :
                noeudSelectione.setPy(noeudSelectione.getPy()+3);
                break;
            case Z :
                noeudSelectione.setPy(noeudSelectione.getPy()-3);
                break;
            
            case X :
                if (noeudSelectione.getClass() == NoeudAppuiSimple.class) noeudSelectione.setAxeVerrouille('x');
                break;
            case Y :
                if (noeudSelectione.getClass() == NoeudAppuiSimple.class) noeudSelectione.setAxeVerrouille('y');
                break;
                
            case DELETE :
                
                for(Barre barreASupprimer : noeudSelectione.getListeDesBarresIncidentes()){
                    treillisActuel.removeBarre(barreASupprimer);
                    if (terrainActuel!=null) terrainActuel.removeBarre(barreASupprimer);
                }
                treillisActuel.removeNoeud(noeudSelectione);
                if (terrainActuel!=null) terrainActuel.removeNoeud(noeudSelectione);
                
            case E :
            case ESCAPE :
                noeudSelectione =null;
                break;
            case N :
                noeudSelectione.setForce(new Vecteur2D(0,0));
                break;
            
        }
        Dessin.clear();
        Dessin.chargerAffichage(treillisActuel);
        }
    }
    
    
    public static void switchNoeudClique(){
        BarreBas.setText("Placer un Noeud en cliquant sur l'écran");
    }
    
    public static void switchPoutreClique(){
        BarreBas.setText("Placer une Poutre en selectionnant 2 Noeuds");
    }
    
    public static void switchForceClique(){
        BarreBas.setText("Placer une Force en selectionnant le point voulu, puis en cliquant autre part sur l'ecran (echelle 10 pixel = ? Newton)");
    }
    
    public static void switchSupprimerClique(){
        BarreBas.setText("cliquer sur un Noeud pour le supprimer lui et toutes ses barres incidentes");
        noeudSelectione = null;
        Dessin.clear();
        Dessin.chargerAffichage(treillisActuel);
    }
    
    public static void startSimulation(){
        try{
        Systemes sys = new Systemes(treillisActuel);
        Dessin.clear();
        Dessin.dessinSimulation(treillisActuel);
        } catch (Exception e){
            AlertePopUp ap = new AlertePopUp();
        }
    }
    
    public static void deleteAllAction(){
        MainFx.getEcran().getCentre().bombeGif();
        treillisActuel = new Treillis();
        terrainActuel = null;
    }
    
    public static void endDeleteAllAction(){
        Dessin.clear(); 
    }
    
    public static void removeBombe(){
        //MainFx.getEcran().getCentre().retireBombe();
    }
    
    //pour BarreHaut : ferme la fenetre
    public static void closeScreen() {
        Platform.exit();
    }
    
    public static Noeud getNoeudSelctionne(){
        return noeudSelectione;
    }
    
    public static Barre getBarreSelctionne(){
        return barreSelectione;
    }
    
    public static void savePartOne(){
      SavePopUp savepu = new SavePopUp();
    }
      
     
    public static void savePartTwo(String id){
      
      treillisActuel.setId(id);
      
      try{
        FileWriter out = new FileWriter(treillisActuel.getId()+".treilli",false);
        BufferedWriter bw = new BufferedWriter(out);
        bw.write(treillisActuel.save());
        bw.close();
        out.close();
      } catch (OutOfMemoryError e){
          System.out.println(e);
      }
      catch (Exception e){
          System.out.println("ERREUR NON PREVU ::" + e);
      }
    }
    
    public static void open(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichier Treilli", "*.treilli"));
        fileChooser.setTitle("Selectionne un treillis");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(null);
        
        Treillis treilliCree = new Treillis();
        Terrain terrainAssocie = new Terrain(treilliCree);
        treilliCree.addTerrain(terrainAssocie);
        int idMaxTemp = 0;
        
        
        try{
        
        BufferedReader in = new BufferedReader(new FileReader(file.getAbsolutePath()));
        
        String ligneActuel = "";    
            
        ligneActuel = in.readLine();
        if (ligneActuel == null) throw new Error("C'est nul !");
        StringTokenizer ligneBrise = new StringTokenizer(ligneActuel," ");
        
        if(!ligneBrise.nextToken().equals("Treillis")) {
            System.out.println(new Error("fichier corrompu"));
            return;
        }
        treilliCree.setId(ligneBrise.nextToken());
        
        int idTemporaire;
        
        while((ligneActuel = in.readLine())!= null){
            StringTokenizer ligneSpliteParEspace = new StringTokenizer(ligneActuel, " ");
            switch (ligneSpliteParEspace.nextToken()){
                case "N" :
                case "NA" :
                    System.out.println(new Error("Ligne corrompue"));
                    break;
                case "NS" :
                    idTemporaire = Integer.parseInt(ligneSpliteParEspace.nextToken()); 
                    idMaxTemp = Math.max(idMaxTemp,idTemporaire);
                    treilliCree.addNoeud(new NoeudSimple(Double.parseDouble(ligneSpliteParEspace.nextToken()), Double.parseDouble(ligneSpliteParEspace.nextToken()), idTemporaire, new Vecteur2D(Double.parseDouble(ligneSpliteParEspace.nextToken()), Double.parseDouble(ligneSpliteParEspace.nextToken()))));
                    break;
                case "NAD":
                    idTemporaire=  Integer.parseInt(ligneSpliteParEspace.nextToken());
                    idMaxTemp = Math.max(idMaxTemp,idTemporaire);
                    treilliCree.addNoeud(new NoeudAppuiDouble(Double.parseDouble(ligneSpliteParEspace.nextToken()),Double.parseDouble(ligneSpliteParEspace.nextToken()),idTemporaire,new Vecteur2D(Double.parseDouble(ligneSpliteParEspace.nextToken()), Double.parseDouble(ligneSpliteParEspace.nextToken())), null));
                    break;
                case "NAS":
                    idTemporaire=  Integer.parseInt(ligneSpliteParEspace.nextToken());
                    idMaxTemp = Math.max(idMaxTemp,idTemporaire);
                    treilliCree.addNoeud(new NoeudAppuiSimple(Double.parseDouble(ligneSpliteParEspace.nextToken()),Double.parseDouble(ligneSpliteParEspace.nextToken()),idTemporaire,ligneSpliteParEspace.nextToken().charAt(0) ,new Vecteur2D(Double.parseDouble(ligneSpliteParEspace.nextToken()), Double.parseDouble(ligneSpliteParEspace.nextToken()))));
                    break;
                case "B":
                    treilliCree.addBarre(Integer.parseInt(ligneSpliteParEspace.nextToken()),Integer.parseInt(ligneSpliteParEspace.nextToken()));
                    break;
                case "BT":
                    terrainAssocie.addBarre(Integer.parseInt(ligneSpliteParEspace.nextToken()),Integer.parseInt(ligneSpliteParEspace.nextToken()));
                    break;
                case "NADT" : 
                    idTemporaire=  Integer.parseInt(ligneSpliteParEspace.nextToken());
                    idMaxTemp = Math.max(idMaxTemp,idTemporaire);
                    terrainAssocie.addNoeud(new NoeudAppuiDouble(Double.parseDouble(ligneSpliteParEspace.nextToken()),Double.parseDouble(ligneSpliteParEspace.nextToken()),idTemporaire,new Vecteur2D(Double.parseDouble(ligneSpliteParEspace.nextToken()), Double.parseDouble(ligneSpliteParEspace.nextToken())), null));
                    break;
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
        treillisActuel = treilliCree;
        treillisActuel.setMaxIdNoeud(idMaxTemp);
        Dessin.clear();
        Dessin.chargerAffichage(treillisActuel);
    }
}
