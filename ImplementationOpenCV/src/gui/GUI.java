package gui;

import processing.core.*;

import java.awt.Color;
import java.util.ArrayList;

import controlP5.*;

public class GUI {

	PApplet app;
	ControlP5 cp5;
	int myColor;
	public float hueMin;
	public float saturationMin;
	public float brightnessMin;
	public float hueMax;
	public float saturationMax;
	public float brightnessMax;
	public float boxMinSize;

	private static GUI guiInstance = null;

	/**
	 * Singleton pattern
	 */
	public static GUI getInstance() {
		if (guiInstance == null) {
			guiInstance = new GUI();
		}
		return guiInstance;
	}

	protected GUI() {
	}

	public void init(PApplet app) {
		this.app = app;

		myColor = app.color(0, 0, 0);
		hueMin = 0;
		saturationMin = 0;
		brightnessMin = 0;
		hueMax = 0;
		saturationMax = 1;
		brightnessMax = 1;
		boxMinSize = 100;

		cp5 = new ControlP5(app);

		// add a horizontal sliders, the value of this slider will be linked
		// to variable 'sliderValue'
		cp5.addSlider("hueMin").setPosition(100, app.height - 55).setRange(0f, 1f).plugTo(app);
		cp5.addSlider("saturationMin").setPosition(100, app.height - 35).setRange(0f, 1f).plugTo(app);
		cp5.addSlider("brightnessMin").setPosition(100, app.height - 15).setRange(0f, 1f).plugTo(app);
		cp5.addSlider("hueMax").setPosition(270, app.height - 55).setRange(0f, 1f).setValue(1f).plugTo(app);
		cp5.addSlider("saturationMax").setPosition(270, app.height - 35).setRange(0f, 1f).setValue(1f).plugTo(app);
		cp5.addSlider("brightnessMax").setPosition(270, app.height - 15).setRange(0f, 1f).setValue(1f).plugTo(app);
		cp5.addSlider("boxMinSize").setPosition(440, app.height - 55).setRange(0f, 5000f).setValue(200f).plugTo(app);
	}

	public void update() {
		hueMin = cp5.getController("hueMin").getValue();
		hueMax = cp5.getController("hueMax").getValue();
		saturationMin = cp5.getController("saturationMin").getValue();
		saturationMax = cp5.getController("saturationMax").getValue();
		brightnessMin = cp5.getController("brightnessMin").getValue();
		brightnessMax = cp5.getController("brightnessMax").getValue();
		boxMinSize = cp5.getController("boxMinSize").getValue();

	}

	public void render() {
		app.noStroke();
		app.fill(152, 152, 152);
		app.rect(0, app.height - 110, app.width, app.height);
		Color minColor = Color.getHSBColor(hueMin, saturationMin, brightnessMin);
		Color maxColor = Color.getHSBColor(hueMax, saturationMax, brightnessMax);
		ArrayList<Color> interpolated = interpolateColors(minColor, maxColor, 20);
		for (int i = 0; i < interpolated.size(); i++) {
			app.fill(interpolated.get(i).getRGB());
			app.rect(150 + (i * 10), app.height - 100, 10, 30);
		}
	}

	public ArrayList<Color> interpolateColors(Color a, Color b, int cant) {
		ArrayList<Color> colors = new ArrayList<Color>();
		float[] hsbPxlA = Color.RGBtoHSB(a.getRed(), a.getGreen(), a.getBlue(), null);
		float[] hsbPxlB = Color.RGBtoHSB(b.getRed(), b.getGreen(), b.getBlue(), null);
		for (int i = 0; i <= cant; i++) {
			float hue = PApplet.lerp(hsbPxlA[0], hsbPxlB[0], i / (float) cant);
			float sat = PApplet.lerp(hsbPxlA[1], hsbPxlB[1], i / (float) cant);
			float brg = PApplet.lerp(hsbPxlA[2], hsbPxlB[2], i / (float) cant);
			colors.add(Color.getHSBColor(hue, sat, brg));
		}
		return colors;
	}
}
