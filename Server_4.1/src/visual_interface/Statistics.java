package visual_interface;

import processing.core.*;
import server.Client_communication_thread;
import server.ServerCom;

public class Statistics {

	private PApplet app;

	private int porcentaje;
	private PFont fuente;
	private int iD;
	private int maxEnergy = 20000;
	private int maxGoats = 100;
	private int gap = 5;

	private float barWidth, widthBarSet;

	public Statistics(MainInterface iPpal) {
		this.app = iPpal;
		fuente = app.loadFont("../data/fonts/Roboto-Regular-200.vlw");
		widthBarSet = (app.width - 200) / 2;
		barWidth = widthBarSet;

	} // setup

	public void draw() {
		drawFrames();
		// get bar width
		if (ServerCom.clients.size() != 0) {
			barWidth = (widthBarSet / ServerCom.clients.size()) - (100 / ServerCom.clients.size());
		}

		for (int i = 0; i < ServerCom.clients.size(); i++) {
			Client_communication_thread cct = ServerCom.clients.get(i);
			// make bars
			Bar tmpGoat = new Bar(app, -650, 0, maxGoats, cct.getClient().getId());
			Bar tmpEnergy = new Bar(app, -650, 0, maxEnergy, cct.getClient().getId());
			// set width
			tmpGoat.setWidth(barWidth);
			tmpEnergy.setWidth(barWidth);
			// showBars
			tmpGoat.showGoats(cct.getClient(), new PVector(100 + (gap * i) + (i * barWidth), 500));
			tmpEnergy.showEnergy(cct.getClient(), new PVector(650 + (gap * i) + (i * barWidth), 500));
		}
	} // draw

	private void drawFrames() {
		app.noStroke();
		app.fill(70, 100);
		app.rect(50, 510, widthBarSet, -390);
		app.rect(600, 510, widthBarSet, -390);
		app.fill(255);
		app.textFont(fuente);
		app.textAlign(PApplet.LEFT);
		app.textSize(18);
		app.text("Cantidad de cabras", 100, 530);
		app.text("Energía potrero", 650, 530);
	}

	public void mousePressed() {

	}// mousePressed

	public int getPorcentaje() {
		return porcentaje;
	}

	public void setPercentage(int porcentaje) {
		this.porcentaje = porcentaje;
	}

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}
}
