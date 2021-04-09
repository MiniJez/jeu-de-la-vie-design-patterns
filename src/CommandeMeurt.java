public class CommandeMeurt extends Commande{

    public CommandeMeurt(Cellule c){
        this.cellule = c;
    }

    @Override
    void executer() {
        this.cellule.meurt();
    }
}
