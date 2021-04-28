/**
 * @author : CHAUMULON Cassandra
 */

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

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Classe qui s'occupe de la partie de la logique du jeu.
 */
public class JeuDeLaVie implements Observable {
    /** Grille de cellule du jeu */
    private Cellule[][] grille;
    /** Valeur maximum pour les x */
    private int xMax;
    /** Valeur maximum pour les y*/
    private int yMax;

    /** Numero pour la generation actuelle */
    private int numGeneration;
    /** Nombre de cellules vivantes */
    private int nbCellulesVivantes;

    /** Liste des observateurs */
    private final List<Observateur> observateurs;
    /** Liste des des actions a effectuer (= modification des cellules) */
    private final List<Commande> commandes;

    /** Visiteur : regles du jeu a prendre en compte */
    private Visiteur visiteur;

    /**
     * Constructeur du jeu de la vie
     */
    public JeuDeLaVie() {
        this.observateurs = new CopyOnWriteArrayList<>();
        this.commandes = new CopyOnWriteArrayList<>();
    }

    /**
     * Programme principal
     * @param args arguments en ligne de commande
     */
    public static void main(String[] args) {
        JeuDeLaVie jeu = new JeuDeLaVie();
        // Taille de la grille par défaut : 80 x 80
        jeu.setXMax(80);
        jeu.setYMax(80);
        jeu.initialiseGrille();

        // Attachement de l'observateur
        ObservateurGeneration og = new ObservateurGeneration(jeu);
        jeu.attacheObservateur(og);

        // Création de l'interface
        JeuDeLaVieUI jdv = new JeuDeLaVieUI(jeu);
        jeu.attacheObservateur(jdv);

        jdv.creerInterface();
    }

    /**
     * Methode qui permet d'initialiser la grille ou de la remettre a zero.
     * Génération aléatoire des cellules vivantes et mortes.
     */
    public void initialiseGrille() {
        // Création de la grille == tableau de cellules
        this.grille = new Cellule[xMax][yMax];
        this.numGeneration = 0; // generation initiale
        this.nbCellulesVivantes = 0; // compteur des cellules vivantes

        for(int x = 0; x < xMax-1; x++){
            for(int y = 0; y < yMax-1; y++){
                // Generation d'un chiffre aléatoire => Si 0 == cellule morte, sinon == cellule vivante
                Random random = new Random();
                int rand = random.nextInt() % 2;

                if(rand == 0){ grille[x][y] = new Cellule(x, y, CelluleEtatMort.getInstance()); }
                else{ grille[x][y] = new Cellule(x, y, CelluleEtatVivant.getInstance()); }
            }
        }
        calculerNbCellulesVivantes();
        System.out.println("Initialisation OK");
    }

    /**
     * Methode qui permet d'initialiser une grille avec un canon qui emet des vaisseaux a intervalle regulier.
     * Creer une grille 80x80
     */
    public void initialiserCanon(){
        setXMax(80);
        setYMax(80);
        this.grille = new Cellule[xMax][yMax];
        this.numGeneration = 0; // generation initiale
        this.nbCellulesVivantes = 0; // compteur des cellules vivantes

        // Remplir de cellules mortes
        for(int x = 0; x < xMax-1; x++){
            for(int y = 0; y < yMax-1; y++){
                grille[x][y] = new Cellule(x, y, CelluleEtatMort.getInstance());
            }
        }

        // Placement des cellules vivantes
        getGrilleXY(10,24).vit();
        getGrilleXY(10,25).vit();
        getGrilleXY(11,24).vit();
        getGrilleXY(11,25).vit();

        getGrilleXY(20,24).vit();
        getGrilleXY(20,25).vit();
        getGrilleXY(20,26).vit();

        getGrilleXY(21,23).vit();
        getGrilleXY(21,27).vit();

        getGrilleXY(22,22).vit();
        getGrilleXY(22,28).vit();

        getGrilleXY(23,22).vit();
        getGrilleXY(23,28).vit();

        getGrilleXY(24,25).vit();

        getGrilleXY(25,23).vit();
        getGrilleXY(25,27).vit();

        getGrilleXY(26,24).vit();
        getGrilleXY(26,25).vit();
        getGrilleXY(26,26).vit();

        getGrilleXY(27,25).vit();

        getGrilleXY(30,22).vit();
        getGrilleXY(30,23).vit();
        getGrilleXY(30,24).vit();

        getGrilleXY(31,22).vit();
        getGrilleXY(31,23).vit();
        getGrilleXY(31,24).vit();

        getGrilleXY(32,21).vit();
        getGrilleXY(32,25).vit();

        getGrilleXY(34,20).vit();
        getGrilleXY(34,21).vit();
        getGrilleXY(34,25).vit();
        getGrilleXY(34,26).vit();

        getGrilleXY(44,22).vit();
        getGrilleXY(44,23).vit();
        getGrilleXY(45,22).vit();
        getGrilleXY(45,23).vit();
    }
    /**
     * Methode qui permet recuperer une cellule en fonction de ses coordonnees
     * @param x coordonnee x
     * @param y coordonnee y
     * @return la cellule a la position (x,y)
     */
    public Cellule getGrilleXY(int x, int y){
        // Vérification qu'on est bien dans la grille
        if( (x >= 0) && (y >= 0) && (x <= xMax) && (y <= yMax) ){ return this.grille[x][y]; }
        else {  return null; }
    }

    /**
     * Methode qui permet d'attacher un observateur
     * @param o observateur a attacher
     */
    @Override
    public void attacheObservateur(Observateur o) { this.observateurs.add(o); }

    /**
     * Methode qui permet de detacher un observateur
     * @param o observateur a detacher
     */
    @Override
    public void detacheObservateur(Observateur o) { this.observateurs.remove(o); }

    /**
     * Methode qui permet d'avertir les observateurs d'un changement
     */
    @Override
    public void notifieObservateurs() {
        for(Observateur o : observateurs){
            o.actualise();
        }
    }

    /**
     * Methode qui permet d'ajouter une action a realiser dans la file d'attente
     * @param c commande a ajouter
     */
    public void ajouteCommande(Commande c){ this.commandes.add(c); }

    /**
     * Methode qui permet d'executer toutes les actions de la liste d'attente
     */
    public void executeCommandes(){
        for(Commande c : commandes){
            c.executer();
        }
        this.commandes.clear();
    }

    /**
     * Methode qui permet de distribuer le visiteur a chaque cellule de la grille
     */
    public void distribueVisiteur(){
        for(int x = 0; x < xMax-1; x++){
            for(int y = 0; y < yMax-1; y++){
                getGrilleXY(x,y).accepte(visiteur);
            }
        }
    }

    /**
     * Methode qui permet de calculer la generation de cellules suivantes :
     * - Distribue le visiteur a chaque cellule
     * - Execute les actions de la file d'attente
     * - Notifie les observateurs.
     */
    public void calculerGenerationSuivante(){
        distribueVisiteur();
        executeCommandes();
        notifieObservateurs();
        this.numGeneration++;
    }

    /**
     * Methode qui calcule le nombre de cellules vivantes a un moment donne
     * @return le nombre de cellules vivantes
     */
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

    /**
     * Setter de x maximum de la grille
     * @param xMax x max
     */
    public void setXMax(int xMax) { this.xMax = xMax; }

    /**
     * Setter du y maximum de la grille
     * @param yMax y max
     */
    public void setYMax(int yMax) { this.yMax = yMax; }

    /**
     * Setter du visiteur
     * @param visiteur visiteur
     */
    public void setVisiteur(Visiteur visiteur) { this.visiteur = visiteur; }

    /**
     * Getter du x maximum de la grille
     * @return le x max
     */
    public int getXMax() { return xMax; }

    /**
     * Getter du y maximum de la grille
     * @return y max
     */
    public int getYMax() { return yMax; }

    /**
     * Getter du numero de la generation actuelle
     * @return le numero de la generation
     */
    public int getNumGeneration() { return numGeneration; }
}
