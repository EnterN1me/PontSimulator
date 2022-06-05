package fr.insa.guerchongranier.infos2.gui;

import fr.insa.guerchongranier.infos2.Barre;
import fr.insa.guerchongranier.infos2.Noeud;
import fr.insa.guerchongranier.infos2.Terrain;
import fr.insa.guerchongranier.infos2.Treillis;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author EnterN1me
 */
public class Dessin {

    private static Color couleurActuelDroite;
    
    private static final int WIDTHLINE = 3;;
    
    public static void chargerAffichage(Treillis treilliVisible){
        
        /*
        On Dessine les barres en premier
        */
        for(Terrain terrainADEssiner : treilliVisible.getListeDeTerrain()){
            dessinerBarre(terrainADEssiner, Color.LIME, 2*WIDTHLINE);
        }
        dessinerBarre(treilliVisible, Color.BLACK,WIDTHLINE);
        
        /*
        On dessine ensuite les noeuds ( avec les forces
        */
        dessinerNoeud(treilliVisible);
        for(Terrain terrainADEssiner : treilliVisible.getListeDeTerrain()){
            dessinerNoeud(terrainADEssiner);
        }
    }
    
    public static void dessinSimulation(Treillis treillisVisible){
        /*
        On Dessine les barres en premier
        */
        for(Terrain terrainADEssiner : treillisVisible.getListeDeTerrain()){
            dessinerBarre(terrainADEssiner, Color.LIME, 1);
        }
        
        for(Barre barreADessiner : treillisVisible.getListeDeBarre()){
            int nuanceRouge = Math.abs((int)barreADessiner.getCompression());
            int largeurBarre = 4;
            if(nuanceRouge>255){
                nuanceRouge = 255;
                largeurBarre = 15;
            }
            
            barreADessiner.dessin(Color.rgb(nuanceRouge,0,0),largeurBarre);
        }
        
        /*
        On dessine ensuite les noeuds ( avec les forces
        */
        for(Noeud noeudADessiner : treillisVisible.getListeDeNoeud()){
            noeudADessiner.dessinSimulation();
            
            if(!noeudADessiner.getForce().isNull()){
                drawLine((int)noeudADessiner.getPx(),(int)noeudADessiner.getPy(),(int)(noeudADessiner.getPx() - noeudADessiner.getForce().getVx()), (int)(noeudADessiner.getPy() - noeudADessiner.getForce().getVy()), Color.PINK, 4);
            }
        }
        for(Terrain terrainADEssiner : treillisVisible.getListeDeTerrain()){
            for(Noeud noeudADessiner : terrainADEssiner.getListeDeNoeud()){
            noeudADessiner.dessinSimulation();
            
            if(!noeudADessiner.getForce().isNull()){
                drawLine((int)noeudADessiner.getPx(),(int)noeudADessiner.getPy(),(int)(noeudADessiner.getPx() - noeudADessiner.getForce().getVx()), (int)(noeudADessiner.getPy() - noeudADessiner.getForce().getVy()), Color.PINK, 4);
                }
            }
        }
    }
    
    public static void dessinerBarre(Treillis treillisActuel, Color couleurBarre, int largeurBarre){
        for(Barre barreADessiner : treillisActuel.getListeDeBarre()){
            
            if((Controller.getBarreSelctionne()!=null)&&(barreADessiner.equal(Controller.getBarreSelctionne()))) {
                couleurActuelDroite = Color.RED;
                largeurBarre += 4;
            }
            else {
                couleurActuelDroite = couleurBarre;
            }
            
            barreADessiner.dessin(couleurActuelDroite,largeurBarre);
        }
    }
    
    public static void dessinerNoeud(Treillis treillisActuel){
        for(Noeud noeudADessiner : treillisActuel.getListeDeNoeud()){
            if ((Controller.getNoeudSelctionne()!=null)&&(noeudADessiner.equal(Controller.getNoeudSelctionne()))) noeudADessiner.dessinSiSelection();
            else noeudADessiner.dessin();
            
            if(!noeudADessiner.getForce().isNull()){
                drawLine((int)noeudADessiner.getPx(),(int)noeudADessiner.getPy(),(int)(noeudADessiner.getPx() - noeudADessiner.getForce().getVx()), (int)(noeudADessiner.getPy() - noeudADessiner.getForce().getVy()), Color.PINK, 3);
            }
        }
    }
    
    public static void drawPoint(int x, int y, Color couleur,int taille){
        GraphicsContext gc = ZoneDessin.getZone().getGraphicsContext2D();
        gc.setFill(couleur);
        gc.fillOval(x-((int)taille/2), y-((int)taille/2), taille, taille);
    }
    
    public static void drawSquare(int x, int y,Color couleur){
        GraphicsContext gc = ZoneDessin.getZone().getGraphicsContext2D();
        gc.setFill(couleur);
        gc.fillRect(x-11, y-11, 22, 22);
    }
    
    public static void drawRectangleX(int x, int y,Color couleur){
        GraphicsContext gc = ZoneDessin.getZone().getGraphicsContext2D();
        gc.setFill(couleur);
        gc.fillRect(x, y-11, 11, 22);
    }
    
    public static void drawRectangleY(int x, int y,Color couleur){
        GraphicsContext gc = ZoneDessin.getZone().getGraphicsContext2D();
        gc.setFill(couleur);
        gc.fillRect(x-11, y, 22, 11);
    }
    
    public static void drawLine(int memX, int memY, int x, int y, Color couleur, int largeur){
        GraphicsContext gc = ZoneDessin.getZone().getGraphicsContext2D();
        gc.setStroke(couleur);
        gc.setLineWidth(largeur);
        gc.strokeLine(memX,memY,x,y);
    }
    
    public static void clear(){
        GraphicsContext gc = ZoneDessin.getZone().getGraphicsContext2D();
        gc.clearRect(0, 0, ZoneDessin.getZone().getWidth(), ZoneDessin.getZone().getHeight());
    }
}
