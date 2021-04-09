package jeuDeLaVie.commandes;

import jeuDeLaVie.cellules.Cellule;

public class CommandeMeurt extends Commande{

    public CommandeMeurt(Cellule c){
        this.cellule = c;
    }

    @Override
    public void executer() {
        this.cellule.meurt();
    }
}
