package jeuDeLaVie.observateurs;

import jeuDeLaVie.JeuDeLaVie;

import javax.swing.*;
import java.awt.*;

public class JeuDeLaVieUI extends JPanel implements Observateur, Runnable {
    private JeuDeLaVie jeu;
    private boolean enCours;
    private Thread thread;

    public JeuDeLaVieUI(JeuDeLaVie jeu) {
        this.jeu = jeu;
    }

    public void actualise() {
        repaint();
    }

    public void arreter() {
        this.enCours = false;
    }
    public void lancer() {
        this.enCours = true;
        thread = new Thread(this);
        thread.start();
    }

    public void paint(Graphics g) {
        super.paint(g);

        for(int x = 0; x < jeu.getXMax(); x++){
            for(int y = 0; y < jeu.getYMax(); y++){
                if( jeu.getGrilleXY(x, y) != null && jeu.getGrilleXY(x, y).estVivante() ){
                    g.fillRect(x*4, y*4, 3, 3);
                }
            }
        }
    }

    @Override
    public void run() {
        while(enCours){
            jeu.calculerGenerationSuivante();

            try {
                int tmp = jeu.getVitesse();

                if(tmp == 5) tmp *= 100;
                else if(tmp > 5) tmp = 1000 - (tmp*100);
                else tmp = 500 + (tmp*100);

                Thread.sleep(tmp);
            }catch(InterruptedException ex){
                System.out.println("Erreur : "+ex);
            }
        }
    }
}
