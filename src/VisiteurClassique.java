public class VisiteurClassique extends Visiteur {

    public VisiteurClassique(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(Visiteur.jeu);

        if(nbVoisins < 2 || nbVoisins > 3){
            cellule.meurt();
        }

        Visiteur.jeu.ajouteCommande(new CommandeVit(cellule));
    }

    @Override
    void visiteCelluleMorte(Cellule cellule) {
        if(cellule.nombreVoisinesVivantes(Visiteur.jeu) == 3){
            cellule.vit();
        }

        Visiteur.jeu.ajouteCommande(new CommandeMeurt(cellule));
    }
}
