package jeuDeLaVie.commandes;

import jeuDeLaVie.cellules.Cellule;

/**
 * Classe
 */
public class CommandeMeurt extends Commande{

    /**
     * Constructeur
     * @param c cellule a modifier
     */
    public CommandeMeurt(Cellule c){
        this.cellule = c;
    }

    /**
     * Methode qui demande a la cellule de mourir
     */
    @Override
    public void executer() {
        this.cellule.meurt();
    }
}
