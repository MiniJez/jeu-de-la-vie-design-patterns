package jeuDeLaVie.cellules;

import jeuDeLaVie.JeuDeLaVie;

import jeuDeLaVie.visiteurs.Visiteur;

/**
 * Classe qui represente une cellule de la grille. Une cellule est caractérisee par un etat et des coordonnees x et y
 */
public class Cellule {
    /** Etat de la cellule */
    private CelluleEtat etat;
    /** Coordonnee en  x */
    private final int x;
    /** Coordonnee en  y */
    private final int y;

    /**
     * Constructeur d'une Cellule
     * @param x coordonnee x
     * @param y coordonnee y
     * @param etat etat de la cellule (morte ou vivante)
     */
    public Cellule(int x, int y, CelluleEtat etat){
        this.x = x;
        this.y = y;
        this.etat = etat;
    }

    /**
     * Methode qui calcule et retourne le nombre de voisines vivantes de la cellule
     * @param jeu jeu (pour avoir acces a la taille de la grille)
     * @return le nombre de voisines vivantes
     */
    public int nombreVoisinesVivantes(JeuDeLaVie jeu) {
        int nbVoisins = 0;
        int limiteEnBas = Math.min(x + 1, jeu.getXMax() - 1);
        int limiteADroite = Math.min(y + 1, jeu.getYMax() - 1);

        // Parcours des voisines
        for (int i = Math.max(0, x - 1); i <= limiteEnBas; i++) {
            for (int j = Math.max(0, y - 1); j <= limiteADroite; j++) {
                Cellule voisin = jeu.getGrilleXY(i, j); // Voisine actuelle

                // Vérification qu'elle n'est pas en dehors de la grille
                if(voisin != null) {
                    if (!(i == x && j == y ) && (voisin.estVivante())) { nbVoisins++; }
                }
            }
        }
        return nbVoisins;
    }

    /**
     * Methode qui change l'etat de la cellule (la ressuscite)
     */
    public void vit(){
        this.etat = etat.vit();
    }

    /**
     * Methode qui change l'etat de la cellule (la fait mourir)
     */
    public void meurt(){
        this.etat = etat.meurt();
    }

    /**
     * Methode qui permet de savoir si la cellule est vivante
     * @return un booleen
     */
    public boolean estVivante(){
        return this.etat.estVivante();
    }

    /**
     * Methode qui permet d'accepter un visiteur
     * @param visiteur le visiteur a accepter
     */
    public void accepte(Visiteur visiteur){
        this.etat.accepte(visiteur, this);
    }
}