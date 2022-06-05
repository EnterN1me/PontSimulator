package fr.insa.guerchongranier.infos2;

import static fr.insa.guerchongranier.infos2.gui.Dessin.drawLine;
import javafx.scene.paint.Color;

/**
 *
 * @author Guerchon
 */

@SuppressWarnings("LeakingThisInConstructor") // c'est voulue !!
public class Barre {
    
    private int id;
    private double tractionMax;
    private double compressionMax;
    
    private double compression;
    
    private Noeud noeudDepart;
    private Noeud noeudArrive;
    
    // valeur des forces exercées sur la barre
    public double getTractionMax () {
        return this.tractionMax;
    }
    public double getCompressionMax () {
        return this.compressionMax;
    }
    //numéro de la barre
    public int getId(){
        return this.id;
    }
    // la barre est associé à un noeud d'arrivé et de départ
    public Noeud getNoeudDepart(){
        return this.noeudDepart;
    }
    public Noeud getNoeudArrive(){
        return this.noeudArrive;
    }
    
    public void setCompression(double compress){
        this.compression = compress;
    }
    
    public double getCompression(){
        return this.compression;
    }
    
    public void setNoeudDepart(Noeud n){
        this.noeudDepart=n;
    }
    public void setNoeudArrive(Noeud n){
        this.noeudArrive=n;
    }
    
    // détermine la taille de la barre en fonction des coordonnées du noeud de départ et l'arrivé
    public double taille(){
        return Math.sqrt(Math.pow(noeudDepart.getPx()-noeudArrive.getPx(),2)+Math.pow(noeudDepart.getPy()-noeudArrive.getPy(),2));       
    }
    // constructeur de la classe
    public Barre (int id, Noeud noeudDepart,Noeud noeudArrive, double tractionMax, double compressionMax){
        this.id=id;
        this.noeudDepart=noeudDepart;
        this.noeudArrive=noeudArrive;
        this.tractionMax=tractionMax;       
        this.compressionMax=compressionMax;
        noeudDepart.addBarreIncidente(this);
        noeudArrive.addBarreIncidente(this);
    }
    
    public Barre(int id, Noeud noeudDepart,Noeud noeudArrive){
        this(id ,noeudDepart,noeudArrive,0,0);
    }
    
    // constructeur avec des paramètres fixés
    public Barre(Noeud noeudDepart,Noeud noeudArrive){
        this(-1,noeudDepart,noeudArrive);
    }
    // met en relation le noeud de départ et d'arrivé de la barre
    public static Noeud noeudOppose(Barre b, Noeud n){
        if(n.getId()==b.noeudDepart.getId()) return b.noeudArrive;
        else return b.noeudDepart;
    }
    
    public boolean hasNoeud(Noeud n){
        return ((this.noeudDepart.equal(n))||(this.noeudArrive.equal(n)));
    }
    
    // calcul l'angle d'inclinaison de la barre
    public double angle(Noeud n){
        return Math.acos((Math.abs(n.getPx()-noeudOppose(this,n).getPx()))/taille());       
    }
    
    public boolean clickDansLaHitBoxDeLaBarre(int coordonneeXDuClick, int coordonneeYDuClick){
        
        //droite de la barre:
        double pente = Calcul.penteDroiteAffine(noeudDepart.getPx(), noeudDepart.getPy(), noeudArrive.getPx(), noeudArrive.getPy());
        double ordonneeOrigine = Calcul.ordonneeALOrigine(noeudDepart.getPx(), noeudDepart.getPy(),pente);
        
        //si le click est entre les deux droite parrallele a la barre (plus ou moins 20)
        boolean auDessusDeLaDroiteParalleleBas = coordonneeYDuClick > (pente*coordonneeXDuClick + ordonneeOrigine -25);
        boolean auDessousDeLaDroiteParalleleHaut = coordonneeYDuClick < (pente*coordonneeXDuClick + ordonneeOrigine +25);
        
        //droite perpendiculaire a la droite : a*a' = -1 avec a et a' les pentes des 2 droites =>a'= -1/a
        double pentePerpendiculaire = (-1) / pente ;
        
        //ordonnée pour que ces droites passent par les extremité de la barre
        double ordonneeOriginePerpendicualireDepart = Calcul.ordonneeALOrigine(noeudDepart.getPx(), noeudDepart.getPy(),pentePerpendiculaire);
        double ordonneeOriginePerpendicualireArrive = Calcul.ordonneeALOrigine(noeudArrive.getPx(), noeudArrive.getPy(),pentePerpendiculaire);
        
        boolean aDroiteDeLaPerpendiculaireDeGauche = coordonneeYDuClick > (pentePerpendiculaire*coordonneeXDuClick + Math.min(ordonneeOriginePerpendicualireDepart,ordonneeOriginePerpendicualireArrive));
        boolean aGaucheDeLaPerpendiculaireDeDroite = coordonneeYDuClick < (pentePerpendiculaire*coordonneeXDuClick + Math.max(ordonneeOriginePerpendicualireDepart,ordonneeOriginePerpendicualireArrive));
        
        //il faut que toute les conditions soient vraies pour que le point soit dans la hitbox
        return ((auDessousDeLaDroiteParalleleHaut && auDessusDeLaDroiteParalleleBas) 
                    && (aDroiteDeLaPerpendiculaireDeGauche && aGaucheDeLaPerpendiculaireDeDroite));
    }
    
    public boolean equal(Barre barreAComparer){
        return ((this.getNoeudArrive().equal(barreAComparer.getNoeudArrive()))&&(this.getNoeudDepart().equal(barreAComparer.getNoeudDepart()))
                ||
                (this.getNoeudArrive().equal(barreAComparer.getNoeudDepart()))&&(this.getNoeudDepart().equal(barreAComparer.getNoeudArrive())));
    }
    
    public String save(){
        return "B "+this.getNoeudArrive().getId()+" "+this.getNoeudDepart().getId();
    }
    
    public String saveTerrain(){
        return "BT "+this.getNoeudArrive().getId()+" "+this.getNoeudDepart().getId();
    }
    
    @Override
    public String toString(){
        return ("la barre "+id+" qui part du noeud "+noeudDepart+" et arrive au noeud "+noeudArrive+" fait "+taille());
    }
    
    public void dessin(Color couleur, int largeur){
        int xDepart = (int)getNoeudDepart().getPx();
        int yDepart = (int)getNoeudDepart().getPy();
        int xArrive = (int)getNoeudArrive().getPx();
        int yArrive = (int)getNoeudArrive().getPy();
        
        drawLine(xDepart,yDepart,xArrive,yArrive, couleur, largeur);
    }
    
    public void dessionSimulation(){
        
    }
}
