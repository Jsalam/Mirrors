
import processing.core.PApplet;
import processing.core.PVector;

public class Visitor {

	PApplet app;
	PVector pos;
	float angle;
	int stride = 1;
	boolean last;

	public Visitor(PApplet app) {
		this.app = app;
		last = false;
	}

	public void show() {
		walk();
		if (last) {
			app.fill(155, 125, 155);
		} else {
			app.fill(255, 0, 255);
		}
		app.ellipse(pos.x, pos.y, 10, 10);
	}

	public PVector getPos() {
		return pos;
	}

	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public void setPos(float x, float y) {
		pos.x = x;
		pos.y = y;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void walk() {

		if (pos.x > app.width - 100) {
			stride = -1;
		} else if (pos.x < 100) {
			stride = 1;
		}
		pos.x += stride;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}

}
