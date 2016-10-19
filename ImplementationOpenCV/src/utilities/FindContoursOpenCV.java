package utilities;

import gab.opencv.*;
import processing.core.*;
import processing.video.*;

import java.util.*;

public class FindContoursOpenCV {

	PImage src, dst;
	OpenCV opencv;
	ArrayList<Contour> contours;
	PApplet app;
	Movie mov;

	public FindContoursOpenCV(PApplet app, Movie mov) {
		this.app = app;
		
		// La película
		this.mov = mov;
		mov.play();
		mov.loop();
		opencv = new OpenCV(app, mov.width, mov.height);
		
		// OpenCV
		opencv.gray();
		int val = (int)PApplet.map(0.2f,0f,1f,0f,255f);
		opencv.threshold(val);
	}

	void draw(boolean newFrame) {
		if (newFrame) {
			newFrame = false;
			opencv.loadImage(mov);
			dst = opencv.getOutput();
			contours = opencv.findContours();
	
			app.scale(0.5f);
			app.image(mov, 0, 0);
			app.image(dst, mov.width, 0);
			
			app.noFill();
			app.strokeWeight(2);
			for (Contour contour : contours) {
				app.stroke(0, 255, 0);
				contour.draw();
				app.stroke(255, 0, 0);
				app.beginShape();
				for (PVector point : contour.getPolygonApproximation().getPoints()) {
					app.vertex(point.x, point.y);
				}
				app.endShape();
			}
		}
		app.scale(2f);
		String mensage = "found " + contours.size() + " contours";
		app.text(mensage, 10,20);
	}
}
