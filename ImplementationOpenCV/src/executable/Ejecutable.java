package executable;

import gui.GUI;
import processing.core.*;
import processing.video.*;
import utilities.ColorTracking;

@SuppressWarnings("serial")
public class Ejecutable extends PApplet {

	// HelloOpenCV hOCV;
	// BlobThreshold blobThld;
	// FindContoursOpenCV contour;
	ColorTracking cTrack;
	// BrokenGlassEffect bknGlss;
	boolean newFrame = false;
	Movie mov;

	public void setup() {
		mov = new Movie(this, "../data/Movies/MovingTarget.mov");
		// blobThld = new BlobThreshold(this, mov);
		// hOCV = new HelloOpenCV(this);
		// contour = new FindContoursOpenCV(this, mov);
		cTrack = new ColorTracking(this, mov);
		// bknGlss = new BrokenGlassEffect(this, mov);
		// interfaz grafica
		GUI.getInstance().init(this);
		System.out.println(">>> Setup completed <<<");
	}

	public void draw() {
		background(255);
		// blobThld.show(newFrame);
		// hOCV.show();
		// contour.draw(newFrame);
		cTrack.draw(newFrame);
		// bknGlss.draw(newFrame);
		GUI.getInstance().update();
	}
	
	public void settings(){
		size(640, 600);
	}

	// In Eclipse, this method MUST BE PUBLIC
	public void movieEvent(Movie m) {
		m.read();
		newFrame = true;
	}

	public void captureEvent(Capture c) {
		c.read();
	}

	// *********** main method
	public static void main(String[] args) {
		String[] appletArgs = new String[] { "executable.Ejecutable" };
		if (args != null) {
			PApplet.main(concat(appletArgs, args));
		} else {
			PApplet.main(appletArgs);
		}
	}

}
