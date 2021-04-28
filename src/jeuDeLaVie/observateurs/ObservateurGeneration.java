/**
 * @author : CHAUMULON Cassandra
 */

package jeuDeLaVie.observateurs;

import jeuDeLaVie.JeuDeLaVie;

/**
 * Classe qui permet de d'observer le numero de la generation actuelle + le nombre de cellules vivantes
 */
public class ObservateurGeneration implements Observateur {
    /** Jeu de la vie */
    private JeuDeLaVie jeu;

    /**
     * Constructeur
     * @param jeu jeu de la vie
     */
    public ObservateurGeneration(JeuDeLaVie jeu){
        this.jeu = jeu;
    }

    /**
     * Methode qui permet d'actualiser l'affichage en fonction du changement du numero de la generation
     * et le nombre de cellules vivantes
     */
    @Override
    public void actualise() {
        System.out.println("Generation "+jeu.getNumGeneration()+" : "+jeu.calculerNbCellulesVivantes()+" cellules vivantes");
    }
}
