package jeuDeLaVie.observateurs;

import jeuDeLaVie.JeuDeLaVie;

public class ObservateurGeneration implements Observateur {
    private JeuDeLaVie jeu;

    public ObservateurGeneration(JeuDeLaVie jeu){
        this.jeu = jeu;
    }

    @Override
    public void actualise() {
        System.out.println("Generation "+jeu.getNumGeneration()+" : "+jeu.calculerNbCellulesVivantes()+" cellules vivantes");
    }
}
