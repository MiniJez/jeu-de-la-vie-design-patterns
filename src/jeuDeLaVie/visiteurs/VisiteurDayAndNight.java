package jeuDeLaVie.visiteurs;

import jeuDeLaVie.JeuDeLaVie;

import jeuDeLaVie.cellules.Cellule;

import jeuDeLaVie.commandes.CommandeMeurt;
import jeuDeLaVie.commandes.CommandeVit;

/**
 * Classe qui implemente les regles 'Day & Night' du jeu de la vie.
 * Elle herite de la classe Visiteur.
 */
public class VisiteurDayAndNight extends Visiteur{

    /**
     * Constructeur du VisiteurDayAndNight
     * @param jeu jeu de la vie
     */
    public VisiteurDayAndNight(JeuDeLaVie jeu) {
        super(jeu);
    }

    /**
     * Methode qui permet visiter les cellules vivantes
     * @param cellule cellule a visiter
     */
    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(this.jeu);

        if(nbVoisins == 3 || nbVoisins == 4 || nbVoisins == 6  || nbVoisins == 7  || nbVoisins == 8){
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

        if(nbVoisins == 3 || nbVoisins == 6 || nbVoisins == 7 || nbVoisins == 8){
            this.jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
