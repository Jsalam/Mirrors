import java.util.ArrayList;

import processing.core.*;

public class Mirror {

	PApplet app;
	PVector pos;
	float movimiento = 1;
	boolean mover;
	float angle;

	public Mirror(PApplet app) {
		this.app = app;
		pos = new PVector();
	}

	public void show(ArrayList<Visitor> visitors, boolean visitorInInteraction) {

		// If any visitor is beyond the x coordinate of the mirror
			
		if (visitorInInteraction) {
			// movimiento = 0;
			// calculate the angle to the first visitor
			float angleFirst = calcAngle(visitors.get(0));
			// calculate angle to the last visitor
			float angleLast = calcAngle(visitors.get(visitors.size() - 1));
			// calculate average angle
			float averageAngle = (angleFirst + angleLast) / 2;
			// Here we make a transformation, but it should be done with
			// trigonometry operations
			app.pushMatrix();
			app.translate(pos.x, pos.y);
			app.rotate(averageAngle);
			app.fill(255, 0, 0);
			app.noFill();
			app.stroke(100);
			app.line(0, -15, 0, 15);
			app.line(-5, 2, -9, 2);
			app.popMatrix();
		} else {
			movimientoOla();
		}

	}

	private void movimientoOla() {
		if (movimiento <= 0) {
			mover = true;
		} else if (movimiento >= 3) {
			mover = false;
		}
		if (mover == true) {
			movimiento += 0.03;
		} else if (mover == false) {
			movimiento -= 0.03;
		}
		float averageAngle = movimiento;
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(averageAngle);
		app.fill(255, 0, 0);
		app.noFill();
		app.stroke(100);
		app.line(0, -15, 0, 15);
		app.line(-5, 2, -9, 2);
		app.popMatrix();
	}

	public void rotate() {

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

	public float calcAngle(Visitor visit) {
		return PApplet.atan2(pos.y - visit.pos.y, pos.x - visit.pos.x);
	}

}
