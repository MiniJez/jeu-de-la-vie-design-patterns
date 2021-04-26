package jeuDeLaVie.visiteurs;

import jeuDeLaVie.cellules.Cellule;

import jeuDeLaVie.JeuDeLaVie;

/**
 * Classe abstraite d'un visiteur
 * DESIGN PATTERN : VISITEUR
 */
public abstract class Visiteur {
    /** Jeu de la vie */
    protected JeuDeLaVie jeu;

    /**
     * Constructeur
     * @param jeu jeu de la vie
     */
    public Visiteur(JeuDeLaVie jeu){
        this.jeu = jeu;
    }

    /**
     * Methode abstraite pour visiter les cellules vivantes
     * @param cellule cellule a visiter
     */
    public abstract void visiteCelluleVivante(Cellule cellule);

    /**
     * Methode abstraite pour visiter les cellules mortes
     * @param cellule cellule a visiter
     */
    public abstract void visiteCelluleMorte(Cellule cellule);
}
