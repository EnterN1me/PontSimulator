package fr.insa.guerchongranier.infos2;

import static fr.insa.guerchongranier.infos2.gui.Dessin.drawPoint;
import static fr.insa.guerchongranier.infos2.gui.Dessin.drawSquare;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.paint.Color;

public abstract class Noeud {

    private double px;
    private double py;
    private int id;
    private Vecteur2D force;
    private Treillis treilliAssocie;

    private ArrayList<Barre> listeDesBarresIncidentes;
    private static int lastId = 0;

    // obtient les coordonnées du point en x et y
    public double getPx() {
        return this.px;
    }

    public double getPy() {
        return this.py;
    }

    // numéro du noeud
    public int getId() {
        return this.id;
    }
    
    public Vecteur2D getForce(){
        return this.force;
    }
    public void setForce(Vecteur2D force){
        this.force=force;
    }

    // permet de définir les coordonnées en x et y
    public void setPx(double px) {
        this.px = px;
    }

    public void setPy(double py) {
        this.py = py;
    }

    // ajoute une barre à la liste
    public void addBarreIncidente(Barre b) {
        this.listeDesBarresIncidentes.add(b);

    }

    public ArrayList<Barre> getListeDesBarresIncidentes() {
        return this.listeDesBarresIncidentes;
    }
    
    
    public static Noeud entreeNoeud(){
        
        int typeDeNoeud; double nx, ny;
        System.out.println("donner le type du noeud");      
        Scanner sc = new Scanner(System.in);  
        typeDeNoeud=sc.nextInt();
        System.out.println("donner l'abscisse x et l'ordonnée y");
        nx=sc.nextDouble();
        ny=sc.nextDouble();
        
        if (typeDeNoeud==0){
            return new NoeudSimple(nx,ny,-1,new Vecteur2D(0,0));
        }else if (typeDeNoeud==1){
            return new NoeudAppuiSimple(nx,ny,-1,new Vecteur2D(0,0));
        } else{
            return new NoeudAppuiDouble(nx,ny,-1,new Vecteur2D(0,0));
        }       
    }
    
    
    public Noeud(double px, double py, int id, Vecteur2D force, Treillis newTreilliAssocie){
        this.px = px;
        this.py = py;
        this.id = id;
        this.force = force;
        this.treilliAssocie = newTreilliAssocie;

        listeDesBarresIncidentes = new ArrayList<>();
    }

    // constructeur de la classe
    public Noeud(double px, double py, int id, Vecteur2D force) {
        this(px,py,id,force,null);
    }

    //constructeur avec deux paramètres fixés
    public Noeud(double px, double py) {
        this(px, py, lastId++, new Vecteur2D(0, 0));
    }

    //renvoi vrai si les coordonnées données se situe dans le carré de 20 par 20 autour du point 
    //par rapport a la taille du point sur l'interface
    public boolean dansLaHitBox(int coordonneeXDuClic, int coordonneeYDuClic) {
        return ((Math.abs(this.getPx() - coordonneeXDuClic) < 20) && (Math.abs(this.getPy() - coordonneeYDuClic) < 20));
    }

    public boolean equal(Noeud noeudCompare) {
        return (this.id == noeudCompare.id);
    }
    
    public int getType(){
        return -1;
    }
    
    public void setTreillisAssocie(Treillis t){
        this.treilliAssocie = t;
    }
    
    public char getAxeVerrouille(){ return 'o';}
    public void setAxeVerrouille(char direction){}

    @Override
    public String toString() {
        return ("noeud numéro " + id + " de coordonnées (" + px + ";" + py + "), et de force " + force);
    }
    
    public String save(){
        return "N void ";
    }
    
    public String saveTerrain(){
        return "NT void ";
    }
    
    public void dessin(){}
    
    public void dessinSiSelection(){
        Color couleurActuelPoint = Color.RED;
        Color couleurActuelCarré = Color.BROWN;
        int taillePoint = 22;
        drawSquare((int)this.getPx(), (int)this.getPy(), couleurActuelCarré);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelCarré, taillePoint+3);
        drawPoint((int)this.getPx(), (int)this.getPy(), couleurActuelPoint, taillePoint);
    }
   
    public void dessinSimulation(){
        drawPoint((int)this.getPx(), (int)this.getPy(), Color.BLACK, 10);
    }

}
