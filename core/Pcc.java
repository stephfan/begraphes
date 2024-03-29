package core ;

import java.io.* ;
import base.Readarg ;

public class Pcc extends Algo {

    // Numero des sommets origine et destination
    protected int zoneOrigine ;
    protected int origine ;

    protected int zoneDestination ;
    protected int destination ;

    public Pcc(Graphe gr, PrintStream sortie, Readarg readarg) {
	super(gr, sortie, readarg) ;

	// Demander la zone et le sommet d'origine.
	if (this.version != Version.LOCAL)
	    this.zoneOrigine = readarg.lireInt ("ZONE du sommet d'origine ? ") ;
	else
	    this.zoneOrigine = gr.getZone () ;

	this.origine = readarg.lireInt ("Numero du sommet d'origine ? ") ;

	// Demander la zone et le sommet destination.
	if (this.version != Version.LOCAL)
	    this.zoneDestination = readarg.lireInt ("ZONE du sommet destination ? ");
	else
	    this.zoneOrigine = gr.getZone () ;

	this.destination = readarg.lireInt ("Numero du sommet destination ? ");
    }

    public void run() {

	System.out.println("Run PCC de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;

	// A vous d'implementer la recherche de plus court chemin.
    }

}
