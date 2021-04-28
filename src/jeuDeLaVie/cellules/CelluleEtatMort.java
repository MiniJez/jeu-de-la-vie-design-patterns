/**
 * @author : CHAUMULON Cassandra
 */

package jeuDeLaVie.cellules;

import jeuDeLaVie.visiteurs.Visiteur;

/**
 * Classe CelluleEtatMort qui implemente l'interface CelluleEtat.
 * DESIGN PATTERN : SINGLETON
 */
public class CelluleEtatMort implements CelluleEtat {
    /** Instance unique de CelluleEtatMort */
    private static final CelluleEtatMort instanceUnique = null;

    /**
     * Constructeur
     */
    private CelluleEtatMort(){}

    /**
     * Getter de l'instance de CelluleEtatMort
     * @return l'instance unique de CelluleEtatMort
     */
    public static CelluleEtat getInstance() {
        if(instanceUnique == null) return new CelluleEtatMort();
        return instanceUnique;
    }

    /**
     * Methode qui change l'etat d'une cellule a vivante
     * @return l'instance de CelluleEtatVivant
     */
    @Override
    public CelluleEtat vit() {
        return CelluleEtatVivant.getInstance();
    }

    /**
     * Methode qui change l'etat d'une cellule a morte
     * @return l'instance de CelluleEtatMort
     */
    @Override
    public CelluleEtat meurt() {
        return this;
    }

    /**
     * Methode qui permet de savoir si l'etat actuel est 'Vivant' ou 'Mort'
     * @return false (car la cellule est toujours dans l'etat 'Mort')
     */
    @Override
    public boolean estVivante() {
        return false;
    }

    /**
     * Methode qui permet d'accepter un visiteur
     * @param visiteur visiteur a accepter
     * @param cellule cellule qui doit accepter le visiteur
     */
    @Override
    public void accepte(Visiteur visiteur, Cellule cellule) {
        visiteur.visiteCelluleMorte(cellule);
    }
}
