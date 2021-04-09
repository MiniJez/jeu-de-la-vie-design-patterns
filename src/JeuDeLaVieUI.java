import javax.swing.JFrame;
import java.awt.*;

public class JeuDeLaVieUI extends JFrame implements Observateur  {
    private JeuDeLaVie jeu;

    public JeuDeLaVieUI(JeuDeLaVie jeu) {
        super("Le super jeu de la vie");
        this.jeu = jeu;

        this.setSize(new Dimension(jeu.getXMax()*3, jeu.getYMax()*3));// regler la taille
        this.setLocationRelativeTo(null); // centrer la fenetre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //gestion de la fermeture de la fenetre
        this.setVisible(true); // afficher
    }

    public void actualise() {
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        for(int x = 0; x < jeu.getXMax(); x++){
            for(int y = 0; y < jeu.getYMax(); y++){
                if( jeu.getGrilleXY(x, y).estVivante() ){
                    g.fillOval(x*3, y*3, 3, 3);
                }
            }
        }
    }
}
