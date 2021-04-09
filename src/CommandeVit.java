public class CommandeVit extends Commande{

    public CommandeVit(Cellule c){
        Commande.cellule = c;
    }

    @Override
    void executer() {
        Commande.cellule.vit();
    }
}
