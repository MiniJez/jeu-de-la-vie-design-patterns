package jeuDeLaVie;

import jeuDeLaVie.cellules.Cellule;
import jeuDeLaVie.cellules.CelluleEtatMort;
import jeuDeLaVie.cellules.CelluleEtatVivant;
import jeuDeLaVie.commandes.Commande;
import jeuDeLaVie.observateurs.JeuDeLaVieUI;
import jeuDeLaVie.observateurs.Observable;
import jeuDeLaVie.observateurs.Observateur;
import jeuDeLaVie.observateurs.ObservateurGeneration;
import jeuDeLaVie.visiteurs.Visiteur;
import jeuDeLaVie.visiteurs.VisiteurClassique;
import jeuDeLaVie.visiteurs.VisiteurDayAndNight;
import jeuDeLaVie.visiteurs.VisiteurHighLife;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JeuDeLaVie implements Observable {
    private Cellule[][] grille;
    private int xMax;
    private int yMax;

    private int numGeneration;
    private int nbCellulesVivantes;
    private int vitesse;

    private List<Observateur> observateurs;
    private List<Commande> commandes;

    private Visiteur visiteur;

    public JeuDeLaVie() {
        this.observateurs = new ArrayList<>();
        this.commandes = new ArrayList<>();
        this.numGeneration = 0;
        this.vitesse = 5;
    }

    public static void main(String[] args) {
        JFrame fenetre = new JFrame("Le super jeu de la vie");

        fenetre.setSize(new Dimension(670, 720));// regler la taille
        fenetre.setLocationRelativeTo(null); // centrer la fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //gestion de la fermeture de la fenetre
        fenetre.setVisible(true); // afficher

        JeuDeLaVie jeu = new JeuDeLaVie();
        jeu.setXMax(150);
        jeu.setYMax(150);
        jeu.initialiseGrille();

        Visiteur vClassic = new VisiteurClassique(jeu);
        Visiteur vHighLife = new VisiteurHighLife(jeu);
        Visiteur vDayNight = new VisiteurDayAndNight(jeu);

        jeu.setVisiteur(vClassic);

        ObservateurGeneration og = new ObservateurGeneration(jeu);
        jeu.attacheObservateur(og);

        JPanel boxPrincipale = new JPanel();
        boxPrincipale.setLayout(new BoxLayout(boxPrincipale, BoxLayout.Y_AXIS));

        JeuDeLaVieUI jdv = new JeuDeLaVieUI(jeu);
        jeu.attacheObservateur(jdv);

        jdv.setPreferredSize(new Dimension(600,600));
        jdv.setBounds(50,25,600,600);
        boxPrincipale.add(jdv);

        JPanel panneau = new JPanel();
        panneau.setPreferredSize(new Dimension(700,100));

        JButton btnLancer = new JButton("Lancer", new ImageIcon("./assets/play.png"));
        JButton btnPause = new JButton("Pause", new ImageIcon("./assets/pause.png"));
        JButton btnSuivant = new JButton("Suivant", new ImageIcon("./assets/suivant.png"));

        btnLancer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Lancement cliqué");
                jdv.lancer();
            }
        });

        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pause cliqué");
                jdv.arreter();
            }
        });

        btnSuivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Suivant cliqué");
                jeu.calculerGenerationSuivante();
            }
        });

        panneau.add(btnLancer);
        panneau.add(btnPause );
        panneau.add(btnSuivant);

        JPanel slider = new JPanel();
        JSlider s = new JSlider(JSlider.HORIZONTAL,2,8,5);
        s.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jeu.vitesse = s.getValue();
            }
        });

        slider.setLayout(new FlowLayout(FlowLayout.TRAILING, 15, 5));
        slider.add(new JLabel("Vitesse"));
        slider.add(s);
        panneau.add(slider);

        panneau.add(new JLabel("Choix des règles"));

        JComboBox<String> choixRegles = new JComboBox<>();
        choixRegles.addItem("Classique");
        choixRegles.addItem("HighLife");
        choixRegles.addItem("Day & Night");
        choixRegles.setSelectedIndex(0);

        choixRegles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choix = choixRegles.getSelectedItem().toString();
                if(choix == "Classique"){ jeu.setVisiteur(vClassic); }
                else if(choix == "HighLife"){ jeu.setVisiteur(vHighLife); }
                else { jeu.setVisiteur(vDayNight); }
                System.out.println("Regles : "+choix);
            }
        });

        panneau.add(choixRegles);

        boxPrincipale.add(panneau);

        fenetre.add(boxPrincipale);
        fenetre.pack();
    }

    public void initialiseGrille() {
        this.grille = new Cellule[xMax][yMax];
        this.nbCellulesVivantes = 0;

        for(int x = 0; x < xMax-1; x++){
            for(int y = 0; y < yMax-1; y++){
                Random random = new Random();
                int rand = random.nextInt() % 2;

                if(rand == 0){
                    grille[x][y] = new Cellule(x, y, CelluleEtatMort.getInstance());
                }
                else{
                    grille[x][y] = new Cellule(x, y, CelluleEtatVivant.getInstance());
                }
            }
        }
        calculerNbCellulesVivantes();
        System.out.println("Initialisation OK");
    }

    public Cellule getGrilleXY(int x, int y){
        // Vérification qu'on est bien dans la grille
        if( (x >= 0) && (y >= 0) && (x <= xMax) && (y <= yMax) ){ return this.grille[x][y]; }
        else {  return null; }
    }

    @Override
    public void attacheObservateur(Observateur o) { this.observateurs.add(o); }

    @Override
    public void detacheObservateur(Observateur o) { this.observateurs.remove(o); }

    @Override
    public void notifieObservateurs() {
        for(Observateur o : observateurs){
            o.actualise();
        }
    }

    public void ajouteCommande(Commande c){ this.commandes.add(c); }

    public void executeCommandes(){
        for(Commande c : commandes){
            c.executer();
        }
        this.commandes.clear();
    }

    public void distribueVisiteur(){
        for(int x = 0; x < xMax-1; x++){
            for(int y = 0; y < yMax-1; y++){
                getGrilleXY(x,y).accepte(visiteur);
            }
        }
    }

    public void calculerGenerationSuivante(){
        distribueVisiteur();
        executeCommandes();
        notifieObservateurs();
        this.numGeneration++;
    }

    public int calculerNbCellulesVivantes(){
        int cpt = 0;
        for(int x = 0; x < xMax-1; x++) {
            for (int y = 0; y < yMax - 1; y++) {
                if(getGrilleXY(x,y).estVivante()) cpt++;
            }
        }
        this.nbCellulesVivantes = cpt;
        return this.nbCellulesVivantes;
    }

    public void setXMax(int xMax) { this.xMax = xMax; }

    public void setYMax(int yMax) { this.yMax = yMax; }

    public void setVisiteur(Visiteur visiteur) { this.visiteur = visiteur; }

    public int getXMax() { return xMax; }

    public int getYMax() { return yMax; }

    public int getVitesse() { return vitesse; }

    public int getNumGeneration() { return numGeneration; }
}
