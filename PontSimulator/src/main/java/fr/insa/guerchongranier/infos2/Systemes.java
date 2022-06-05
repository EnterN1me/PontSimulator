/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.guerchongranier.infos2;

/**
 *
 * @author lucie
 */

import java.util.ArrayList;

public class Systemes {
    
    private ArrayList<Noeud> noeuds;
    private ArrayList<Barre> barres;
    private Treillis treillis;

    public Systemes (Treillis treillis) throws Exception {
        this.noeuds = treillis.getListeDeNoeud();
        this.barres = treillis.getListeDeBarre();
        this.treillis = treillis;
        
        int nbnoeudssimple = 0;
        int nbasimple = 0;
        int nbadouble = 0;
        int inconnuesScalaire = 0;

        for (Noeud noeud : noeuds) {
            switch (noeud.getType()) {
                case 0:
                    nbnoeudssimple = nbnoeudssimple + 1;
                    break;
                case 1:
                    nbasimple = nbasimple + 1;
                    inconnuesScalaire++;
                    break;
                case 2:
                    nbadouble = nbadouble + 1;
                    inconnuesScalaire+=2;
                    break;
                default:
                    break;
            }
        }
        
        /*
        if (2 * nbnoeudssimple != treillis.nombreBarre() + nbasimple + 2 * nbadouble) {
            System.out.println(new Exception("Cest pas isostatique"));
            return;
        }
        */
        
        Matrice systeme = new Matrice(2*treillis.nombreNoeud(), treillis.nombreBarre()+inconnuesScalaire);
        
        int ligne = 0;
        int colonne = 0;
        int dernierScalaireEntree =barres.size()-1;
        
        for(Noeud noeud: noeuds){
            //1er ligne en x
            for(Barre barre : barres){
                if (barre.hasNoeud(noeud)) systeme.set(ligne, colonne, Math.cos(barre.angle(noeud)));
                colonne++;
            }
            switch (noeud.getType()) {
                case 1:
                    dernierScalaireEntree++;
                    if (noeud.getAxeVerrouille()=='x') systeme.set(ligne, dernierScalaireEntree, 1);
                    break;
                    
                case 2:
                    systeme.set(ligne, dernierScalaireEntree++, 1);
                    systeme.set(ligne, dernierScalaireEntree++, 1);
                    break;
                    
                default:
                    break;
                    }
            
            
            ligne++;
            colonne = 0;
            dernierScalaireEntree =barres.size()-1;
            //2eme ligne en y
            for(Barre barre : barres){
                if (barre.hasNoeud(noeud)) systeme.set(ligne, colonne, Math.sin(barre.angle(noeud)));
                colonne++;
            }
            switch (noeud.getType()) {
                case 1:
                    dernierScalaireEntree++;
                    if (noeud.getAxeVerrouille()=='y') systeme.set(ligne, dernierScalaireEntree, 1);
                    break;
                    
                case 2:
                    systeme.set(ligne, dernierScalaireEntree++, 1);
                    systeme.set(ligne, dernierScalaireEntree++, 1);
                    break;
                    
                default:
                    break;
                    }
            
            
            ligne++;
            colonne = 0;
            dernierScalaireEntree =barres.size()-1;
        }

        //System.out.println(systeme);
        
        Matrice vecteur = new Matrice(2*treillis.nombreNoeud(), 1);
        
        ligne = 0;
        for(Noeud noeud: noeuds){
            vecteur.set(ligne,0,noeud.getForce().getVx());
            ligne++;
            vecteur.set(ligne,0,noeud.getForce().getVy());
            ligne++;
        }
        
        systeme.descenteGauss();
        systeme.pivotsaun();
        systeme.remonteeGauss();
        
        Matrice valeur = systeme.multiplication(vecteur);
        
        System.out.println("solution : " + valeur);
        
        int iterator = 0;
        for(Barre barre : treillis.getListeDeBarre()){
            barre.setCompression(valeur.get(iterator, 0));
            iterator++;
        }
        
        
                

    }

}
