public class CelluleEtatMort implements CelluleEtat {
    private CelluleEtatMort instanceUnique = new CelluleEtatMort();

    private CelluleEtatMort(){}

    public CelluleEtatMort getInstance() {
        return instanceUnique;
    }

    @Override
    public CelluleEtat vit() {
        return null;
    }

    @Override
    public CelluleEtat meurt() {
        return null;
    }

    @Override
    public boolean estVivante() {
        return false;
    }
}
