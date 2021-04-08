public class CelluleEtatVivant implements CelluleEtat {
    private static CelluleEtatVivant instanceUnique = new CelluleEtatVivant();

    private CelluleEtatVivant(){}


    public static CelluleEtatVivant getInstance(){
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
}
