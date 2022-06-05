package fr.insa.guerchongranier.infos2;

/**
 *
 * @author EnterN1me
 */
public class Calcul {
    
    public static double distancePythagore(double xA, double yA, double xB, double yB){
        double pythagore = Math.sqrt(Math.pow(xA-xB,2)+Math.pow(yA-yB,2));
        return pythagore;
    }
    
    public static double penteDroiteAffine(double xA, double yA, double xB, double yB){
        if ((xA - xB)!= 0){
        double pente = (yA - yB)/(xA - xB);
        return pente;
        }
        else return Double.MAX_VALUE;
    }
    
    public static double ordonneeALOrigine(double xA, double yA, double xB, double yB){
        //y = ax + b => b = y - ax;
        double ordonnee = yA - penteDroiteAffine(xA, yA, xB, yB)*xA;
        return ordonnee;
    }
    
    public static double ordonneeALOrigine(double xA, double yA, double pente){
        //y = ax + b => b = y - ax;
        double ordonnee = yA - pente * xA;
        return ordonnee;
    }
    
    public static double forceDuneBarre (Barre b){
        
        double d = b.getNoeudDepart().getForce().normeDeLaForce();
        double a = b.getNoeudArrive().getForce().normeDeLaForce();
        double angle1= b.angle(b.getNoeudDepart());
        double angle2=b.angle(b.getNoeudArrive());
        
        double forceProjete1= Math.cos(angle1)*d;
        double forceProjete2=Math.cos(angle2)*a;
        
        return forceProjete1+forceProjete2;
    }
    
}