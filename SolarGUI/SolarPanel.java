package SolarGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

/**JPanel containing ellipses and moving discs
 * @author James
 */
@SuppressWarnings("serial")
public class SolarPanel extends JPanel implements ActionListener {

	//some variables are created great, others have greatness thrust upon them.
	int howWide, howHigh, zoomLevel = 100, relativeDiscScale = 100, dayNumber = 0, delay = 10;
	double orbitScaleFactor, discScaleFactor;
	ArrayList<ArrayList<double[]>> allPositions = new ArrayList<ArrayList<double[]>>();
	ArrayList<OrbitalBody> bods = bodies();
	Timer animationTimer;
	
	/**Sets value of delay */
	public void setDelay(int value){
		delay = value;
		animationTimer.setDelay(delay);
	}
	
	/**Sets value of zoom level.
	 * @param value percentage factor by which to scale the size of the orbits, to apparently zoom in and out of the solar system.
	 */
	public void setOSF(int value){
		zoomLevel = value;
	}

	/**Sets value of disc size.
	 * @param value percentage scale of disc size relative to orbital dimensions (e.g. 100% is to scale, 10000% makes discs 100x larger)
	 */
	public void setDSF(int value){
		relativeDiscScale = value;
	}

	/**Sets value of width and height
	 * @param width width of panel
	 * @param height height of panel
	 */
	public SolarPanel(int width, int height){
		setPreferredSize(new Dimension(width,height));
		howWide = width;
		howHigh = height;

		animationTimer = new Timer(delay,this);
		animationTimer.start();
		
		//populate allPositions:
		for(int i = 0; i<bods.size(); i++){
			OrbitalBody thisBody = bods.get(i);
			ArrayList<double[]> thesePositions = thisBody.positions();
			allPositions.add(thesePositions);
		}
	}

	/**Generates an array of Color objects from the ROYGBIV spectrum
	 * @param n number of Color objects requested
	 * @return array of Color objects
	 */
	public Color[] generateColors(int n)
	{
		Color[] cols = new Color[n];
		for(int i = 0; i < n; i++){
			cols[i] = Color.getHSBColor(-(float) i / (float) n, 0.85f, 1.0f);
		}
		return cols;
	}

	/**Generates a list of orbital bodies from data found at http://nssdc.gsfc.nasa.gov/planetary/factsheet/ and http://en.wikipedia.org/wiki/List_of_notable_asteroids#Largest_by_mass
	 * @return ArrayList of OrbitalBody objects for all bodies in the simulation
	 */
	public static ArrayList<OrbitalBody> bodies(){
		ArrayList<OrbitalBody> result = new ArrayList<OrbitalBody>();
		result.add(new OrbitalBody("Sun",1391000,0,0,0,10000));
		//planets:
		result.add(new OrbitalBody("Mercury",4879,46,69.8,0.205,88));
		result.add(new OrbitalBody("Venus",12104,107.5,108.9,0.007,224.7));
		result.add(new OrbitalBody("Earth",12756,147.1,152.1,0.017,365.2));
		result.add(new OrbitalBody("Mars",6792,206.6,249.2,0.094,687));
		result.add(new OrbitalBody("Jupiter",142984,740.5,816.6,0.049,4331));
		result.add(new OrbitalBody("Saturn",120536,1352.6,1514.5,0.057,10747));
		result.add(new OrbitalBody("Uranus",51118,2741.3,3003.6,0.046,30589));
		result.add(new OrbitalBody("Neptune",49528,4444.5,4545.7,0.011,59800));
		//other bodies:
		result.add(new OrbitalBody("Pluto (dwarf planet)",2390,4435,7304.3,0.244,90588));
		result.add(new OrbitalBody("Ceres (dwarf planet)", 508.4, 382.5,445.28,0.075797,1681));
		result.add(new OrbitalBody("Eris (dwarf planet)", 2326, 5723,14602,0.437,204624));
		result.add(new OrbitalBody("Hygieia (asteroid)", 530, 414.38, 524.63, 0.117, 2031));
		result.add(new OrbitalBody("Vesta (asteroid)", 572, 321.82,353.268,0.08862, 1326));
		result.add(new OrbitalBody("Pallas (asteroid)", 582, 318.9, 510.4, 0.231, 1686));
		result.add(new OrbitalBody("Eros (asteroid)", 33,135,266,0.223,625));
		result.add(new OrbitalBody("Apophis (asteroid)", 325, 111.6, 164.3, 0.191, 323.5));
		result.add(new OrbitalBody("Halley's Comet", 11, 87.66, 5250.88, 0.967, 27503));
		result.add(new OrbitalBody("Comet Hale-Bop", 1, 136.7, 55470, 0.995, 91980));
		result.add(new OrbitalBody("Comet Encke",1,49.3,614.8,0.8471,1073));
		return result;
	}

	/**Draws the ellipse representing an orbital body's orbital path.
	 * @param g Graphics object being used to draw the ellipse
	 * @param b Orbital body being considered
	 */
	public void drawOrbit(Graphics g, OrbitalBody b, double scalingFactor){
		int x = (int) (-1*b.xDisplacement*scalingFactor);
		int y = (int) (-1*b.yDisplacement*scalingFactor);
		int orbitWidth = (int) (b.ovalWidth*scalingFactor);
		int orbitHeight = (int) (b.ovalHeight*scalingFactor);
		g.drawOval(x, y, orbitWidth, orbitHeight);
	}

	/**Draws the disc representing an orbital body's size & position
	 * @param g Graphics object being used to draw the body
	 * @param b OrbitalBody supplying data for drawing
	 * @param xCoord x-coordinate of body's position
	 * @param yCoord y-coordinate of body's position
	 * @param col requested colour for resulting disc
	 */
	public void drawDisc(Graphics g, OrbitalBody b, double xCoord, double yCoord, Color col, double discScale, double posScale){
		int xCoordScaled = (int)((xCoord*posScale-b.d/2*discScale));
		int yCoordScaled = (int)((yCoord*posScale-b.d/2*discScale));
		int diamInt = (int)(b.d*discScale);
		g.setColor(col);
		g.fillOval(xCoordScaled, yCoordScaled, diamInt, diamInt);
		g.drawString(b.name, xCoordScaled+diamInt, yCoordScaled+diamInt); //writes the body's name next to it
	}

	/**Called by the animation Timer object after every delay and updates the model. */
	public void actionPerformed(ActionEvent e) {
		dayNumber++;
		repaint();
	}

	/**Must override paintComponent to draw orbital paths and discs
	 * @param g Graphics object being used to draw
	 */
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		int width = getWidth();

		//this is to scale distances on the order of 10^6 km down to a reasonable number of pixels for coordinates
		orbitScaleFactor = (zoomLevel/100)*width/15000000000.0;
		discScaleFactor = orbitScaleFactor*relativeDiscScale/100;

		int height = getHeight();
		g.translate(width/2, height/2);	//move origin to centre of panel
		Color[] cols = generateColors(bods.size());

		//drawing the sun:
		OrbitalBody sun = bods.get(0);
		drawDisc(g,sun,0,0,Color.YELLOW, discScaleFactor, orbitScaleFactor);

		//drawing every other body from "bods"
		for(int i = 1; i<bods.size(); i++){
			int thisDay = dayNumber;
			OrbitalBody thisBody = bods.get(i);
			ArrayList<double[]> thesePositions = allPositions.get(i);
			int thisDayLimit = thesePositions.size();

			//once a body has orbited once, the current day must be reset to the beginning of the period again
			while(thisDay >= thisDayLimit){
				thisDay = thisDay - thisDayLimit;
			}

			//drawing the orbital path and disc for the current body (all non-planets are drawn in red):
			if(i < 9){
				g.setColor(cols[i]);
			}
			else{
				g.setColor(Color.RED);
			}
			drawOrbit(g, thisBody, orbitScaleFactor);
			double thisBodyX = thesePositions.get(thisDay)[0]-thisBody.f;
			double thisBodyY = thesePositions.get(thisDay)[1];
			drawDisc(g,thisBody,thisBodyX,thisBodyY,g.getColor(), discScaleFactor, orbitScaleFactor);
		}

		//drawing some titles
		g.setColor(Color.white);
		g.setFont(new Font("Tahoma",Font.PLAIN,22));
		g.drawString("Solar system simulation by James Cowley",5-width/2, 30-height/2);
		g.setFont(new Font("Tahoma",Font.BOLD,12));
		g.drawString("When disc size is set to 100%, model is to scale.",5-width/2, height/2-30);
		g.drawString("Exaggerate relative disc size & zoom in (by using the sliders or by typing values) to make bodies more easily visible.",5-width/2, height/2-10);
		g.drawString("Days passed: "+dayNumber,width/2-140, height/2-30);

		double years = dayNumber/365.25;
		BigDecimal bd = new BigDecimal(years);
		bd = bd.round(new MathContext(4));
		double rounded = bd.doubleValue();
		g.drawString("Years passed: "+rounded,width/2-140, height/2-10);

	}
}
