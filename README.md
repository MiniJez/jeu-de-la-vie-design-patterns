## Jeu de la vie (*Game of Life*)
Projet réalisé dans le cadre du module "Design Pattern" de la 3ème année de Licence Informatique (Le Mans Université).

### Objectifs :

* Implémenter le principe du "Jeu de la vie" (aussi appelé "Atomate Cellulaire")
* Implémenter le jeu selon 5 Design Patterns :
  * Etat (*State*)
  * Singleton
  * jeuDeLaVie.observateurs.Observateur (*Observer*)
  * jeuDeLaVie.commandes.Commande (*Command*)
  * jeuDeLaVie.visiteurs.Visiteur (*Visitor*)

### Principe :

Un automate cellulaire est un objet mathématique qui permet de simuler l’évolution d’une population de
cellules virtuelles au cours du temps, selon des règles de voisinage :
* Si une cellule possède moins de 2 voisines, elle meurt;
* Si une cellule possède plus de 3 voisines, elle meurt aussi;
* Si un emplacement vide (ou cellule morte) possède 3 voisines (vivantes), une nouvelle cellule naît.

