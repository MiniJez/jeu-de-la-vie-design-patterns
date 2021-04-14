package jeuDeLaVie.visiteurs;

import jeuDeLaVie.JeuDeLaVie;
import jeuDeLaVie.cellules.Cellule;
import jeuDeLaVie.commandes.CommandeMeurt;
import jeuDeLaVie.commandes.CommandeVit;

public class VisiteurDayAndNight extends Visiteur{
    public VisiteurDayAndNight(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(this.jeu);

        if(nbVoisins == 3 || nbVoisins == 4 || nbVoisins == 6  || nbVoisins == 7  || nbVoisins == 8){
            this.jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(this.jeu);

        if(nbVoisins == 3 || nbVoisins == 6 || nbVoisins == 7 || nbVoisins == 8){
            this.jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
