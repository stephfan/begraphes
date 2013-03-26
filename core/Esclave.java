package core ;

import java.io.* ;
import base.Readarg ;

// Ecoute une socket UDP et repond aux requetes.
// Utile pour la partie distribuee de l'algorithme.
public class Esclave extends Algo {

    public Esclave(Graphe gr, PrintStream sortie, Readarg readarg) {
	super(gr, sortie, readarg) ;
    }

    public void run() {

	sortie.println("Lancement de l'esclave") ;

	// A vous d'implementer l'ecoute de la socket UDP et la reponse aux requetes.
    }

}
