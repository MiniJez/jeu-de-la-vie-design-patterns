package jeuDeLaVie.observateurs;

import jeuDeLaVie.JeuDeLaVie;
import jeuDeLaVie.visiteurs.Visiteur;
import jeuDeLaVie.visiteurs.VisiteurClassique;
import jeuDeLaVie.visiteurs.VisiteurDayAndNight;
import jeuDeLaVie.visiteurs.VisiteurHighLife;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class JeuDeLaVieUI extends JPanel implements Observateur, Runnable {
    private JeuDeLaVie jeu;
    private boolean enCours;
    private Thread thread;
    private int vitesse;
    private JTextArea infos;

    public JeuDeLaVieUI(JeuDeLaVie jeu) {
        this.jeu = jeu;
        this.vitesse = 5;
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

    public void creerInterface(){
        JFrame fenetre = new JFrame("Le super jeu de la vie");

        fenetre.setSize(new Dimension(670, 720));// regler la taille
        fenetre.setLocationRelativeTo(null); // centrer la fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //gestion de la fermeture de la fenetre
        fenetre.setVisible(true); // afficher

        JPanel boxPrincipale = new JPanel();
        boxPrincipale.setBorder(new EmptyBorder(10,10,10,10));
        boxPrincipale.setLayout(new BoxLayout(boxPrincipale, BoxLayout.X_AXIS));

        this.setPreferredSize(new Dimension(600,600));
        boxPrincipale.add(this);

        JPanel panneau = new JPanel();
        panneau.setLayout(new BorderLayout(5,25));
        panneau.setPreferredSize(new Dimension(400,600));

        JPanel haut = new JPanel();
        haut.setLayout(new BorderLayout(5,25));


        JPanel jp = new JPanel();
        infos = new JTextArea(2,20);
        infos.setEditable(false);
        jp.setPreferredSize(new Dimension(300,50));
        infos.setText("Génération numéro "+jeu.getNumGeneration()+"\nCellules vivantes : "+jeu.calculerNbCellulesVivantes());
        jp.add(infos);

        haut.add(jp);

        JPanel boutons = new JPanel();
        boutons.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

        JButton btnLancer = new JButton("Lancer", new ImageIcon("./assets/play.png"));
        JButton btnPause = new JButton("Pause", new ImageIcon("./assets/pause.png"));
        JButton btnSuivant = new JButton("Suivant", new ImageIcon("./assets/suivant.png"));

        btnLancer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { lancer();  }
        });

        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { arreter(); }
        });

        btnSuivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { jeu.calculerGenerationSuivante(); }
        });

        boutons.add(btnLancer);
        boutons.add(btnPause );
        boutons.add(btnSuivant);

        haut.add(boutons, BorderLayout.SOUTH);
        panneau.add(haut, BorderLayout.NORTH);

        JPanel options = new JPanel();

        JSlider s = new JSlider(JSlider.HORIZONTAL,2,8,5);

        Hashtable labels = new Hashtable();
        labels.put( 2, new JLabel("Lent") );
        labels.put( 8, new JLabel("Rapide") );

        s.setLabelTable(labels);
        s.setMinorTickSpacing(1);
        s.setMajorTickSpacing(8);
        s.setPaintTicks(true);
        s.setPaintLabels(true);

        s.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) { vitesse = s.getValue(); }
        });

        options.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 35));
        options.add(new JLabel("Vitesse"));
        options.add(s);


        JPanel choixRegles = new JPanel();
        choixRegles.setLayout(new FlowLayout(FlowLayout.TRAILING, 15, 5));
        choixRegles.add(new JLabel("Choix des règles"));

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Classique");
        comboBox.addItem("HighLife");
        comboBox.addItem("Day & Night");
        comboBox.setSelectedIndex(0);


        choixRegles.add(comboBox);

        Visiteur vClassic = new VisiteurClassique(jeu);
        Visiteur vHighLife = new VisiteurHighLife(jeu);
        Visiteur vDayNight = new VisiteurDayAndNight(jeu);

        jeu.setVisiteur(vClassic);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choix = comboBox.getSelectedItem().toString();
                if(choix == "Classique"){ jeu.setVisiteur(vClassic); }
                else if(choix == "HighLife"){ jeu.setVisiteur(vHighLife); }
                else { jeu.setVisiteur(vDayNight); }
            }
        });

        options.add(choixRegles);

        JButton reset = new JButton("Réinitialiser", new ImageIcon("./assets/reset.png"));
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reinitialiser");
                jeu.initialiseGrille();
            }
        });

        options.add(reset);

        panneau.add(options, BorderLayout.CENTER);

        boxPrincipale.add(panneau);

        fenetre.add(boxPrincipale);
        fenetre.pack();
    }

    @Override
    public void run() {
        while(enCours){
            updateAffichage();
            jeu.calculerGenerationSuivante();

            try {
                int tmp = vitesse;

                if(tmp == 5) tmp *= 100;
                else if(tmp > 5) tmp = 1000 - (tmp*100);
                else tmp = 500 + (tmp*100);

                Thread.sleep(tmp);
            }catch(InterruptedException ex){
                System.out.println("Erreur : "+ex);
            }
        }
    }

    public void updateAffichage(){
        this.infos.setText("Génération numéro "+jeu.getNumGeneration()+"\nCellules vivantes : "+jeu.calculerNbCellulesVivantes());
    }
}
