public class VisiteurClassique extends Visiteur {

    public VisiteurClassique(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(this.jeu);

        if(nbVoisins < 2 || nbVoisins > 3){
            this.jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
//        else{
//            Visiteur.jeu.ajouteCommande(new CommandeVit(cellule));
//        }


    }

    @Override
    void visiteCelluleMorte(Cellule cellule) {
        if(cellule.nombreVoisinesVivantes(this.jeu) == 3){
            this.jeu.ajouteCommande(new CommandeVit(cellule));
        }
//        else{
//            Visiteur.jeu.ajouteCommande(new CommandeMeurt(cellule));
//        }


    }
}
