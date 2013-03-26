package core;

import base.* ;

public class Segment 
{
	private float deltaLatitude;
	private float deltaLongitude;
	
	public Segment(float dlat, float dlong)
	{
		this.deltaLatitude = dlat;
		this.deltaLongitude = dlong;
	}

	public float getDeltaLatitude() {
		return deltaLatitude;
	}

	public void setDeltaLatitude(float deltaLatitude) {
		this.deltaLatitude = deltaLatitude;
	}

	public float getDeltaLongitude() {
		return deltaLongitude;
	}

	public void setDeltaLongitude(float deltaLongitude) {
		this.deltaLongitude = deltaLongitude;
	}
}
