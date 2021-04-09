import java.util.ArrayList;
import java.util.List;

public class JeuDeLaVie implements Observable {
    private Cellule[][] grille;
    private int xMax;
    private int yMax;
    private List<Observateur> observateurs;
    private List<Commande> commandes;
    private Visiteur visiteur;

    public JeuDeLaVie() {
        this.observateurs = new ArrayList<>();
        this.commandes = new ArrayList<>();
    }

    public static void main(String[] args) {
        JeuDeLaVie jeu = new JeuDeLaVie();
        jeu.setXMax(150);
        jeu.setYMax(150);
        jeu.initialiseGrille();
        jeu.setVisiteur(new VisiteurClassique(jeu));

        JeuDeLaVieUI fenetre = new JeuDeLaVieUI(jeu);
        jeu.attacheObservateur(fenetre);


        while (fenetre.isVisible()){
            jeu.calculerGenerationSuivante();
        }

    }

    public void initialiseGrille() {
        this.grille = new Cellule[xMax][yMax];

        for(int x = 0; x < xMax-1; x++){
            for(int y = 0; y < yMax-1; y++){
                int rand = (int) (0 + (Math.random() * 2));

                if(rand == 0) grille[x][y] = new Cellule(x, y, CelluleEtatMort.getInstance());
                else grille[x][y] = new Cellule(x, y, CelluleEtatVivant.getInstance());
            }
        }
        System.out.println("Initialisation OK");
    }

    public Cellule getGrilleXY(int x, int y){
        if( (x >= 0) && (y >= 0) && (x <= xMax) && (y <= yMax) ){
            return this.grille[x][y];
        }
        else {
            return null;
        }
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
        executeCommandes();
        notifieObservateurs();

    }
}
