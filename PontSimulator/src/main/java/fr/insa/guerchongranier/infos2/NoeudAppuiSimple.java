package fr.insa.guerchongranier.infos2;

import fr.insa.guerchongranier.infos2.gui.Dessin;
import static fr.insa.guerchongranier.infos2.gui.Dessin.drawPoint;
import static fr.insa.guerchongranier.infos2.gui.Dessin.drawSquare;
import javafx.scene.paint.Color;

/**
 *
 * @author lgranier01
 */
public class NoeudAppuiSimple extends NoeudAppui {
    
    private char axeVerrouille;
    
    public NoeudAppuiSimple (double px, double py, int id, Vecteur2D force, Treillis newTreilliAssocie){
        super(px,py,id,force, newTreilliAssocie);
        axeVerrouille = 'x';
    } 
    
    public NoeudAppuiSimple (double px, double py, int id, char dir, Vecteur2D force){
        super(px,py,id,force, null);
        axeVerrouille = dir;
    } 
    
    public NoeudAppuiSimple (double px, double py, int id, Vecteur2D force){
        this(px,py,id,'x',force);
    }
   
    @Override
    public char getAxeVerrouille(){
        return this.axeVerrouille;
    }
    
    @Override
    public int getType(){
        return 1;
    }
    
    @Override
    public void setAxeVerrouille(char direction){
        switch (direction){
            case 'x' :
            case 'y' :
                this.axeVerrouille = direction;
                return;
            default:
                System.out.println(new Exception("mauvais caractère, doit etre x ou y"));
        }
    }
    
    // nombre pour le type de noeud de la classe noeud
     public static int nbrInconnues(){
        return 1;
    }
     
    @Override
    public String toString(){
        return "les coordonnées du Noeud Appui Simple numéro "+this.getId()+" sont: ("+this.getPx()+";"+this.getPy()+")";
    }
    
    @Override
    public void dessin(){
        Color couleurActuelPoint = Color.YELLOWGREEN;
        Color couleurActuelCarré = Color.YELLOW;
        Color couleurVerrouillage = Color.ORANGE;
        
        int taillePoint = 20;
        drawSquare((int)this.getPx(), (int)this.getPy(), couleurActuelCarré);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelCarré, taillePoint+3);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelPoint, taillePoint);
        if(this.axeVerrouille=='x') Dessin.drawRectangleX((int)this.getPx(), (int)this.getPy(), couleurVerrouillage);
        else if(this.axeVerrouille=='y') Dessin.drawRectangleY((int)this.getPx(), (int)this.getPy(), couleurVerrouillage);
    }
    
    @Override
    public void dessinSiSelection(){
        Color couleurActuelPoint = Color.RED;
        Color couleurActuelCarré = Color.BROWN;
        Color couleurVerrouillage = Color.BROWN;
        int taillePoint = 22;
        drawSquare((int)this.getPx(), (int)this.getPy(), couleurActuelCarré);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelCarré, taillePoint+3);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelPoint, taillePoint);
        if(this.axeVerrouille=='x') Dessin.drawRectangleX((int)this.getPx(), (int)this.getPy(), couleurVerrouillage);
        else if(this.axeVerrouille=='y') Dessin.drawRectangleY((int)this.getPx(), (int)this.getPy(), couleurVerrouillage);
    }
    
    @Override
    public String save(){
        return "NAS "+this.getId()+" "+this.getPx()+" "+this.getPy()+" "+this.axeVerrouille +" " +this.getForce().save();
    }
    
    
    
    @Override
    public void dessinSimulation(){
        drawPoint((int)this.getPx(), (int)this.getPy(), Color.YELLOW, 10);
    }
    
}
