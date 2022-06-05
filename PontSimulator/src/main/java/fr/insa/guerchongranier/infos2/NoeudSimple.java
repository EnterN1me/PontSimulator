package fr.insa.guerchongranier.infos2;

import static fr.insa.guerchongranier.infos2.gui.Dessin.drawPoint;
import static fr.insa.guerchongranier.infos2.gui.Dessin.drawSquare;
import javafx.scene.paint.Color;

/**
 *
 * @author lgranier01
 */

public class NoeudSimple extends Noeud {
    
    public NoeudSimple (double px, double py, int id, Vecteur2D force, Treillis newTreilliAssocie){
        super(px,py,id,force, newTreilliAssocie);
    } 
    
    public NoeudSimple (double px, double py, int id, Vecteur2D force){
        this(px,py,id,force, null);
    }
    
    public NoeudSimple( double px, double py, Treillis newTreilliAssocie){
        this(px,py,-1, new Vecteur2D(0,0), newTreilliAssocie);
    }
    

    // nombre pour le type de noeud de la classe noeud
    public static int nbrInconnues(){
        return 0;
    }
    
    @Override
    public int getType(){
        return 0;
    }
    
    @Override
    public String toString(){
        return "Noeud Simple numéro "+this.getId()+" de coordonnées : ("+this.getPx()+";"+this.getPy()+")" + " de force "+this.getForce();
    }
    
    @Override
    public void dessin(){
        Color couleurActuelPoint = Color.MIDNIGHTBLUE;
        Color couleurActuelCarré = Color.AQUA;
        int taillePoint = 20;
        drawSquare((int)this.getPx(), (int)this.getPy(), couleurActuelCarré);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelCarré, taillePoint+3);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelPoint, taillePoint);
    }
    
    @Override
    public String save(){
        return "NS "+this.getId()+" "+this.getPx()+" "+this.getPy()+" "+this.getForce().save()+" ";
    }
}
