public class CelluleEtatMort implements CelluleEtat {
    private static CelluleEtatMort instanceUnique = new CelluleEtatMort();

    private CelluleEtatMort(){}

    public static CelluleEtatMort getInstance() {
        return instanceUnique;
    }

    @Override
    public CelluleEtat vit() {
        return CelluleEtatVivant.getInstance();
    }

    @Override
    public CelluleEtat meurt() {
        return this;
    }

    @Override
    public boolean estVivante() {
        return false;
    }
}
