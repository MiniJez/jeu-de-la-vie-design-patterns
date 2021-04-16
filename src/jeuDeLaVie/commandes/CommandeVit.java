package jeuDeLaVie.commandes;

import jeuDeLaVie.cellules.Cellule;

/**
 * Classe qui represente la commande qui ordonne a une cellule de vivre.
 * Elle herite de la classe abstraite Commande (DESIGN PATTERN : COMMANDE)
 */
public class CommandeVit extends Commande{

    /**
     * Constructeur
     * @param c cellule a modifier
     */
    public CommandeVit(Cellule c){
        this.cellule = c;
    }

    /**
     * Methode qui demande a la cellule de vivre
     */
    @Override
    public void executer() {
        this.cellule.vit();
    }
}
