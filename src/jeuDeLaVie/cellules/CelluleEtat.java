package jeuDeLaVie.cellules;

import jeuDeLaVie.visiteurs.Visiteur;

public interface CelluleEtat {
     CelluleEtat vit();
     CelluleEtat meurt();
     boolean estVivante();
     void accepte(Visiteur visiteur, Cellule cellule);
}
