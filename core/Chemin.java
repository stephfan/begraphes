package core;

import base.* ;

public class Chemin 
{
	private int numZone;
	private int cout;
	private int noeuds[];
	private int nbNoeud;
	private int premier;
	private int dernier;

	public Chemin(int numZone, int noeuds, int premier, int dernier)
	{
		this.numZone = numZone;
		this.nbNoeud = noeuds;
		this.premier = premier;
		this.dernier = dernier;
		this.cout = 0;
		this.noeuds = new int[nbNoeud];
	}

	public int getNumZone() 
	{
		return numZone;
	}

	public void setNumZone(int numZone) 
	{
		this.numZone = numZone;
	}

	public int getCout()
	{
		return cout;
	}

	public void setCout(int cout)
	{
		this.cout = cout;
	}

	public int getNbNoeud() 
	{
		return nbNoeud;
	}

	public void setNbNoeud(int nbNoeud) 
	{
		this.nbNoeud = nbNoeud;
	}

	public int getPremier()
	{
		return premier;
	}

	public void setPremier(int premier) 
	{
		this.premier = premier;
	}

	public int[] getNoeuds() 
	{
		return noeuds;
	}

	public void setNoeuds(int[] noeuds) 
	{
		this.noeuds = noeuds;
	}

	public int getDernier() 
	{
		return dernier;
	}

	public void setDernier(int dernier)
	{
		this.dernier = dernier;
	}

	public void addNoeud(int noeud, int indice)
	{
		this.noeuds[indice] = noeud;
	}

	public void calculerCout(Sommet[] sommets, Descripteur[] descripteurs)
	{

		// Initialisation sommet courant
		Sommet s;

		//Inititalisation arc courant
		Arc arcCourant;

		// Initialisation du courant au premier noeud du chemin
		int courant;

		// Initialisation cout
		float somme = 0;

		//Initialisation longueur et vitesse
		int longueur, vitesse;

		// Pour chaque noeud du chemin, sauf le dernier
		for ( int i = 0 ; i < this.getNbNoeud()-1; i++ )
		{

			// Mise à jour courant
			courant = noeuds[i];
            //System.out.println("identifiant courant = " + courant);
            
			// Récupération du sommet correspondant a l'identifiant courant
			s = sommets[courant];

            
			// Recherche de l'arc correspondant au suivant
			arcCourant = s.getArcSpecial(noeuds[i+1]);
			if (arcCourant != null)
			{

				// Mise à jour du cout

					// longueur de l'arc
					longueur = arcCourant.getLongueur();
                    //longueur = longueur / 1000;//parce que la longueur est donnée en mètres
                    System.out.println("Longueur = "+ longueur);
					// vitesse sur l'arc
					vitesse = descripteurs[arcCourant.getDescripteur()].vitesseMax();
                    System.out.println("Vitesse = "+ vitesse);
					// calcul du cout sur cet arc et mise à jour somme
					somme = somme + ((longueur/(float)(vitesse*1000))*60);
                    System.out.println("Somme = "+ somme);
		
			}
			else
			{
				// Pas de chemin possible, on quitte le parcours et affecte -1 à cout.
				somme = -1;
				System.out.println("Le chemin n'est pas empruntable.");
				break;
			
			}
		}

        // Mise à jour cout
		this.setCout((int)somme);
	}

}
