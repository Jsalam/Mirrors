package utilities;

import gab.opencv.Contour;
import gab.opencv.OpenCV;
import gui.GUI;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import processing.core.*;
import processing.video.Movie;

public class ColorTracking {
	PApplet app;
	OpenCV opencv;
	Movie mov;
	ArrayList<Contour> contours;

	public ColorTracking(PApplet app, Movie mov) {
		this.app = app;
		// La pelicula
		this.mov = mov;
		mov.play();
		mov.loop();
		opencv = new OpenCV(app, mov.width, mov.height);
		// OpenCV
		opencv.gray();
	}

	public void draw(boolean newFrame) {
		if (newFrame) {
			newFrame = false;
			// Toma la imagen del video
			PImage imgA = mov;
			// app.image(imgA, 0, 0);
			// Crea una imagen filtrada con el umbral de color
			PImage imgB = filtrarUmbral(imgA);
			// app.image(imgB, 300, 0);
			// le pasa la imagen a openCV para que cree los contornos
			try{
				opencv.loadImage(imgB);
			// contruye los contornos
			contours = opencv.findContours(true, true);
			// Visualizacion de resultados
			visualizaResultados(imgA, imgB);
			} catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			// Interfaz grafica (sliders)
			GUI.getInstance().render();
		}
	}

	private PImage filtrarUmbral(PImage fuente) {
		PImage tmp = new PImage(mov.width, mov.height);

		// fills imageB of white pixels
		tmp.loadPixels();
		for (int i = 0; i < tmp.pixels.length; i++) {
			tmp.pixels[i] = Color.BLACK.getRGB();
		}
		// Convert fuente to HSB
		fuente.loadPixels();
		// get pixels beyond the threshold
		for (int i = 0; i < fuente.pixels.length; i++) {
			// get each pixel color
			Color pxl = new Color(fuente.pixels[i]);
			// convert color to HSB
			float[] hsbPxl = Color.RGBtoHSB(pxl.getRed(), pxl.getGreen(), pxl.getBlue(), null);
			// si esta dentro de los limites
			// System.out.println(hsbPxl[2]);
			if (hsbPxl[0] > GUI.getInstance().hueMin && hsbPxl[0] < GUI.getInstance().hueMax) {
				if (hsbPxl[1] > GUI.getInstance().saturationMin && hsbPxl[1] < GUI.getInstance().saturationMax) {
					if (hsbPxl[2] > GUI.getInstance().brightnessMin && hsbPxl[2] < GUI.getInstance().brightnessMax) {
						// fills image with black pixel
						tmp.pixels[i] = Color.WHITE.getRGB();
					}
				}
			}
		}
		// erode and deplet
		fuente.updatePixels();
		tmp.updatePixels();
		// avoid gaps
		tmp.filter(PApplet.ERODE);
		// avoid gaps
		tmp.filter(PApplet.DILATE);
		// Create contours
		return tmp;
	}

	public void visualizaResultados(PImage imgA, PImage imgB) {
		app.scale(0.5f);
		app.image(imgA, imgA.width, imgA.height);
		app.image(imgB, imgA.width, 0);
		app.noFill();
		Rectangle rec = null;
		for (Contour contour : contours) {

			// discard the contours smaller than parameter
			if (contour.area() > GUI.getInstance().boxMinSize) {
				app.stroke(0, 255, 0);
				app.strokeWeight(2);
				contour = contour.getConvexHull();
				contour.draw();

				// boundingBox
				app.stroke(255, 0, 0);
				rec = contour.getBoundingBox();
				app.rect((float) rec.getX(), (float) rec.getY(), (float) rec.getWidth(), (float) rec.getHeight());
				// cross
				app.line((float) (rec.getX() + rec.getWidth() / 2), 0, (float) (rec.getX() + rec.getWidth() / 2),
						imgA.height);
				app.line(0, (float) (rec.getY() + rec.getHeight() / 2), imgA.width,
						(float) (rec.getY() + rec.getHeight() / 2));

				// left or right presence
				if ((rec.getX() + rec.getWidth() / 2) < imgA.width / 2) {
					app.fill(255, 0, 0, 100);
					app.rect(0, imgA.height, imgA.width / 2, imgA.height);
				}
				if ((rec.getX() + rec.getWidth() / 2) > imgA.width / 2) {
					app.fill(0, 255, 0, 100);
					app.rect(imgA.width / 2, imgA.height, imgA.width / 2, imgA.height);
				}

				// detect dot in contour
				PVector point = new PVector (80,30);
				app.ellipse((imgA.width / 2) + point.x, (imgA.height / 2) - point.y, 5, 5);
				if (containsPoint(contour,imgA,point)) {
					app.ellipse((imgA.width / 2) + 80, (imgA.height / 2) - 30, 30, 30);
				}
			}
		}
		app.scale(2f);
	}

	public boolean containsPoint(Contour contour, PImage imgA, PVector point) {
		boolean rtn = false;
		if (contour.containsPoint((imgA.width / 2) + (int) point.x, (imgA.height / 2) - (int) point.y)) {
			rtn = true;
		}
		return rtn;
	}
}
