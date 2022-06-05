package fr.insa.guerchongranier.infos2;

import static fr.insa.guerchongranier.infos2.gui.Dessin.drawPoint;
import static fr.insa.guerchongranier.infos2.gui.Dessin.drawSquare;
import javafx.scene.paint.Color;

/**
 *
 * @author lgranier01
 */

public class NoeudAppuiDouble extends NoeudAppui {
    // constructeur avec les mêmes attributs que noeud appui
    public NoeudAppuiDouble (double px, double py, int id, Vecteur2D force, Treillis newTreilliAssocie){
        super(px,py,id,force,newTreilliAssocie);
    } 
    
    public NoeudAppuiDouble (double px, double py, int id, Vecteur2D force){
        this(px,py,id,force,null);
    }
    
    // nombre pour le type de noeud de la classe noeud
     public static int nbrInconnues(){
        return 2;
    }
     
    @Override
    public int getType(){
        return 2;
    }
     
    @Override
    public String toString(){
        return "les coordonnées du Noeud Appui Double numéro "+this.getId()+" sont: ("+this.getPx()+";"+this.getPy()+")";
    }
    
    @Override
    public void dessin(){
        Color couleurActuelPoint = Color.LAWNGREEN;
        Color couleurActuelCarré = Color.GREEN; 
        int taillePoint = 20;
        drawSquare((int)this.getPx(), (int)this.getPy(), couleurActuelCarré);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelCarré, taillePoint+3);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelPoint, taillePoint);
    }
    
    @Override
    public String save(){
        return "NAD "+this.getId()+" "+this.getPx()+" "+this.getPy()+" "+this.getForce().save();
    }
    
    @Override
    public String saveTerrain(){
        return "NADT "+this.getId()+" "+this.getPx()+" "+this.getPy()+" "+this.getForce().save();
    }
    
    @Override
    public void dessinSimulation(){
        drawPoint((int)this.getPx(), (int)this.getPy(), Color.LAWNGREEN, 10);
    }
    
}
