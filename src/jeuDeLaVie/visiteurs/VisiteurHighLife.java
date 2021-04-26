package jeuDeLaVie.visiteurs;

import jeuDeLaVie.JeuDeLaVie;

import jeuDeLaVie.cellules.Cellule;

import jeuDeLaVie.commandes.CommandeMeurt;
import jeuDeLaVie.commandes.CommandeVit;

public class VisiteurHighLife extends Visiteur{

    /**
     * Constructeur du VisiteurHighLife
     * @param jeu jeu de la vie
     */
    public VisiteurHighLife(JeuDeLaVie jeu) {
        super(jeu);
    }

    /**
     * Methode qui permet visiter les cellules vivantes
     * @param cellule cellule a visiter
     */
    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(this.jeu);

        if(nbVoisins < 2 || nbVoisins > 3){
            this.jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    /**
     * Methode qui permet visiter les cellules mortes
     * @param cellule cellule a visiter
     */
    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(this.jeu);

        if(nbVoisins == 3 || nbVoisins == 6){
            this.jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
