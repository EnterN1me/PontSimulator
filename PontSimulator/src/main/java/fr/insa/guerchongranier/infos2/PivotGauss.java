/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.guerchongranier.infos2;

/**
 *
 * @author lucie
 */
public class PivotGauss {
    
    private int rang;
    private int signature;

    PivotGauss(int rang, int signature)
    {
        this.rang = rang;
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "PivotGauss{" +
                "rang=" + rang +
                ", signature=" + signature +
                '}';
    }
}