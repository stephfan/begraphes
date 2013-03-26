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
		this.arcs = arcs;
		arcs = new ArrayList<Arc>();
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
	
	
}
