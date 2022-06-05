package fr.insa.guerchongranier.infos2;

/**
 *
 * @author lgranier01
 */
public class Vecteur2D {
    

    private double valeurForce; // en Newton
    private double angle; // modulo 2 Pi
  
    private double vx;
    private double vy;
    
    private double norme;
    
    public double getValeurForce () {
        return this.valeurForce;
    }
    public double getAngle () {
        return this.angle;
    }
    public double getVx(){
        return this.vx;
    }
    public double getVy(){
        return this.vy;
    }
    public void setValeurForce (double valeurForce){
        this.valeurForce=valeurForce;
    }
    public void setAngle(double angle){
        this.angle=angle;
    }
    
    public Vecteur2D(double valeurForce, double direction){
        this.valeurForce= valeurForce;
        this.angle = direction;
    }
    
    public Vecteur2D(double xNoeudRelie, double yNoeudRelie, double xFinDuVecteur, double yFinDuVecteur){
        this.vx= -xFinDuVecteur+xNoeudRelie;
        this.vy= -yFinDuVecteur+yNoeudRelie;
    }
    
    public Vecteur2D(Noeud noeudRelie, double xFinDuVecteur, double yFinDuVecteur){
        this(noeudRelie.getPx(),noeudRelie.getPy(),xFinDuVecteur,yFinDuVecteur);
    }
    
    public boolean isNull(){
        return ((this.vx == 0)&&(this.vy == 0));
    }
    
    public double normeDeLaForce(){
        return this.norme=Math.sqrt(this.vx*this.vx+this.vy*this.vy);
    }
    
    @Override
    public String toString(){
        return ("vecteur de coordonnées ("+vx+";"+vy+")");
    }
    
    public String save(){
        return this.vx+" "+this.vy+" "; 
    }
}