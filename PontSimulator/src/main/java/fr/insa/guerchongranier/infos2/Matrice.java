/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.guerchongranier.infos2;

/**
 *
 * @author lucie
 */
public class Matrice {
    
    private int nbrLig, nbrCol;
    private double[][] coeffs;

    public Matrice(int nl, int nc, double[][] coeffs){
        this.nbrLig = nl;
        this.nbrCol = nc;
        this.coeffs = coeffs;
    }

    public Matrice(int nl, int nc)
    {
        this(nl,nc,new double[nl][nc]);
    }

    public Matrice(int size){
        this(size, size);
    }

    @Override
    public String toString() {

        String result = "";

        for (int i = 0; i < nbrLig; i++) {
            result += "[ ";
            for (int j = 0; j < nbrCol; j++) {
                result += String.format("%+4.2E",coeffs[i][j])+ " ";
            }
            result += "]\n";
        }

        return result;
    }

    public Matrice identite(int size){
        Matrice id = new Matrice(size);
        for (int i = 0; i < size; i++) {
            id.coeffs[i][i] = 1;
        }
        return id;
    }

    public static Matrice creeVecteur(double[] values) {
        Matrice vect = new Matrice(values.length, 1);
        for (int i = 0; i < values.length; i++) {
            vect.coeffs[i][0] = values[i];
        }
        return vect;
    }

    public Double get(int i,int j) {
        return coeffs[i][j];
    }

    public int getNbrCol() {
        return nbrCol;
    }

    public int getNbrLig() {
        return nbrLig;
    }

    public void set(int i, int j, double value) {
        coeffs[i][j] = value;
    }



    public Matrice concatLig(Matrice mat2) {
        if (this.nbrCol != mat2.nbrCol) {
            throw new Error("nombre de colonnes non égal : "+ this.nbrCol + " et " + mat2.nbrCol);
        }
        Matrice mat = new Matrice(this.nbrLig + mat2.nbrLig, this.nbrCol);
        for (int i = 0; i < mat.nbrLig; i++) {
            for (int j = 0; j < mat.nbrCol; j++) {
                mat.coeffs[i][j] = (i < this.nbrLig) ? this.coeffs[i][j] : mat2.coeffs[i - this.nbrLig][j];
            }
        }
        return mat;

    }

    public static Matrice concatColonne(Matrice matrice, Matrice matrice2) {
        if (matrice.nbrLig != matrice2.nbrLig) {
            throw new Error("nombre de colonnes non égal : " + matrice.nbrLig + " et " + matrice2.nbrLig);
        } else {

            Matrice mat = new Matrice(matrice.nbrLig, matrice.nbrCol + matrice2.nbrCol);
            for (int i = 0; i < mat.nbrLig; i++) {
                for (int j = 0; j < mat.nbrCol; j++) {
                    mat.coeffs[i][j] = (j < matrice.nbrCol) ? matrice.coeffs[i][j] : matrice2.coeffs[i][j - matrice.nbrCol];
                }
            }
            return mat;
        }
    }

    public Matrice transposee() {
        Matrice mat = new Matrice(nbrCol, nbrLig);
        for (int i = 0; i < mat.nbrCol; i++) {
            for (int j = 0; j < nbrLig; j++) {
                mat.coeffs[i][j] = coeffs[j][i];
            }
        }
        return mat;
    }

    public Matrice metAuCarre()  {
        return concatColonne(this.concatLig(identite(nbrCol)), identite(nbrLig).concatLig(this.transposee()));
    }

    public Matrice addition(Matrice mat2){
        if(this.nbrLig != mat2.nbrLig || this.nbrCol != mat2.nbrCol){
            throw new Error("matrices de tailles différentes");
        }
        else {
            double[][] coeffs = new double[this.nbrLig][this.nbrCol];
            for (int i = 0; i < coeffs.length; i++) {
                for (int j = 0; j < coeffs[i].length; j++) {
                    coeffs[i][j] = this.coeffs[i][j] + mat2.coeffs[i][j];
                }
            }
            return new Matrice(this.nbrLig, this.nbrCol, coeffs);
        }

    }

    public Matrice multiplication(Matrice mat2) throws Exception{
        if (this.nbrCol != mat2.nbrLig) throw new Exception("erreur de dimension");
        Matrice resultat = new Matrice(this.nbrLig, mat2.nbrCol);
        for (int ligne = 0; ligne < resultat.nbrLig; ligne++) {
            for (int colonne = 0; colonne < resultat.nbrCol; colonne++) {
                double coeff = 0;
                for (int k = 0; k < this.nbrCol; k++) {
                    coeff += this.coeffs[ligne][k] * mat2.coeffs[k][colonne];
                }
                resultat.coeffs[ligne][colonne] = coeff;
            }
        }
        return resultat;
    }

    public void permuteLigne(int ligne1, int ligne2) {
        double[][] newCoeffs = new double[nbrLig][nbrCol];
        for (int i = 0; i < coeffs.length; i++) {
            for (int j = 0; j < coeffs[i].length; j++) {
                newCoeffs[i][j] = coeffs[i][j];
            }
        }

        for (int i = 0; i < nbrCol; i++) {

            newCoeffs[ligne2][i] = coeffs[ligne1][i];
            newCoeffs[ligne1][i] = coeffs[ligne2][i];
        }

        coeffs = newCoeffs;
    }
    
    
    public void transvection(int l1, int l2) {
        if (l1 > nbrCol) throw new Error("l1 > nbrCol");
        if (coeffs[l1][l1] == 0.0) throw new Error("Ml1l1 = 0");

        double p = coeffs[l2][l1] / coeffs[l1][l1];

        for (int i = 0; i < nbrCol; i++) {
            if (i == l1) coeffs[l2][l1] = 0.0;
            else coeffs[l2][i] = coeffs[l2][i] - p * coeffs[l1][i];
        }
    }
    

    public int lignePlusGrandPivot(int nombre) throws Exception {
        if(nombre >= nbrCol || nombre >= nbrLig ) throw new Exception("nombre trop grand");
        double max = 0.0;
        int maxI = 0;
        for (int i = nombre; i < nbrLig; i++) {
            if( max < Math.abs(coeffs[i][nombre])) {
                max = Math.abs(coeffs[i][nombre]);
                maxI = i;
            }
        }
        if(max > Math.pow(10, -8.0)) return maxI;
        else return -1;
    }
    
    public PivotGauss descenteGauss() throws Exception {
        int signature = 1;
        int rang = Math.min(this.nbrCol,this.nbrLig);

        for (int i = 0; i < this.nbrLig; i++) {
            for (int j = 0; j < this.nbrLig; j++) {
                int lppp = lignePlusGrandPivot(j);
                if (lppp!= -1){
                    permuteLigne(j, lppp);
                }
                else signature= -signature;
            }
        }

        for (int i = 0; i < this.nbrLig; i++) {
            for (int j =0; j< i; j++) {
                if (coeffs[i][j] != 0) transvection(j, i);
            }
        }
        return new PivotGauss(rang,signature);
    }

    public double determinant (int signature){
        double determinant = signature;
        for(int i=0; i<this.nbrLig; i++){
            determinant = determinant * this.coeffs[i][i];
        }
        return(determinant);
    }

    public Matrice pivotsaun (){
        for(int i=0; i<this.nbrCol; i++){
            for(int j=i; j<this.nbrLig;j++){
                double pivot = this.coeffs[i][i];
                this.coeffs[i][j] = this.coeffs[i][j] / pivot;
            }
        }
        return this;
    }

    public Matrice remonteeGauss (){
        for (int j = 0; j< this.nbrLig; j++) {
            for (int i = 0; i < j; i++) {
                if (coeffs[i][j] != 0) transvection(j, i);
            }
        }
        return this;
    }

}
