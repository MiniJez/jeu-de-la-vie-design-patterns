package jeuDeLaVie.cellules;

import jeuDeLaVie.JeuDeLaVie;
import jeuDeLaVie.visiteurs.Visiteur;

public class Cellule {
    private CelluleEtat etat;
    private int x;
    private int y;

    public Cellule(int x, int y, CelluleEtat etat){
        this.x = x;
        this.y = y;
        this.etat = etat;
    }

    public int nombreVoisinesVivantes(JeuDeLaVie g) {
        int nbVoisins = 0;

        int limiteEnBas = Math.min(x + 1, g.getXMax() - 1);
        int limiteADroite = Math.min(y + 1, g.getYMax() - 1);
        for (int i = Math.max(0, x - 1); i <= limiteEnBas; i++) {
            for (int j = Math.max(0, y - 1); j <= limiteADroite; j++) {
                Cellule voisin = g.getGrilleXY(i, j);
                if(voisin != null) {
                    if (!(i == x && j == y ) && (voisin.estVivante())) {
                        nbVoisins++;
                    }
                }

            }
        }
        return nbVoisins;
    }

    public void vit(){
        this.etat = etat.vit();
    }

    public void meurt(){
        this.etat = etat.meurt();
    }

    public boolean estVivante(){
        return this.etat.estVivante();
    }

    public void accepte(Visiteur visiteur){
        this.etat.accepte(visiteur, this);
    }
}