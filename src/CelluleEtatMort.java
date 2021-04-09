public class CelluleEtatMort implements CelluleEtat {
    private static CelluleEtatMort instanceUnique = null;

    private CelluleEtatMort(){}

    public static CelluleEtat getInstance() {
        if(instanceUnique == null) return new CelluleEtatMort();
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
