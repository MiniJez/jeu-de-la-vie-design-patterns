package jeuDeLaVie.commandes;

import jeuDeLaVie.cellules.Cellule;

public class CommandeVit extends Commande{

    public CommandeVit(Cellule c){
        this.cellule = c;
    }

    @Override
    public void executer() {
        this.cellule.vit();
    }
}
