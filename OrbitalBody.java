package module8;

import java.util.ArrayList;

/**Contains data about an object's orbit and properties
 * @author James
 */
public class OrbitalBody {
	String name;
	double d, e, dMax, dMin, period,a,b,f,ovalWidth,ovalHeight;
	double xDisplacement;
	double yDisplacement;
	double r0;
	double theta0;
	
	/**Constructor 
	 * @param bodyName name of body e.g. "Mars"
	 * @param bodyDiameter diameter of body's disc
	 * @param periheleon body's maximum distance from the sun
	 * @param aphelion body's minimum distance from the sun
	 * @param eccentricity eccentricity of body's orbital ellipse
	 * @param orbitalPeriod period of body's elliptical orbit in Earth days e.g. Mercury's year lasts 88 days so orbitalPeriod = 88
	 */
	public OrbitalBody(String bodyName, double bodyDiameter, double periheleon, double aphelion, double eccentricity, double orbitalPeriod){
		name = bodyName;
		d = bodyDiameter;
		e = eccentricity;
		dMax = periheleon*1000000; //data given in 10^6 km
		dMin = aphelion*1000000; //data given in 10^6 km
		period = orbitalPeriod;

		//setting up the parameters of the ellipse:
		a = (dMax + dMin)/2; //semi-major axis
		b = Math.sqrt(a*a*(1 - e*e)); //semi-minor axis
		f = a*e; //focus (i.e. distance of sun from centre of ellipse)
		r0 = a;
		theta0 = 0;

		//translating these into useful oval-drawing parameters:
		ovalWidth = (2*a);
		ovalHeight = 2*b;
		xDisplacement = (a+f); //x-distance from top left to focus
		yDisplacement = b; //y-distance from top left to focus
	}

	/**Finds position in polar coordinates given previous day's coordinates.
	 * @param rOld Distance from sun the day before
	 * @param thetaOld Angle from +ive x-axis the day before
	 * @return
	 */
	public double[] getCoords(double rOld, double thetaOld){
		double thetaNew = thetaOld + 2*Math.PI*a*b/(rOld*rOld*period);
		double rNew = a*(1-e*e)/(1+e*Math.cos(thetaNew)); //equation from end of section at http://en.wikipedia.org/wiki/Kepler%27s_laws_of_planetary_motion#Position_as_a_function_of_time
		double[] result = {rNew,thetaNew};
		return result;
	}

	/**Generates 1 position for every day of the body's orbital period
	 * @return ArrayList of coordinate pairs, store in 2-double arrays
	 */
	public ArrayList<double[]> positions(){
		ArrayList<double[]> result = new ArrayList<double[]>();
		double r = r0;
		double theta = theta0;
		for(int day = 1; day < period; day++){
			r = getCoords(r,theta)[0];
			theta = getCoords(r,theta)[1];
			double x = r*Math.cos(theta);
			double y = r*Math.sin(theta);
			double[] coords = {x+f,y}; //must shift x-coordinate from focus to origin at centre of ellipse by adding +f.
			result.add(coords);
		}
		return result;
	}
	
	/**toString method
	 * Primarily for code-testing purposes, returns some key parameters in String form
	 */
	public String toString(){
		return "\r\nFor "+name+", a = "+a+", b = "+b+", f = "+f;
	}
}
