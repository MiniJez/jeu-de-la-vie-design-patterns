package jeuDeLaVie.observateurs;

import jeuDeLaVie.JeuDeLaVie;

import jeuDeLaVie.cellules.Cellule;
import jeuDeLaVie.visiteurs.Visiteur;
import jeuDeLaVie.visiteurs.VisiteurClassique;
import jeuDeLaVie.visiteurs.VisiteurDayAndNight;
import jeuDeLaVie.visiteurs.VisiteurHighLife;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.util.Hashtable;

/**
 * Classe qui gere l'interface du jeu de la vie.
 * DESIGN PATTERN : OBSERVATEUR
 */
public class JeuDeLaVieUI extends JPanel implements Observateur, Runnable {
    /** Jeu de la vie */
    private final JeuDeLaVie jeu;
    /** Booleen pour savoir si le jeu est lance ou en pause */
    private boolean enCours;
    /** Thread pour l'affichage */
    private Thread thread;
    /** Vitesse de le rafraichissement de l'affichage */
    private int vitesse;
    /** Zone de texte ou est affiche le numero de la generation en cours ainsi que le nombre cellules vivantes */
    private JTextArea infos;

    /** Couleur pour les cellules vivantes */
    private Color coulVivantes;
    /** Couleur pour les cellules mortes */
    private Color coulMortes;

    /** Couleur marron */
    private static final Color MARRON = new Color(103,68,0);
    /** Couleur orange */
    private static final Color ORANGE = new Color(255,100,31);
    /** Couleur verte */
    private static final Color VERT = new Color(30,95,20);
    /** Couleur bleue */
    private static final Color BLEU = new Color(0,43,155);
    /** Couleur violette */
    private static final Color VIOLET = new Color(129,0,185);
    /** Couleur grise */
    private static final Color GRIS = new Color(220,220,220);

    /**
     * Constructeur de JeuDeLaVieUI
     * @param jeu jeu de la vie
     */
    public JeuDeLaVieUI(JeuDeLaVie jeu) {
        this.jeu = jeu;
        // Valeurs par défaut
        this.vitesse = 5;
        this.coulVivantes = BLEU;
        this.coulMortes = GRIS;
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

        for(int x = 0; x < jeu.getXMax(); x++){
            for(int y = 0; y < jeu.getYMax(); y++){
                Cellule c = jeu.getGrilleXY(x,y);
                if( c != null && c.estVivante() ){ g.setColor(coulVivantes); }
                else{ g.setColor(coulMortes); }
                g.fillRect(x*4, y*4, 3, 3);
            }
        }
    }

    /**
     * Methode qui permet de creer l'interface du jeu de la vie
     */
    public void creerInterface(){
/*----------------------------- Gestion de la fenêtre -----------------------------*/
        JFrame fenetre = new JFrame("Le super jeu de la vie");
        fenetre.setResizable(false);
        fenetre.setSize(new Dimension(670, 720));// regler la taille
        fenetre.setLocationRelativeTo(null); // centrer la fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //gestion de la fermeture de la fenetre
        fenetre.setVisible(true); // afficher

/*----------------------------- Gestion du container principal -----------------------------*/
        JPanel boxPrincipale = new JPanel();
        boxPrincipale.setBorder(new EmptyBorder(10,10,10,10));
        boxPrincipale.setLayout(new BoxLayout(boxPrincipale, BoxLayout.X_AXIS));

/*----------------------------- Gestion de la grille -----------------------------*/
        this.setPreferredSize(new Dimension(jeu.getYMax()*4,jeu.getXMax()*4));
        this.setLayout(new BorderLayout(5,5));
        boxPrincipale.add(this);

/*-----------------------------  Gestion de la partie droite de l'application -----------------------------*/
        JPanel droite = new JPanel();
        droite.setLayout(new BorderLayout(5,25));
        droite.setMinimumSize(new Dimension(400,400));
        droite.setPreferredSize(new Dimension(400,400));

        JPanel haut = new JPanel();
        haut.setLayout(new BorderLayout(5,25));

/*----------------------------- Gestion de l'affichage du nombre de générations + nombre de cellules vivantes -----------------------------*/
        JPanel nbGen = new JPanel();
        infos = new JTextArea(2,20);
        infos.setEditable(false);
        nbGen.setPreferredSize(new Dimension(300,50));
        infos.setText("Génération numéro "+jeu.getNumGeneration()+"\nCellules vivantes : "+jeu.calculerNbCellulesVivantes());

        nbGen.add(infos);
        haut.add(nbGen);

/*----------------------------- Gestion du positionnement des boutons de lancement - pause - mode 'pas à pas' -----------------------------*/
        JPanel boutons = new JPanel();
        boutons.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnLancer = new JButton("Lancer", new ImageIcon("assets/play.png"));
        JButton btnPause = new JButton("Pause", new ImageIcon("assets/pause.png"));
        JButton btnSuivant = new JButton("Suivant", new ImageIcon("assets/suivant.png"));

/*----------------------------- Gestion du bouton de lancement -----------------------------*/
        btnLancer.addActionListener(evenement-> { if(!enCours) lancer(); });

/*----------------------------- Gestion du bouton de pause -----------------------------*/
        btnPause.addActionListener(evenement-> arreter());

/*----------------------------- Gestion du bouton pour avancer "pas à pas" -----------------------------*/
        btnSuivant.addActionListener(evenement-> {
            if(enCours){ arreter(); }
            if(!enCours){
                updateAffichage();
                jeu.calculerGenerationSuivante();
            }
        });

/*----------------------------- Ajout des boutons -----------------------------*/
        boutons.add(btnLancer);
        boutons.add(btnPause);
        boutons.add(btnSuivant);
        haut.add(boutons, BorderLayout.SOUTH);
        droite.add(haut, BorderLayout.NORTH);

/*----------------------------- Panneau des options -----------------------------*/
        JPanel options = new JPanel();
        options.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 35));

/*----------------------------- Gestion du slider pour la vitesse -----------------------------*/
        JSlider s = new JSlider(JSlider.HORIZONTAL,2,8,5);
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put( 2, new JLabel("Lent") );
        labels.put( 8, new JLabel("Rapide") );
        s.setLabelTable(labels);
        s.setMinorTickSpacing(1);
        s.setMajorTickSpacing(8);
        s.setPaintTicks(true);
        s.setPaintLabels(true);

/*----------------------------- Gestion de la vitesse -----------------------------*/
        s.addChangeListener(change -> vitesse = s.getValue());

        options.add(new JLabel("Vitesse"));
        options.add(s);

/*----------------------------- Gestion du choix des règles -----------------------------*/
        JPanel choixRegles = new JPanel();
        choixRegles.setLayout(new FlowLayout(FlowLayout.TRAILING, 15, 5));
        choixRegles.add(new JLabel("Choix des règles"));

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Classique");
        comboBox.addItem("HighLife");
        comboBox.addItem("Day & Night");
        comboBox.setSelectedIndex(0);

        // Création des visiteurs pour les différentes règles du jeu
        Visiteur vClassic = new VisiteurClassique(jeu);
        Visiteur vHighLife = new VisiteurHighLife(jeu);
        Visiteur vDayNight = new VisiteurDayAndNight(jeu);

        jeu.setVisiteur(vClassic); // par défaut : mode classique

        comboBox.addActionListener(change -> {
            String choix = comboBox.getSelectedItem().toString();
            if(choix.equals("Classique")){ jeu.setVisiteur(vClassic); }
            else if(choix.equals("HighLife")){ jeu.setVisiteur(vHighLife); }
            else { jeu.setVisiteur(vDayNight); }
        });

        choixRegles.add(comboBox);
        options.add(choixRegles);

/*----------------------------- Gestion du reset -----------------------------*/
        JButton reset = new JButton("Réinitialiser", new ImageIcon("assets/reset.png"));
        reset.addActionListener(evenement -> {
            updateAffichage();
            System.out.println("Reinitialiser");
            jeu.initialiseGrille();
        });

        options.add(reset);

/*----------------------------- Gestion du choix de la taille de la grille -----------------------------*/
        JPanel choixTaille = new JPanel();
        choixTaille.setLayout(new FlowLayout(FlowLayout.TRAILING, 15, 5));
        choixTaille.add(new JLabel("Taille grille"));

        JComboBox<String> comboBox2 = new JComboBox<>();
        comboBox2.addItem("80x80");
        comboBox2.addItem("100x100");
        comboBox2.addItem("125x125");
        comboBox2.addItem("150x150");
        comboBox2.addItem("170x170");
        comboBox2.setSelectedIndex(4);

        // Gestion du changement de taille de la grille
        comboBox2.addActionListener(evenement -> {
            String choix = comboBox2.getSelectedItem().toString();
            int valeur = switch (choix) {
                case "80x80" -> 80;
                case "100x100" -> 100;
                case "125x125" -> 125;
                case "150x150" -> 150;
                default -> 170;
            };

            jeu.setXMax(valeur);
            jeu.setYMax(valeur);

            jeu.initialiseGrille();
            s.setValue(5);
            vitesse = 5;
            updateAffichage();

            lancer();
        });

        choixTaille.add(comboBox2);
        options.add(choixTaille);

/*----------------------------- Gestion du choix des couleurs des cellules vivantes -----------------------------*/
        JPanel choixCoulV = new JPanel();
        choixCoulV.setLayout(new FlowLayout(FlowLayout.TRAILING, 15, 5));
        choixCoulV.add(new JLabel("Cellules vivantes"));

        JComboBox<String> coulV = new JComboBox<>();
        coulV.addItem("Bleu");
        coulV.addItem("Noir");
        coulV.addItem("Marron");
        coulV.addItem("Orange");
        coulV.addItem("Vert");
        coulV.addItem("Violet");
        coulV.addItem("Rouge");
        coulV.setSelectedIndex(0);

        // Gestion du changement de couleur pour les cellules vivantes
        coulV.addActionListener(evenement -> {
            String choix = coulV.getSelectedItem().toString();

            switch (choix) {
                case "Noir" -> coulVivantes = Color.BLACK;
                case "Marron" -> coulVivantes = MARRON;
                case "Orange" -> coulVivantes = ORANGE;
                case "Vert" -> coulVivantes = VERT;
                case "Violet" -> coulVivantes = VIOLET;
                case "Rouge" -> coulVivantes = Color.RED;
                default -> coulVivantes = BLEU;
            }
        });
        choixCoulV.add(coulV);

/*----------------------------- Gestion du choix des couleurs des cellules mortes -----------------------------*/
        JPanel choixCoulM = new JPanel();
        choixCoulM.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));
        choixCoulM.add(new JLabel("Cellules mortes"));

        JComboBox<String> coulM = new JComboBox<>();
        coulM.addItem("Gris");
        coulM.addItem("Blanc");
        coulM.setSelectedIndex(0);

        // Gestion du changement de couleur pour les cellules mortes
        coulM.addActionListener(evenement -> {
            String choix = coulM.getSelectedItem().toString();

            if(choix.equals("Gris")){ coulMortes = GRIS; }
            else{ coulMortes = Color.WHITE; }
        });
        choixCoulM.add(coulM);

        options.add(choixCoulV);
        options.add(choixCoulM);

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
/*----------------------------- Gestion de la vitesse (en fonction de de la position du curseur sur le slider) -----------------------------s*/
                if(tmp == 5) tmp *= 100;
                else if(tmp > 5) tmp = 1000 - (tmp*100);
                else tmp = 500 + (tmp*100);

                Thread.sleep(tmp); // temps a attendre entre 2 générations
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
