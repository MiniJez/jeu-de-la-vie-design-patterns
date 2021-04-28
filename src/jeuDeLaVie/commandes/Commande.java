/**
 * @author : CHAUMULON Cassandra
 */

package jeuDeLaVie.commandes;

import jeuDeLaVie.cellules.Cellule;

/**
 * Classe abstraite d'une commande qui représente une action à effectuer (= modification d'une cellule)
 * DESIGN PATTERN : COMMANDE
 */
public abstract class Commande {
    /** Cellule qui doit subit la modification */
    protected Cellule cellule;

    /**
     * Methode qui permet d'executer la commande
     */
    public abstract void executer();
}
