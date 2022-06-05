package fr.insa.guerchongranier.infos2;

/**
 *
 * @author EnterN1me
 */
public class AlreadyThereException extends Exception {
    
    public AlreadyThereException(Object o){
        super(o.toString() + " is already there !");
    }
}
