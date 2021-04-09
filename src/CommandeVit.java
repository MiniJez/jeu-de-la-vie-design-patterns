public class CommandeVit extends Commande{

    public CommandeVit(Cellule c){
        this.cellule = c;
    }

    @Override
    void executer() {
        this.cellule.vit();
    }
}
