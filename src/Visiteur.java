public abstract class Visiteur {
    protected static JeuDeLaVie jeu;

    public Visiteur(JeuDeLaVie jeu){
        Visiteur.jeu = jeu;
    }

    abstract void visiteCelluleVivante(Cellule cellule);
    abstract void visiteCelluleMorte(Cellule cellule);
}
