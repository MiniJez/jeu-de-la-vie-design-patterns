public class CelluleEtatVivant implements CelluleEtat {
    private CelluleEtatVivant instanceUnique = new CelluleEtatVivant();

    private CelluleEtatVivant(){}


    public CelluleEtatVivant getInstance(){
        return instanceUnique;
    }


    @Override
    public CelluleEtat vit() {
        return this;
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
