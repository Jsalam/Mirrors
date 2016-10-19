import processing.core.PApplet;

public class Executable extends PApplet {
	Logic log;
	public void setup() {
		size(800, 300);
		log = new Logic(this);
	}

	public void draw() {
		background(255);
		log.show();
	}
}
