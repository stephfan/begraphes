package core ;

import java.io.* ;
import base.* ;

/**
 * Classe abstraite representant un algorithme (connexite, plus court chemin, etc.)
 */
public abstract class Algo {

    // Differentes versions de l'algorithme
    public enum Version { 
	/** L'algorithme s'execute en local seulement */
	LOCAL, 
	    
	/** L'algorithme est distribue', connexions avec sockets Java UDP (Datagrams) */
	DIST_JAVA_UDP,

	/** L'algorithme est distribue', connexions avec proxy C sur UDP */
	DIST_UDP,

	/** L'algorithme est distribue', connexions avec proxy C sur TCP */
        DIST_TCP, 

	/** L'algorithme est distribue', connexions avec proxy C sur Simp-TCP */
        DIST_SIMP_TCP,

        /** L'algorithme est distribue', connexions avec proxy C, le meilleur protocole est choisi a la volee */
        DIST_BEST
    }

    protected PrintStream sortie ;
    protected Graphe graphe ;
    protected Version version ;
    
    protected Algo(Graphe gr, PrintStream fichierSortie, Readarg readarg) {
	this.graphe = gr ;
	this.sortie = fichierSortie ;
	
	switch (readarg.lireInt("Version reseau\n" +
				"  0 = local\n" +
				"  1 = Java UDP\n" +
				"  2 = Proxy UDP\n" +
				"  3 = Proxy TCP\n" +
				"  4 = Proxy SimpTCP\n" +
				"  5 = Proxy Best\n\n" +
				"Votre choix : ")) {
	case 0 : this.version = Version.LOCAL ; break ;
	case 1 : this.version = Version.DIST_JAVA_UDP ; break ;
	case 2 : this.version = Version.DIST_UDP ; break ;
	case 3 : this.version = Version.DIST_TCP ; break ;
	case 4 : this.version = Version.DIST_SIMP_TCP ; break ;
	case 5 : this.version = Version.DIST_BEST ; break ;
	default: 
	    System.out.println("Mauvais choix de la version reseau.") ;
	    System.exit(1) ;
	}
    }
    
    public abstract void run() ;

}
