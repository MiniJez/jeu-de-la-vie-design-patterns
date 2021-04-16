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

/**
 * Classe qui gere l'interface du jeu de la vie.
 * DESIGN PATTERN : OBSERVATEUR
 */
public class JeuDeLaVieUI extends JPanel implements Observateur, Runnable {
    /** Jeu de la vie */
    private JeuDeLaVie jeu;
    /** Booleen pour savoir si le jeu est lance ou en pause */
    private boolean enCours;
    /** Thread pour l'affichage */
    private Thread thread;
    /** Vitesse de le rafraichissement de l'affichage */
    private int vitesse;
    /** Zone de texte ou est affiche le numero de la generation en cours ainsi que le nombre cellules vivantes */
    private JTextArea infos;

    /**
     * Constructeur de JeuDeLaVieUI
     * @param jeu jeu de la vie
     */
    public JeuDeLaVieUI(JeuDeLaVie jeu) {
        this.jeu = jeu;
        this.vitesse = 5; // par défaut => vitesse = 5
    }

    /**
     * Methode qui permet de rafraichir l'affichage avec la nouvelle generation de cellules
     */
    public void actualise() { repaint(); }

    /**
     * Methode qui permet de mettre en pause l'automate
     */
    public void arreter() {  this.enCours = false; }

    /**
     * Methode qui permet de lancer l'automate
     */
    public void lancer() {
        this.enCours = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Methode qui permet de permet de dessiner les cellules vivantes
     * @param g Graphics
     */
    public void paint(Graphics g) {
        super.paint(g);
        int nb = 0;

        for(int x = 0; x < jeu.getXMax(); x++){
            for(int y = 0; y < jeu.getYMax(); y++){
                if( jeu.getGrilleXY(x, y) != null && jeu.getGrilleXY(x, y).estVivante() ){
                    // Coloration d'une cellule sur deux en violet
                    if(nb%2==0)  { g.setColor(new Color(104, 9, 155 )); }
                    // Coloration d'une cellule sur deux en vert
                    else { g.setColor(new Color(48, 149, 5 )); }
                    g.fillRect(x*4, y*4, 3, 3);
                    nb++;
                }
            }
        }
    }

    /**
     * Methode qui permet de creer l'interface du jeu de la vie
     */
    public void creerInterface(){
/******************** Gestion de la fenêtre ********************/
        JFrame fenetre = new JFrame("Le super jeu de la vie");
        fenetre.setResizable(false);
        fenetre.setSize(new Dimension(670, 720));// regler la taille
        fenetre.setLocationRelativeTo(null); // centrer la fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //gestion de la fermeture de la fenetre
        fenetre.setVisible(true); // afficher

/******************** Gestion du container principal ********************/
        JPanel boxPrincipale = new JPanel();
        boxPrincipale.setBorder(new EmptyBorder(10,10,10,10));
        boxPrincipale.setLayout(new BoxLayout(boxPrincipale, BoxLayout.X_AXIS));

/******************** Gestion de la grille ********************/
        this.setPreferredSize(new Dimension(jeu.getYMax()*4,jeu.getXMax()*4));
        this.setLayout(new BorderLayout(5,5));
        boxPrincipale.add(this);

/********************  Gestion de la partie droite de l'application ********************/
        JPanel droite = new JPanel();
        droite.setLayout(new BorderLayout(5,25));
        droite.setMinimumSize(new Dimension(400,400));
        droite.setPreferredSize(new Dimension(400,400));

        JPanel haut = new JPanel();
        haut.setLayout(new BorderLayout(5,25));

/******************** Gestion de l'affichage du nombre de générations + nombre de cellules vivantes ********************/
        JPanel nbGen = new JPanel();
        infos = new JTextArea(2,20);
        infos.setEditable(false);
        nbGen.setPreferredSize(new Dimension(300,50));
        infos.setText("Génération numéro "+jeu.getNumGeneration()+"\nCellules vivantes : "+jeu.calculerNbCellulesVivantes());
        nbGen.add(infos);

        haut.add(nbGen);

/******************** Gestion du positionnement des boutons de lancement - pause - mode 'pas à pas' ********************/
        JPanel boutons = new JPanel();
        boutons.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnLancer = new JButton("Lancer", new ImageIcon("./assets/play.png"));
        JButton btnPause = new JButton("Pause", new ImageIcon("./assets/pause.png"));
        JButton btnSuivant = new JButton("Suivant", new ImageIcon("./assets/suivant.png"));

/******************** Gestion du bouton de lancement ********************/
        btnLancer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!enCours) lancer();
            }
        });

/******************** Gestion du bouton de pause ********************/
        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { arreter(); }
        });

/******************** Gestion du bouton pour avancer "pas à pas" ********************/
        btnSuivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!enCours){
                    updateAffichage();
                    jeu.calculerGenerationSuivante();
                }
            }
        });
/******************** Ajout des boutons ********************/
        boutons.add(btnLancer);
        boutons.add(btnPause);
        boutons.add(btnSuivant);
        haut.add(boutons, BorderLayout.SOUTH);
        droite.add(haut, BorderLayout.NORTH);

        JPanel options = new JPanel();
        options.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 35));

/******************** Gestion du slider pour la vitesse ********************/
        JSlider s = new JSlider(JSlider.HORIZONTAL,2,8,5);
        Hashtable labels = new Hashtable();
        labels.put( 2, new JLabel("Lent") );
        labels.put( 8, new JLabel("Rapide") );
        s.setLabelTable(labels);
        s.setMinorTickSpacing(1);
        s.setMajorTickSpacing(8);
        s.setPaintTicks(true);
        s.setPaintLabels(true);

/******************** Gestion de la vitesse ********************/
        s.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) { vitesse = s.getValue(); }
        });

        options.add(new JLabel("Vitesse"));
        options.add(s);

/******************** Gestion du choix des règles ********************/
        JPanel choixRegles = new JPanel();
        choixRegles.setLayout(new FlowLayout(FlowLayout.TRAILING, 15, 5));
        choixRegles.add(new JLabel("Choix des règles"));

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Classique");
        comboBox.addItem("HighLife");
        comboBox.addItem("Day & Night");
        comboBox.setSelectedIndex(0);

        Visiteur vClassic = new VisiteurClassique(jeu);
        Visiteur vHighLife = new VisiteurHighLife(jeu);
        Visiteur vDayNight = new VisiteurDayAndNight(jeu);

        jeu.setVisiteur(vClassic); // par défaut : mode classique

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choix = comboBox.getSelectedItem().toString();
                if(choix == "Classique"){ jeu.setVisiteur(vClassic); }
                else if(choix == "HighLife"){ jeu.setVisiteur(vHighLife); }
                else { jeu.setVisiteur(vDayNight); }
            }
        });

        choixRegles.add(comboBox);
        options.add(choixRegles);

/******************** Gestion du reset ********************/
        JButton reset = new JButton("Réinitialiser", new ImageIcon("./assets/reset.png"));
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAffichage();
                System.out.println("Reinitialiser");
                jeu.initialiseGrille();
            }
        });

        options.add(reset);

/******************** Gestion du choix de la taille de la grille ********************/
        JPanel choixTaille = new JPanel();
        choixTaille.setLayout(new FlowLayout(FlowLayout.TRAILING, 15, 5));
        choixTaille.add(new JLabel("Choix de la taille"));

        JComboBox<String> comboBox2 = new JComboBox<>();
        comboBox2.addItem("80x80");
        comboBox2.addItem("100x100");
        comboBox2.addItem("125x125");
        comboBox2.addItem("150x150");
        comboBox2.setSelectedIndex(1);

        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choix = comboBox2.getSelectedItem().toString();
                int valeur;

                if(choix == "80x80") { valeur = 80; }
                else if(choix == "125x125") { valeur = 125; }
                else if(choix == "150x150"){  valeur = 150; }
                else{ valeur = 100; }

                jeu.setXMax(valeur);
                jeu.setYMax(valeur);
                jeu.initialiseGrille();
                updateAffichage();
                lancer();
            }
        });

        choixTaille.add(comboBox2);
        options.add(choixTaille);


        droite.add(options, BorderLayout.CENTER);
        boxPrincipale.add(droite);

        fenetre.add(boxPrincipale);
        fenetre.pack();
    }

    /**
     * Methode qui est lancer quand l'automate est en cours (Thread en cours)
     */
    @Override
    public void run() {
        while(enCours){
            updateAffichage();
            jeu.calculerGenerationSuivante();

            try {
                int tmp = vitesse;
/******************** Gestion de la vitesse (en fonction de de la position du curseur sur le slider) ********************/
                if(tmp == 5) tmp *= 100;
                else if(tmp > 5) tmp = 1000 - (tmp*100);
                else tmp = 500 + (tmp*100);

                Thread.sleep(tmp);
            }catch(InterruptedException ex){
                System.out.println("Erreur : "+ex);
            }
        }
    }

    /**
     * Methode qui met a jour l'affichage le numero de la generation et les cellulles vivantes
     */
    public void updateAffichage(){
        this.infos.setText("Génération numéro "+jeu.getNumGeneration()+"\nCellules vivantes : "+jeu.calculerNbCellulesVivantes());
    }
}
