public class JeuDeLaVie {
    private Cellule[][] grille;
    private int xMax;
    private int yMax;

    public JeuDeLaVie(){

    }

    public void initialiseGrille(){

    }

    public Cellule getGrilleXY(int x, int y){
        return this.grille[x][y];
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMax() {
        return yMax;
    }
}
