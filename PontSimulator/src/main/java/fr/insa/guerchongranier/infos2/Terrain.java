package fr.insa.guerchongranier.infos2;

/**
 *
 * @author EnterN1me
 */
@SuppressWarnings("FieldMayBeFinal") // c'est volontaire !
public class Terrain extends Treillis{
    
    private Treillis treilliRelie;
    
    @Override
    public void addBarre (Noeud noeudDepart, Noeud noeudArrive){
        super.setMaxIdBarre(treilliRelie.getMaxIdBarre());
        super.addBarre(noeudDepart,noeudArrive);
        treilliRelie.setMaxIdBarre(super.getMaxIdBarre());
    }
    
    @Override
    public void addNoeud (double px, double py){
        super.setMaxIdNoeud(treilliRelie.getMaxIdNoeud());
        super.addNoeud(px,py,"Double");
        treilliRelie.setMaxIdNoeud(super.getMaxIdNoeud());
    }
    
    public Terrain(Treillis pont){
        super();
        this.treilliRelie = pont;
        treilliRelie.addTerrain(this);
    }
    
    @Override
     public String toString(){
        String resultat = ("Terrain relié au treilli : \n"+ this.treilliRelie);
        return resultat;
    }
     
     @Override
     public String save(){
         String result = "";
         for (Noeud noeud : this.getListeDeNoeud()){
            result += noeud.saveTerrain();
            result += "\n";
        }
        for (Barre barre : this.getListeDeBarre()){
            result += barre.saveTerrain();
            result += "\n";
        }
        
        return result;
     }
    
}
