package jeuDeLaVie.jeu;

import jeuDeLaVie.observateurs.Observateur;

import javax.swing.JFrame;
import java.awt.*;

public class JeuDeLaVieUI extends JFrame implements Observateur {
    private JeuDeLaVie jeu;

    public JeuDeLaVieUI(JeuDeLaVie jeu) {
        super("Le super jeu de la vie");
        this.jeu = jeu;

        this.setSize(new Dimension(600, 700));// regler la taille
        this.setLocationRelativeTo(null); // centrer la fenetre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //gestion de la fermeture de la fenetre
        this.setVisible(true); // afficher
    }

    public void actualise() {
        repaint();


        try {
            Thread.sleep(300);
            System.out.println("pause");
        } catch (InterruptedException ie) {
           System.out.println("Erreur : "+ie);
        }
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
