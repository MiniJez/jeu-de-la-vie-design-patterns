public abstract class Visiteur {
    protected JeuDeLaVie jeu;

    public Visiteur(JeuDeLaVie jeu){
        this.jeu = jeu;
    }

    abstract void visiteCelluleVivante(Cellule cellule);
    abstract void visiteCelluleMorte(Cellule cellule);
}
