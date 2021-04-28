/**
 * @author : CHAUMULON Cassandra
 */

package jeuDeLaVie.cellules;

import jeuDeLaVie.visiteurs.Visiteur;

/**
 * Classe CelluleEtatVivant qui implemente l'interface CelluleEtat.
 * DESIGN PATTERN : SINGLETON
 */
public class CelluleEtatVivant implements CelluleEtat {
    /** Instance unique de CelluleEtatVivant */
    private static final CelluleEtatVivant instanceUnique = null;

    /**
     * Constructeur
     */
    private CelluleEtatVivant(){}

    /**
     * Getter de l'instance de CelluleEtatVivant
     * @return l'instance unique de CelluleEtatVivant
     */
    public static CelluleEtat getInstance(){
        if (instanceUnique == null) return new CelluleEtatVivant();
        return instanceUnique;
    }

    /**
     * Methode qui change l'etat d'une cellule a vivante
     * @return l'instance de CelluleEtatVivant
     */
    @Override
    public CelluleEtat vit() {
        return this;
    }

    /**
     * Methode qui change l'etat d'une cellule a morte
     * @return l'instance de CelluleEtatMort
     */
    @Override
    public CelluleEtat meurt() {
        return CelluleEtatMort.getInstance();
    }

    /**
     * Methode qui permet de savoir si l'etat actuel est 'Vivant' ou 'Mort'
     * @return true (car la cellule est toujours dans l'état 'Vivant')
     */
    @Override
    public boolean estVivante() {
        return true;
    }

    /**
     * Methode qui permet d'accepter un visiteur
     * @param visiteur visiteur a accepter
     * @param cellule cellule qui doit accepter le visiteur
     */
    @Override
    public void accepte(Visiteur visiteur, Cellule cellule) {
        visiteur.visiteCelluleVivante(cellule);
    }
}
