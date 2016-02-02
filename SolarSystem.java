package module8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**When the main method is run, opens a JFrame containing a model of the solar system
 * @author James
 */
@SuppressWarnings("serial")
public class SolarSystem extends JFrame{

	SolarPanel sol;
	JSlider zoomSlider, discSlider, speedSlider;
	JLabel zoomLabel, discLabel, speedLabel;
	JPanel zoomPanel, discPanel, speedPanel;
	JTextField zoomField, discField, speedField;

	/**Sets up JSlider to control relative size of the elliptical orbits (i.e. zoom in and out)	 */
	public void zoom(){
		//labels and text fields:
		zoomLabel = new JLabel("Zoom Level (%): ");
		zoomField = new JTextField(4);

		//slider properties:
		zoomSlider = new JSlider(JSlider.HORIZONTAL,100,10000,100);//direction , min , max , current
		zoomSlider.setFont(new Font("Tahoma",Font.PLAIN,11));
		zoomSlider.setMajorTickSpacing(9900);
		zoomSlider.setMinorTickSpacing(2000);
		zoomSlider.setPaintLabels(true);
		zoomSlider.setPaintTrack(true);
		zoomSlider.setAutoscrolls(true);
		zoomSlider.setPreferredSize(new Dimension(300,30));

		//adjust value when slider is moved:
		zoomSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				zoomField.setText(String.valueOf(zoomSlider.getValue()));
				sol.setOSF(zoomSlider.getValue());
			}
		});

		//adjust slider when new value is input:
		zoomField.addActionListener(new ActionListener() {    
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					zoomSlider.setValue(Integer.parseInt(zoomField.getText()));
					sol.setOSF(zoomSlider.getValue());
				}
				catch(Exception ex)
				{
					zoomField.setText("ERROR");
					zoomField.setToolTipText("Set Value in Range between 100 - 1100 ") ;
				}
			}
		});
		zoomPanel = new JPanel();
		zoomPanel.add(zoomLabel);
		zoomPanel.add(zoomField);
		zoomPanel.add(zoomSlider);
	}

	/**Sets up JSlider to control the refresh delay (i.e. speed of playback) */
	public void speed(){
		//labels and text fields:
		speedLabel = new JLabel("Refresh delay (ms): ");
		speedField = new JTextField(4);

		//slider properties:
		speedSlider = new JSlider(JSlider.HORIZONTAL,1,1000,10);//direction , min , max , current
		speedSlider.setFont(new Font("Tahoma",Font.PLAIN,11));
		speedSlider.setMajorTickSpacing(999);
		speedSlider.setMinorTickSpacing(100);
		speedSlider.setPaintLabels(true);
		speedSlider.setPaintTrack(true);
		speedSlider.setAutoscrolls(true);
		speedSlider.setPreferredSize(new Dimension(300,30));

		//adjust value when slider is moved:
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				speedField.setText(String.valueOf(speedSlider.getValue()));
				sol.setDelay(speedSlider.getValue());
			}
		});

		//adjust slider when new value is input:
		speedField.addActionListener(new ActionListener() {    
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					speedSlider.setValue(Integer.parseInt(speedField.getText()));
					sol.setDelay(speedSlider.getValue());
				}
				catch(Exception ex)
				{
					speedField.setText("ERROR");
					speedField.setToolTipText("Set Value in Range between 100 - 1100 ") ;
				}
			}
		});
		speedPanel = new JPanel();
		speedPanel.add(speedLabel);
		speedPanel.add(speedField);
		speedPanel.add(speedSlider);
	}
	
	/**Sets up JSlider to control relative size of the bodies' discs */
	public void disc(){
		//labels and text fields:
		discLabel = new JLabel("Disc size (%): ");
		discField = new JTextField(4);

		//slider properties:
		discSlider = new JSlider(JSlider.HORIZONTAL,100,500000,100);//direction , min , max , current
		discSlider.setFont(new Font("Tahoma",Font.PLAIN,11));
		discSlider.setMajorTickSpacing(499900);
		discSlider.setMinorTickSpacing(50000);
		discSlider.setPaintLabels(true);
		discSlider.setPaintTrack(true);
		discSlider.setAutoscrolls(true);
		discSlider.setPreferredSize(new Dimension(773,30));

		//adjust value when slider is moved:
		discSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				discField.setText(String.valueOf(discSlider.getValue()));
				sol.setDSF(discSlider.getValue());
			}
		});

		//adjust slider when new value is input:
		discField.addActionListener(new ActionListener() {    
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					discSlider.setValue(Integer.parseInt(discField.getText()));
					sol.setDSF(discSlider.getValue());
				}
				catch(Exception ex)
				{
					discField.setText("ERROR");
					discField.setToolTipText("Set Value in Range between 100 - 1100 ") ;
				}
			}
		});
		discPanel = new JPanel();
		discPanel.add(discLabel);
		discPanel.add(discField);
		discPanel.add(discSlider);
	}
	
	/** Creates a JFrame containing a SolarPanel, a JSlider to control zoom and another to control disc size*/
	private SolarSystem(){

		//create the frame in which all components sit:
		super("Solar System Simulation by James Cowley");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//create containers for the components:
		JPanel allContainer = new JPanel();
		allContainer.setLayout(new BoxLayout(allContainer, BoxLayout.Y_AXIS));
		JPanel topTwoSliderContainer = new JPanel();
		topTwoSliderContainer.setLayout(new BoxLayout(topTwoSliderContainer, BoxLayout.X_AXIS));
		JPanel bottomOneSliderContainer = new JPanel();
		allContainer.setLayout(new BoxLayout(allContainer, BoxLayout.Y_AXIS));

		//create panel for the solar system:
		sol = new SolarPanel(2000,2000);
		sol.setBackground(Color.black);
		allContainer.add(sol); // Add panel to frame

		//set up all JSliders:
		zoom();
		disc();
		speed();

		//pack JSliders into their containers:
		topTwoSliderContainer.add(zoomPanel);
		topTwoSliderContainer.add(speedPanel);
		bottomOneSliderContainer.add(discPanel);
		allContainer.add(topTwoSliderContainer);
		allContainer.add(bottomOneSliderContainer);
		
		//add the containers to the frame and show it:
		add(allContainer);
		pack();
		setLocation(100, 5);
		setSize(1000,700);
		setVisible(true);
	}

	/**Main method, creates a new SolarSystem */
	public static void main(String[] args){
		new SolarSystem();
	}
}
