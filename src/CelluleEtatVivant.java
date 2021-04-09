public class CelluleEtatVivant implements CelluleEtat {
    private static CelluleEtatVivant instanceUnique = null;

    private CelluleEtatVivant(){}

    public static CelluleEtat getInstance(){
        if (instanceUnique == null) return new CelluleEtatVivant();
        return instanceUnique;
    }

    @Override
    public CelluleEtat vit() {
        return this;
    }

    @Override
    public CelluleEtat meurt() {
        return CelluleEtatMort.getInstance();
    }

    @Override
    public boolean estVivante() {
        return true;
    }

    @Override
    public void accepte(Visiteur visiteur, Cellule cellule) {
        visiteur.visiteCelluleVivante(cellule);
    }
}
