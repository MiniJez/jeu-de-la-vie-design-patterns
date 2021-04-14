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
import jeuDeLaVie.visiteurs.VisiteurHighLife;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JeuDeLaVie implements Observable {
    private Cellule[][] grille;
    private int xMax;
    private int yMax;
    private int numGeneration;
    private int nbCellulesVivantes;

    private List<Observateur> observateurs;
    private List<Commande> commandes;

    private Visiteur visiteur;

    public JeuDeLaVie() {
        this.observateurs = new ArrayList<>();
        this.commandes = new ArrayList<>();
        this.numGeneration = 0;
    }

    public static void main(String[] args) {
        JFrame fenetre = new JFrame("Le super jeu de la vie");

        fenetre.setSize(new Dimension(620, 750));// regler la taille
        fenetre.setLayout(new BorderLayout(10, 10));
        fenetre.setLocationRelativeTo(null); // centrer la fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //gestion de la fermeture de la fenetre
        fenetre.setVisible(true); // afficher

        JeuDeLaVie jeu = new JeuDeLaVie();
        jeu.setXMax(150);
        jeu.setYMax(150);
        jeu.initialiseGrille();

//        jeu.setVisiteur(new VisiteurClassique(jeu));
        jeu.setVisiteur(new VisiteurHighLife(jeu));

        JeuDeLaVieUI jdv = new JeuDeLaVieUI(jeu);
        jeu.attacheObservateur(jdv);
        
        fenetre.add(jdv, BorderLayout.CENTER);


        ObservateurGeneration og = new ObservateurGeneration(jeu);
        jeu.attacheObservateur(og);

        while(fenetre.isVisible()){
            jeu.calculerGenerationSuivante();
        }
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

    public void setXMax(int xMax) {
        this.xMax = xMax;
    }

    public void setYMax(int yMax) {
        this.yMax = yMax;
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMax() {
        return yMax;
    }

    @Override
    public void attacheObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    @Override
    public void detacheObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    @Override
    public void notifieObservateurs() {
        for(Observateur o : observateurs){
            o.actualise();
        }
    }

    public void ajouteCommande(Commande c){
        this.commandes.add(c);
    }

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

    public void setVisiteur(Visiteur visiteur) {
        this.visiteur = visiteur;
    }

    public void calculerGenerationSuivante(){
        distribueVisiteur();
        try{
            Thread.sleep(400);
            executeCommandes();
            notifieObservateurs();
            this.numGeneration++;
        } catch (InterruptedException ie) {
            System.out.println("Erreur : " + ie);
        }
    }

    public int getNumGeneration() {
        return numGeneration;
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

}
