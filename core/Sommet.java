package core;

import base.* ;

import java.util.ArrayList;

public class Sommet 
{
	private int numSommet;
	private float longitude;
	private float latitude;
	private ArrayList<Arc> arcs;
	
	
	public Sommet(int numSommet, float longitude, float latitude, ArrayList<Arc> arcs) 
	{
		this.numSommet = numSommet;
		this.longitude = longitude;
		this.latitude = latitude;
		arcs = new ArrayList<Arc>();
        this.arcs = arcs;

	}
	
	public Sommet(int numSommet, float longitude, float latitude) 
	{
		this.numSommet = numSommet;
		this.longitude = longitude;
		this.latitude = latitude;
		arcs = new ArrayList<Arc>();
	}

	public int getNumSommet() 
	{
		return numSommet;
	}

	public void setNumSommet(int numSommet) 
	{
		this.numSommet = numSommet;
	}

	public float getLongitude() 
	{
		return longitude;
	}

	public void setLongitude(float longitude) 
	{
		this.longitude = longitude;
	}

	public float getLatitude() 
	{
		return latitude;
	}

	public void setLatitude(float latitude) 
	{
		this.latitude = latitude;
	}

	public ArrayList<Arc> getArcs() 
	{
		return arcs;
	}

	public void setArcs(ArrayList<Arc> arcs) 
	{
		this.arcs = arcs;
	}

	public void addArc(Arc arc)
	{
		this.arcs.add(arc);
	}
	
	// La fonction getArcSpecial prend en paramètre un numéro de sommet
	// et retourne l'arc correspondant au chemin vers ce sommet
	public Arc getArcSpecial(int numSommet)
	{
        System.out.println("Vous voulez un arc du sommet " + this.numSommet + " vers le sommet " + numSommet + " .");
        if (this.getNumSommet() == numSommet){
            System.out.println("Vous indiquez le numéro de sommet actuel. Evitons de reboucler pour rien s'il vous plaît.");
            System.exit(-1);
        }
        
		// Initialisation arc à retourner
		Arc arcSpecial = null;

		int i = 0;
		boolean test = false;

		// Parcours du tableau d'arc
		while ( i <	this.getArcs().size() && !test )
		{

			// Si le sommet cherché est trouvé
			if ( this.getArcs().get(i).getNumSommet() == numSommet )
			{

				// Sortie de la boucle
				test = true;

				// Mise à jour de l'arc à retourner
				arcSpecial = this.getArcs().get(i);

			}

			// Incrémentation de l'indice
			i++;

		}

		if (arcSpecial != null)
		{

			return arcSpecial;

		}
		else
		{
	
			System.out.println("Aucun arc trouvé. Le chemin n'est pas empruntable.");
			return null;

		}

	}
	
}
