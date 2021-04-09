import java.util.ArrayList;
import java.util.List;

public class JeuDeLaVie implements Observable {
    private Cellule[][] grille;
    private int xMax;
    private int yMax;
    private List<Observateur> observateurs;
    private List<Commande> commandes;

    public JeuDeLaVie(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        this.observateurs = new ArrayList<>();
        this.commandes = new ArrayList<>();
        this.grille = new Cellule[xMax][yMax];
        this.initialiseGrille();
    }

    public static void main(String[] args) {
        JeuDeLaVie jeu = new JeuDeLaVie(200,200);
        JeuDeLaVieUI fenetre = new JeuDeLaVieUI(jeu);

        jeu.attacheObservateur(fenetre);
        jeu.notifieObservateurs();
    }

    public void initialiseGrille() {
        for(int x = 0; x < xMax; x++){
            for(int y = 0; y < yMax; y++){
                int rand = (int) (0 + (Math.random() * 2));

                if(rand == 0) grille[x][y] = new Cellule(x, y, CelluleEtatMort.getInstance());
                else grille[x][y] = new Cellule(x, y, CelluleEtatVivant.getInstance());
            }
        }
    }

    public Cellule getGrilleXY(int x, int y){
        if( (x >= 0) && (y >= 0) && (x < xMax) && (y < yMax) ){
            return this.grille[x][y];
        }
        else{
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
}
