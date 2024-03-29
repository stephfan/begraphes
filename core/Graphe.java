package core ;

/**
 *   Classe representant un graphe.
 *   A vous de completer selon vos choix de conception.
 */

import java.io.* ;
import base.* ;

public class Graphe {

    // Nom de la carte utilisee pour construire ce graphe
    private final String nomCarte ;

    // Fenetre graphique
    private final Dessin dessin ;

    // Version du format MAP utilise'.
    private static final int version_map = 4 ;
    private static final int magic_number_map = 0xbacaff ;

    // Version du format PATH.
    private static final int version_path = 1 ;
    private static final int magic_number_path = 0xdecafe ;

    // Identifiant de la carte
    private int idcarte ;

    // Numero de zone de la carte
    private int numzone ;

    /*
     * Ces attributs constituent une structure ad-hoc pour stocker les informations du graphe.
     * Vous devez modifier et ameliorer ce choix de conception simpliste.
     */
    // private float[] longitudes ;
    // private float[] latitudes ;
    
		private Sommet[] sommets;
		private Descripteur[] descripteurs ;
    
    // Deux malheureux getters.
    public Dessin getDessin() { return dessin ; }
    public int getZone() { return numzone ; }

    // Le constructeur cree le graphe en lisant les donnees depuis le DataInputStream
    public Graphe (String nomCarte, DataInputStream dis, Dessin dessin) {

	this.nomCarte = nomCarte ;
	this.dessin = dessin ;
	Utils.calibrer(nomCarte, dessin) ;
	
	// Lecture du fichier MAP. 
	// Voir le fichier "FORMAT" pour le detail du format binaire.
	try {

	    // Nombre d'aretes
	    int edges = 0 ;

	    // Verification du magic number et de la version du format du fichier .map
	    int magic = dis.readInt () ;
	    int version = dis.readInt () ;
	    Utils.checkVersion(magic, magic_number_map, version, version_map, nomCarte, ".map") ;

	    // Lecture de l'identifiant de carte et du numero de zone, 
	    this.idcarte = dis.readInt () ;
	    this.numzone = dis.readInt () ;

	    // Lecture du nombre de descripteurs, nombre de noeuds.
	    int nb_descripteurs = dis.readInt () ;
	    int nb_nodes = dis.readInt () ;

	    // Nombre de successeurs enregistrés dans le fichier.
	    int[] nsuccesseurs_a_lire = new int[nb_nodes] ;
	    
	    // En fonction de vos choix de conception, vous devrez certainement adapter la suite.
	    // this.longitudes = new float[nb_nodes] ;
	    // this.latitudes = new float[nb_nodes] ;
			this.sommets = new Sommet[nb_nodes];	    
			this.descripteurs = new Descripteur[nb_descripteurs] ;

	    // Lecture des noeuds
	    for (int num_node = 0 ; num_node < nb_nodes ; num_node++) {
				// Lecture du noeud numero num_node
				//sommets[num_node].getLongitude() = ((float)dis.readInt ()) / 1E6f ;
				//sommets[num_node].getLatitude() = ((float)dis.readInt ()) / 1E6f ;
				this.sommets[num_node] = new Sommet(num_node, ((float)dis.readInt ()) / 1E6f, ((float)dis.readInt ()) / 1E6f);
				nsuccesseurs_a_lire[num_node] = dis.readUnsignedByte() ;
	    }
	    
	    Utils.checkByte(255, dis) ;
	    
	    // Lecture des descripteurs
	    for (int num_descr = 0 ; num_descr < nb_descripteurs ; num_descr++) {
		// Lecture du descripteur numero num_descr
		descripteurs[num_descr] = new Descripteur(dis) ;

		//// On affiche quelques descripteurs parmi tous.
		//if (0 == num_descr % (1 + nb_descripteurs / 400))
		    //System.out.println("Descripteur " + num_descr + " = " + descripteurs[num_descr]) ;
	    }
	    
	    Utils.checkByte(254, dis) ;
	    
	    // Lecture des successeurs
	    for (int num_node = 0 ; num_node < nb_nodes ; num_node++) {
				// Lecture de tous les successeurs du noeud num_node
				for (int num_succ = 0 ; num_succ < nsuccesseurs_a_lire[num_node] ; num_succ++) {
						// zone du successeur
						int succ_zone = dis.readUnsignedByte() ;

						// numero de noeud du successeur
						int dest_node = Utils.read24bits(dis) ;

						// descripteur de l'arete
						int descr_num = Utils.read24bits(dis) ;

						// longueur de l'arete en metres
						int longueur  = dis.readUnsignedShort() ;

						// Nombre de segments constituant l'arete
						int nb_segm   = dis.readUnsignedShort() ;

						edges++ ;
						
						// Création de l'arc
						Arc arc1 = new Arc(succ_zone, dest_node, descr_num, longueur , nb_segm);
						Arc arc2 = new Arc(succ_zone, num_node, descr_num, longueur , nb_segm);
						sommets[num_node].addArc(arc1);

						// Verification du sens unique ou pas
						if ( !descripteurs[descr_num].isSensUnique() )
						{

							// Création de l'arc dans l'autre sens							
							sommets[dest_node].addArc(arc2);

						}


						Couleur.set(dessin, descripteurs[descr_num].getType()) ;

						float current_long = sommets[num_node].getLongitude() ;
						float current_lat  = sommets[num_node].getLatitude() ;

						// Chaque segment est dessine'
						for (int i = 0 ; i < nb_segm ; i++) {
							float delta_lon = (dis.readShort()) / 2.0E5f ;
							float delta_lat = (dis.readShort()) / 2.0E5f ;
							Segment seg = new Segment(delta_lat, delta_lon);
							arc1.addSegment(seg, i);
							// Verification du sens unique ou pas
							if ( !descripteurs[descr_num].isSensUnique() )
							{

								Segment seg2 = new Segment( (delta_lat * (-1)), (delta_lon * (-1)));
								arc2.addSegment(seg2, i);

							}

							dessin.drawLine(current_long, current_lat, (current_long + delta_lon), (current_lat + delta_lat)) ;
							current_long += delta_lon ;
							current_lat  += delta_lat ;
						}
						
						// Le dernier trait rejoint le sommet destination.
						// On le dessine si le noeud destination est dans la zone du graphe courant.
						if (succ_zone == numzone) {
							dessin.drawLine(current_long, current_lat, sommets[dest_node].getLongitude(), sommets[dest_node].getLatitude()) ;
						}
				}
	    }
	    
	    Utils.checkByte(253, dis) ;

	    System.out.println("Fichier lu : " + nb_nodes + " sommets, " + edges + " aretes, " 
			       + nb_descripteurs + " descripteurs.") ;
			for (int z = 0; z <142; z++){
				int nombre = sommets[z].getArcs().size();
				//System.out.println("Sommet numéro "+(z+1)+" a "+nombre+" successeurs.\n");
				
				if (nombre != 0){
					//System.out.println("Parmi eux, voici quelques descripteurs : \n");
					for(int y = 0; y<sommets[z].getArcs().size(); y++){
						int num_descripteur = sommets[z].getArcs().get(y).getDescripteur();
						//System.out.println("Descripteur " + y + " = " + descripteurs[num_descripteur]+"\n") ;
					}
				}
			}
	} catch (IOException e) {
	    e.printStackTrace() ;
	    System.exit(1) ;
	}

    }

    // Rayon de la terre en metres
    private static final double rayon_terre = 6378137.0 ;

    /** 
     *   Calcule la distance entre deux points.
     *  @param long1 longitude du premier point.
     *  @param lat1 latitude du premier point.
     *  @param long2 longitude du second point.
     *  @param lat2 latitude du second point.
     *  @return la distance entre les deux points en metres.
     */
    public static double distance(double long1, double lat1, double long2, double lat2) {
	double difflat = Math.toRadians((double)(lat2 - lat1)) ;
	double diffy   = difflat * rayon_terre ;
	
	double difflon = Math.toRadians((double)(long2 - long1)) ;
	double diffx   = difflon * rayon_terre * Math.cos(Math.toRadians((double)lat1)) ;

	double dist = Math.sqrt(diffx * diffx + diffy * diffy) ;
	return dist ;
    }


    /**
     *  Attend un clic sur la carte et affiche le numero de sommet le plus proche du clic.
     *  A n'utiliser que pour faire du debug ou des tests ponctuels.
     *  Ne pas utiliser automatiquement a chaque invocation des algorithmes.
     */
    public void situerClick() {

	System.out.println("Allez-y, cliquez donc.") ;
	
	if (dessin.waitClick()) {
	    float lon = dessin.getClickLon() ;
	    float lat = dessin.getClickLat() ;
	    
	    System.out.println("Clic aux coordonnees lon = " + lon + "  lat = " + lat) ;

	    // On cherche le noeud le plus proche. O(n)
	    float minDist = Float.MAX_VALUE ;
	    int   noeud   = 0 ;
	    
	    for (int num_node = 0 ; num_node < sommets.length ; num_node++) {
		float londiff = (sommets[num_node].getLongitude() - lon) ;
		float latdiff = (sommets[num_node].getLatitude() - lat) ;
		float dist = londiff*londiff + latdiff*latdiff ;
		if (dist < minDist) {
		    noeud = num_node ;
		    minDist = dist ;
		}
	    }

	    System.out.println("Noeud le plus proche : " + noeud) ;
	    System.out.println() ;
	    dessin.setColor(java.awt.Color.red) ;
	    dessin.drawPoint(sommets[noeud].getLongitude(), sommets[noeud].getLatitude(), 5) ;
	}
    }

    /**
     *  Charge un chemin depuis un fichier .path (voir le fichier FORMAT_PATH qui decrit le format)
     *  Verifie que le chemin est empruntable et calcule le temps de trajet.
     */
    public void verifierChemin(DataInputStream dis, String nom_chemin) {
		 
		Chemin chemin;
	try {
	    
	    // Verification du magic number et de la version du format du fichier .path
	    int magic = dis.readInt () ;
	    int version = dis.readInt () ;
	    Utils.checkVersion(magic, magic_number_path, version, version_path, nom_chemin, ".path") ;

	    // Lecture de l'identifiant de carte
	    int path_carte = dis.readInt () ;

	    if (path_carte != this.idcarte) {
		System.out.println("Le chemin du fichier " + nom_chemin + " n'appartient pas a la carte actuellement chargee." ) ;
		System.exit(1) ;
	    }

	    int nb_noeuds = dis.readInt () ;

	    // Origine du chemin
	    int first_zone = dis.readUnsignedByte() ;
	    int first_node = Utils.read24bits(dis) ;

	    // Destination du chemin
	    int last_zone  = dis.readUnsignedByte() ;
	    int last_node = Utils.read24bits(dis) ;

	    System.out.println("Chemin de " + first_zone + ":" + first_node + " vers " + last_zone + ":" + last_node) ;

	    int current_zone = 0 ;
	    int current_node = 0 ;

			// Construction du chemin
			chemin = new Chemin(current_zone, nb_noeuds, first_node, last_node);


	    // Tous les noeuds du chemin
	    for (int i = 0 ; i < nb_noeuds ; i++) {
				current_zone = dis.readUnsignedByte() ;
				current_node = Utils.read24bits(dis) ;
                //ajout du noeud au chemin
				chemin.addNoeud(current_node, i);
				System.out.println(" --> " + current_zone + ":" + current_node) ;
	    }

	    if ((current_zone != last_zone) || (current_node != last_node)) {
		    System.out.println("Le chemin " + nom_chemin + " ne termine pas sur le bon noeud.") ;
		    System.exit(1) ;
			}

			// Calcul du cout du chemin
			chemin.calculerCout(sommets, descripteurs);
			System.out.println("Le chemin " + nom_chemin + " a un coût égal à " + chemin.getCout() + " minutes.") ;
			
	} catch (IOException e) {
	    e.printStackTrace() ;
	    System.exit(1) ;
	}

    }

}
