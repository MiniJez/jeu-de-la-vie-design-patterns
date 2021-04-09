package jeuDeLaVie.commandes;

import jeuDeLaVie.cellules.Cellule;

public abstract class Commande {
    protected Cellule cellule;

    public abstract void executer();
}
