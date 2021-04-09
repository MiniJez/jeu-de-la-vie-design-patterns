public class Cellule {
    private CelluleEtat etat;
    private int x;
    private int y;

    public Cellule(int x, int y, CelluleEtat etat){
        this.x = x;
        this.y = y;
        this.etat = etat;
    }

    public int nombreVoisinesVivantes(JeuDeLaVie jeu){
        int nbVoisin = 0;

        for(int numCol = x-1; numCol <= x+1; numCol++){
            for(int numLigne = y-1; numLigne <= y+1; numLigne++){

                if(numCol != x && numLigne != y){
                    Cellule cellule = jeu.getGrilleXY(numCol, numLigne);

                    if(cellule != null && cellule.estVivante()){
//                    if(cellule.estVivante()){
                        nbVoisin++;
                    }
                }

            }
        }
//        if( jeu.getGrilleXY(x-1, y-1) != null && jeu.getGrilleXY(x-1, y-1).estVivante() ) nbVoisin++;
//        if( jeu.getGrilleXY(x,      y-1) != null && jeu.getGrilleXY(x,      y-1).estVivante() ) nbVoisin++;
//        if( jeu.getGrilleXY(x+1, y-1) != null && jeu.getGrilleXY(x+1, y-1).estVivante() ) nbVoisin++;
//        if( jeu.getGrilleXY(x-1,    y) != null && jeu.getGrilleXY(x-1,      y).estVivante() ) nbVoisin++;
//        if( jeu.getGrilleXY(x+1,    y) != null && jeu.getGrilleXY(x+1,      y).estVivante() ) nbVoisin++;
//        if( jeu.getGrilleXY(x-1, y+1) != null && jeu.getGrilleXY(x-1, y+1).estVivante() ) nbVoisin++;
//        if( jeu.getGrilleXY(x,      y+1) != null && jeu.getGrilleXY(x,     y+1).estVivante() ) nbVoisin++;
//        if( jeu.getGrilleXY(x+1, y+1) != null && jeu.getGrilleXY(x+1, y+1).estVivante() ) nbVoisin++;



        return nbVoisin;
    }

    public void vit(){
        this.etat = etat.vit();
    }

    public void meurt(){
        this.etat = etat.meurt();
    }

    public boolean estVivante(){
        return this.etat.estVivante();
    }

    public void accepte(Visiteur visiteur){
        this.etat.accepte(visiteur, this);
    }
}