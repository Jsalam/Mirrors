package filters;

import java.awt.Color;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Movie;
import gab.opencv.Contour;
import gui.GUI;

public class BrokenGlassEffect {
	PApplet app;
	Movie mov;
	ArrayList<Contour> contours;
	PImage glassImg;

	public BrokenGlassEffect(PApplet app, Movie mov) {
		this.app = app;
		// La pelicula
		this.mov = mov;
		mov.play();
		mov.loop();

		// La Imgen
		glassImg = app.loadImage("../data/images/brokenGlassBW.png");

	}

	float cont = 0.0f;
	boolean going = true;

	void draw(boolean newFrame) {
		if (newFrame) {
			newFrame = false;
			// controla el efecto de rebote del progreso del vidrio roto
			if (going && cont < app.width) {
				cont += 1;
			} else {
				going = false;
				cont -= 1;
				if (cont < 0) {
					going = true;
				}
			}
			// Toma la imagen del video
			PImage imgA = mov.get();
			// Toam el cvalor minmo de brillo
			float minBright = GUI.getInstance().brightnessMin;
			// Crea una imagen filtrada con el umbral de color
			PImage imgB = filtrarUmbralGrises(imgA, glassImg, minBright, cont);
			// Visualizacion de resultados
			app.image(imgB, 0, 0);
			// Interfaz grafica (sliders)
			visualizaGUI();
		}
	}

	/**
	 * Filtra la imagen fuente poniendole una mascara obtenida de la imagen
	 * overlap
	 * 
	 * @param fuente
	 *            la imagen sobre la cual se va a poner la mascara
	 * @param mascara
	 *            la imagen que se va a sobreponer
	 * @param valorGris
	 *            el valor de gris que filtrara los pixels
	 * @param ancho
	 *            el ancho de la imagen que queremos sobreponer
	 * @return la imagen fuente enmascarada
	 */
	private PImage filtrarUmbralGrises(PImage fuente, PImage mascara,
			float valorGris, float ancho) {

		// obtiene las matrices de las dos imagenes
		mascara.loadPixels();
		fuente.loadPixels();

		for (int i = 0; i < mascara.pixels.length; i += mascara.width) {
			for (int j = 0; j < ancho; j++) {
				// get each pixel color
				Color pxl = new Color(mascara.pixels[i + j]);
				// get each pixel color
				float[] hsbPxl = Color.RGBtoHSB(pxl.getRed(), pxl.getGreen(),
						pxl.getBlue(), null);
				// si hsbPxl esta dentro de los limites
				if (hsbPxl[2] > valorGris) {
					// pone el pixel en modo RGB en la imagen fuente
					fuente.pixels[i + j] = pxl.getRGB();
				}
			}
		}
		fuente.updatePixels();
		mascara.updatePixels();
		return fuente;
	}


	/**
	 * Muestra la interfaz de control
	 */
	public void visualizaGUI() {
		app.noStroke();
		app.fill(152, 152, 152);
		app.rect(0, app.height - 110, app.width, app.height);
		int gris = (int) PApplet.map(GUI.getInstance().brightnessMin, 0,1,0,255);
			app.fill(gris);
			app.rect(150, app.height - 90, 204, 20);
			app.fill(255,0,0);
			app.text("Cambie el valor de brightnessMin", 155, app.height - 77);
	}

	
}
