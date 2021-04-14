package jeuDeLaVie.observateurs;

import jeuDeLaVie.JeuDeLaVie;

import javax.swing.*;
import java.awt.*;

public class JeuDeLaVieUI extends JPanel implements Observateur {
    private JeuDeLaVie jeu;

    public JeuDeLaVieUI(JeuDeLaVie jeu) {
        this.jeu = jeu;
    }

    public void actualise() {
        repaint();
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
}
