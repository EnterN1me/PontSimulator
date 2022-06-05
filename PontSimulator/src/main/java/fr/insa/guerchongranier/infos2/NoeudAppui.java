package fr.insa.guerchongranier.infos2;

/**
 *
 * @author lgranier01
 */

@SuppressWarnings("override") // c'est voulontaire !
public abstract class NoeudAppui extends Noeud {
    
    public int getType(){
        return -1;
    }
    
    public char getAxeVerrouille(){ return 'o';}
    public void setAxeVerrouille(char direction){}
    
    public NoeudAppui (double px, double py, int id, Vecteur2D force, Treillis newTreilliAssocie){
        super(px,py,id,force, newTreilliAssocie);
    } 
    
    public void dessin(){}
    
    public String save(){
        return "NA void ";
    }
    
    public String saveTerrain(){
        return "NAT void ";
    }
    
}
