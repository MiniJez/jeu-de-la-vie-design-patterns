package jeuDeLaVie.observateurs;

/**
 * Interface d'un Observable
 */
public interface Observable {
    /**
     * Methode qui permet d'attacher un observateur
     * @param o observateur a attacher
     */
    void attacheObservateur(Observateur o);

    /**
     * Methode qui permet de detacher un observateur
     * @param o observateur a detacher
     */
    void detacheObservateur(Observateur o);

    /**
     * Methode qui permet d'avertir les observateurs d'un changement
     */
    void notifieObservateurs();
}
