public class JeuDeLaVie {
    private Cellule[][] grille;
    private int xMax;
    private int yMax;

    public JeuDeLaVie() {

    }

    public void initialiseGrille() {

    }

    public Cellule getGrilleXY(int x, int y){
        if( (x >= 0) && (y >= 0) && (x < xMax) && (y < yMax) ){
            return this.grille[x][y];
        }
        else{
            return null;
        }
    }

    // public int getxMax() {
    //     return xMax;
    // }

    // public int getyMax() {
    //     return yMax;
    // }
}
