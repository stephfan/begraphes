package core;

import base.* ;

public class Arc 
{
	private int numZone;
	private int numSommet;
	private int descripteur;
	private int longueur;
	private Segment segments[];
	private int nbSegments;
	
	public Arc(int numZone, int numSommet, int descripteur, 
			int longueur, Segment[] segments, int nbSegments) 
	{
		this.numZone = numZone;
		this.numSommet = numSommet;
		this.descripteur = descripteur;
		this.longueur = longueur;
		this.segments = segments;
		this.nbSegments = nbSegments;
	}
	
	public Arc(int numZone, int numSommet, int descripteur, int longueur , int nbSegments) 
	{
		super();
		this.numZone = numZone;
		this.numSommet = numSommet;
		this.descripteur = descripteur;
		this.longueur = longueur;
		this.nbSegments = nbSegments;
		this.segments = new Segment[nbSegments];
	}

	public int getNumZone() 
	{
		return numZone;
	}

	public void setNumZone(byte numZone) 
	{
		this.numZone = numZone;
	}

	public int getNumSommet()
	{
		return numSommet;
	}

	public void setNumSommet(int numSommet)
	{
		this.numSommet = numSommet;
	}

	public int getDescripteur() 
	{
		return descripteur;
	}

	public void setDescripteur(int descripteur) 
	{
		this.descripteur = descripteur;
	}

	public int getLongueur()
	{
		return longueur;
	}

	public void setLongueur(short longueur) 
	{
		this.longueur = longueur;
	}

	public Segment[] getSegments() 
	{
		return segments;
	}

	public void setSegments(Segment[] segments) 
	{
		this.segments = segments;
	}

	public int getNbSegments() 
	{
		return nbSegments;
	}

	public void setNbSegments(int nbSegments)
	{
		this.nbSegments = nbSegments;
	}

	public void addSegment(Segment seg, int indice)
	{
		this.segments[indice] = seg;
	}
}
