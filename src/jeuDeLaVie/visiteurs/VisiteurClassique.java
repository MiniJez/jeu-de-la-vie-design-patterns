package jeuDeLaVie.visiteurs;

import jeuDeLaVie.cellules.Cellule;
import jeuDeLaVie.commandes.CommandeMeurt;
import jeuDeLaVie.commandes.CommandeVit;
import jeuDeLaVie.JeuDeLaVie;

public class VisiteurClassique extends Visiteur {

    public VisiteurClassique(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(this.jeu);

        if(nbVoisins < 2 || nbVoisins > 3){
            this.jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        if(cellule.nombreVoisinesVivantes(this.jeu) == 3){
            this.jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
