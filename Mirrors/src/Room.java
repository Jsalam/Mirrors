import java.util.ArrayList;

import processing.core.*;

public class Room {
	PVector size;
	ArrayList<Visitor> visitors;
	ArrayList<Mirror> mirrors;
	PApplet app;

	public Room(PApplet app) {
		this.app = app;
		size = new PVector();
		size.x = app.width;
		size.y = app.height;
		visitors = new ArrayList<Visitor>();
		mirrors = new ArrayList<Mirror>();
	}

	public void populate(int totalMirrors, int totalPeople) {
		// Add visitors
		for (int i = 0; i < totalPeople; i++) {
			addVisitor();
		}
		// set the last one with a different color
		visitors.get(totalPeople - 1).setLast(true);
		// make the mirrors
		int gap = 0;
		for (int i = 0; i < totalMirrors; i++) {
			Mirror mirror = new Mirror(app);
			// calculate the room space
			float space = (app.width - 100) / (totalMirrors + 2);
			// set mirrors in the room
			mirror.setPos(100 + gap * space, app.height / 2);
			mirrors.add(mirror);
			gap++;
		}
	}

	public void show() {
		app.noStroke();
		app.fill(200,150);
		app.rect(mirrors.get(0).pos.x, 0, app.width, app.height);

		// calculate mirror operation and display mirrors
		for (Mirror mirror : mirrors) {
			app.stroke(0, 10);
			// Draws reflection lines
			for (Visitor v : visitors) {
				app.line(v.pos.x, v.pos.y, mirror.pos.x, mirror.pos.y);
			}
			mirror.show(visitors, visitorInInteraction());
		}

		// draw the visitors. The first one is controlled by the mouse
		visitors.get(0).setPos(app.mouseX, app.mouseY);
		for (Visitor v : visitors) {
			v.show();
		}
	}

	public PVector randomCoordinates() {
		float posX, posY;
		posX = (float) Math.random() * app.width;
		posY = (float) Math.random() * app.height;
		return new PVector(posX, posY);
	}

	public void addVisitor() {
		Visitor temp = new Visitor(app);
		temp.setPos(randomCoordinates());
		temp.setLast(false);
		visitors.add(temp);
	}

	private boolean visitorInInteraction() {
		boolean rtn = false;
		for (Visitor v : visitors) {
			for (Mirror m : mirrors) {
				if (!v.last && v.pos.x > m.pos.x) {
					rtn = true;
					break;
				}
			}
		}
		return rtn;
	}
}
