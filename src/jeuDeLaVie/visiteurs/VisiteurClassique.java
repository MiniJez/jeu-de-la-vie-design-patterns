/**
 * @author : CHAUMULON Cassandra
 */

package jeuDeLaVie.visiteurs;

import jeuDeLaVie.cellules.Cellule;

import jeuDeLaVie.commandes.CommandeMeurt;
import jeuDeLaVie.commandes.CommandeVit;

import jeuDeLaVie.JeuDeLaVie;

/**
 * Classe qui implemente les regles classiques du jeu de la vie.
 * Elle herite de la classe Visiteur.
 */
public class VisiteurClassique extends Visiteur {

    /**
     * Constructeur du VisiteurClassique
     * @param jeu jeu de la vie
     */
    public VisiteurClassique(JeuDeLaVie jeu) {
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
        if(cellule.nombreVoisinesVivantes(this.jeu) == 3){
            this.jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}