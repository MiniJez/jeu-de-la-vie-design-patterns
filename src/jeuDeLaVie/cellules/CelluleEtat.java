package jeuDeLaVie.cellules;

import jeuDeLaVie.visiteurs.Visiteur;

/**
 * Interface d'une CelluleEtat
 * DESIGN PATTERN : ETAT
 */
public interface CelluleEtat {
     /**
      * Methode qui change l'etat d'une cellule a vivante
      * @return une CelluleEtatVivante
      */
     CelluleEtat vit();

     /**
      * Methode qui change l'etat d'une cellule a morte
      * @return une CelluleEtatMort
      */
     CelluleEtat meurt();

     /**
      * Methode qui permet de savoir si l'etat actuel est 'Vivant' ou 'Mort'
      * @return un booleen
      */
     boolean estVivante();

     /**
      * Methode qui permet d'accepter un visiteur
      * @param visiteur visiteur a accepter
      * @param cellule cellule qui doit accepter le visiteur
      */
     void accepte(Visiteur visiteur, Cellule cellule);
}
