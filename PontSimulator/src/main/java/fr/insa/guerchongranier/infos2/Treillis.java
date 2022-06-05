package fr.insa.guerchongranier.infos2;

import java.util.ArrayList;

/**
 *
 * @author lgranier01
 */

// On ne souhaite pas mettre les 3 ArrayList en Final car il n'y a pas de raison de faire cela
@SuppressWarnings("FieldMayBeFinal")
public class Treillis {
    
    private ArrayList<Noeud> listeDeNoeud;
    private ArrayList<Barre> listeDeBarre;
    private ArrayList<Terrain> listeDesTerrainRelie;
    private String id = "Nom";
    
    private int maxIdNoeud;
    private int maxIdBarre;
    
    //constructeur
    public Treillis(){
        listeDeNoeud = new ArrayList<Noeud>();
        listeDeBarre = new ArrayList<Barre>();
        listeDesTerrainRelie = new ArrayList<Terrain>();
    }
    
    /*
    * methode pour savoir si un point de lecran donnée par ses 2 coordonnées touche 1 des hitbox des points ou non
    */
    public boolean pointDansLaHitBoxDunDesNoeud(int coordonneeEnX, int coordonneeEnY){
        return (noeudTouche(coordonneeEnX,coordonneeEnY)!=null);
    }
    
    public Noeud noeudTouche(int coordonneeEnX, int coordonneeEnY){
        for( Noeud noeud : listeDeNoeud){
            if(noeud.dansLaHitBox(coordonneeEnX, coordonneeEnY)) return noeud;
        }
        for (Terrain terrain : this.listeDesTerrainRelie){
            for (Noeud noeud : terrain.getListeDeNoeud()){
                if(noeud.dansLaHitBox(coordonneeEnX, coordonneeEnY)) return noeud;
            }
        }
        return null;
    }
    
    
    //hit box des barres puis quelle barre est touché
    public boolean pointDansLaHitBoxDuneDesBarres(int coordonneeEnX, int coordonneeEnY){
        return (barreTouche(coordonneeEnX,coordonneeEnY)!=null);
    }
    
    public Barre barreTouche(int coordonneeEnX, int coordonneeEnY){
        for( Barre barre : listeDeBarre){
            if(barre.clickDansLaHitBoxDeLaBarre(coordonneeEnX, coordonneeEnY)) return barre;
        }
        for (Terrain terrain : this.listeDesTerrainRelie){
            for (Barre barre : terrain.getListeDeBarre()){
                if(barre.clickDansLaHitBoxDeLaBarre(coordonneeEnX, coordonneeEnY)) return barre;
            }
        }
        return null;
    }
    
    /*
    *getter and setter
    *
    */
    public ArrayList<Noeud> getListeDeNoeud(){
        return this.listeDeNoeud;
    }
    
    public ArrayList<Barre> getListeDeBarre(){
        return this.listeDeBarre;
    }
    
    public ArrayList<Terrain> getListeDeTerrain(){
        return this.listeDesTerrainRelie;
    }
    
    public void addBarre (Barre b){
        try{
            if(!hasBarre(b)) this.listeDeBarre.add(b);
            else throw new AlreadyThereException(b);
        }
        catch (AlreadyThereException exept){
            System.out.println(exept);
        }
    }
    
    public void addBarre (Noeud noeudDepart, Noeud noeudArrive){
        Barre temp = new Barre(++this.maxIdBarre,noeudDepart,noeudArrive);
        this.addBarre(temp);
    }
    
    public void addBarre(int idDepart, int idArrive){
        this.addBarre(this.getNoeud(idDepart), this.getNoeud(idArrive));
    }
    
    public void addNoeud (Noeud n){
        try {
            if(!hasNoeud(n)) {
                this.listeDeNoeud.add(n);
                n.setTreillisAssocie(this);
            }
            else throw new AlreadyThereException(n);
            
        }catch (AlreadyThereException exept){
            System.out.println(exept);
        }
    }
    
    
    public void addNoeud(double px, double py, String arg){
        switch (arg){
            case "Double" :
                this.listeDeNoeud.add(new NoeudAppuiDouble(px, py, ++this.maxIdNoeud,new Vecteur2D(0,0),this));
                return;
            case "Simple" :
                this.listeDeNoeud.add(new NoeudAppuiSimple(px,py,++this.maxIdNoeud,new Vecteur2D(0,0),this));
                return;
            default :
                this.listeDeNoeud.add(new NoeudSimple(px,py,++this.maxIdNoeud,new Vecteur2D(0,0),this));
                
        }
        
    }
    
    public boolean hasBarre(Barre barre){
        for(Barre barreDejaLa : this.listeDeBarre){
            if (barre.equal(barreDejaLa)) return true;
        }
        return false;
    }
    
    public boolean hasNoeud(Noeud noeud){
        for(Noeud noeudDejaLa : this.listeDeNoeud){
            if (noeud.equal(noeudDejaLa)) return true;
        }
        return false;
    }
    
    public void addNoeud (double px, double py){
        this.addNoeud(px, py, "Simple");
    }
    
    public void addTerrain(Terrain terrain){
        this.listeDesTerrainRelie.add(terrain);
    }
    
    public boolean possedeNoeud(Noeud n){
        for(Noeud noeud : this.listeDeNoeud){
            if(noeud.equal(n)) return true;
        }
        return false;
    }
    
    public void removeNoeud (Noeud n){
        this.listeDeNoeud.remove(n);
    }
    
    public void removeBarre (Barre b){
        this.listeDeBarre.remove(b);
    }
    
    public Noeud getNoeud(int id){
        for(Noeud noeud : this.listeDeNoeud){
            if (noeud.getId()==id) return noeud;
        }
        
        return null;
    }
    
    public Noeud getDernierNoeud(){
        return this.getNoeud(maxIdNoeud);
    }
    
    public void setMaxIdNoeud(int id){
        this.maxIdNoeud = id;
    }
    
    public void setMaxIdBarre(int id){
        this.maxIdBarre = id;
    }
    
    public int getMaxIdNoeud(){
        int tempMaxId = -1;
        for (Noeud noeud : this.listeDeNoeud){
            tempMaxId = Math.max(tempMaxId ,noeud.getId());
        }
        for (Terrain terrain : this.listeDesTerrainRelie){
            for (Noeud noeud : terrain.getListeDeNoeud()){
                tempMaxId = Math.max(tempMaxId ,noeud.getId());
            }
        }
        return tempMaxId;
    }
    
    public String getId(){
        return this.id;
    }
    
    public void setId(String arg){
        this.id = arg;
    }
    
    public int nombreBarre(){
        int temp = 0;
        for(Barre barre : this.listeDeBarre){
            temp++;
        }
        return temp;
    }
    
    public int nombreNoeud(){
        int temp = 0;
        for(Noeud noeud : this.listeDeNoeud){
            temp++;
        }
        return temp;
    }
    
     public int getMaxIdBarre(){
        int tempMaxId = -1;
        for (Barre barre : this.listeDeBarre){
            tempMaxId = Math.max(tempMaxId ,barre.getId());
        }
        for (Terrain terrain : this.listeDesTerrainRelie){
            for (Barre barre : terrain.getListeDeBarre()){
                tempMaxId = Math.max(tempMaxId ,barre.getId());
            }
        }
        return tempMaxId;
    }
     
     @Override
     public String toString(){
        String resultat = ("Treillis"+ this.id );
        /*
        for(Noeud noeud : this.listeDeNoeud){
            resultat+=("\n"+noeud );
        }
        for(Barre barre : this.listeDeBarre){
            resultat+=("\n"+barre);
        }
        */
        resultat += ("\n"+ this.listeDeNoeud);
        resultat += ("\n"+ this.listeDeBarre);
        return resultat;
    }
     
    public String save(){
        String resultat = "";
        resultat += "Treillis "+this.id+ " ";
        resultat += "\n";
        for (Noeud noeud : this.listeDeNoeud){
            resultat += noeud.save();
            resultat += "\n";
        }
        for (Barre barre : this.listeDeBarre){
            resultat += barre.save();
            resultat += "\n";
        }
        for (Terrain terrain : this.listeDesTerrainRelie){
            resultat += terrain.save();
            resultat += "\n";
        }
       return resultat;
    }
     
}
