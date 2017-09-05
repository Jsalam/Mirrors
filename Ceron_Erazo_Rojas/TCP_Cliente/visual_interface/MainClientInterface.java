package visual_interface;

import logic.Logic;
import processing.core.*;

public class MainClientInterface extends PApplet {

	private Logic logic;

	private int width, height;

	public static void main(String[] args) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new MainClientInterface());
	} // main

	public void settings() {
		width = 1080;
		height = 720;
		size(width, height);
	}// SETTINGS

	public void setup() {
		logic = new Logic(this);
	}// SETUP

	public void draw() {
		background(150);
		fill(255);
		textAlign(CENTER);
		textSize(60);
		text(logic.getUser().getNumberOfGoats(), width / 2, height / 2);
	}// DRAW

	public void mousePressed() {

	}// mouse pressed

	public void mouseClicked() {
		if (mouseButton == LEFT) {
			logic.buyGoat();
		} else if (mouseButton == RIGHT) {
			logic.sacrificeGoat();
		} // button pressed
	}// mouse clicked

	// --------------- GETTERS & SETTERS ---------------

}// INTERFAZ PRINCIPAL