package utilities;

import processing.core.*;
import processing.video.*;
import gab.opencv.*;

public class HelloOpenCV {
	PApplet app;
	Capture cam;
	OpenCV opencv;

	public HelloOpenCV(PApplet app) {
		this.app = app;
		String[] cameras = Capture.list();
		if (cameras.length == 0) {
			PApplet.println("There are no cameras available for capture.");
			app.exit();
		} else {
			PApplet.println("Available cameras:");
			for (int i = 0; i < cameras.length; i++) {
				PApplet.println(cameras[i]);
			}
			cam = new Capture(app, cameras[4]); // 4 es 640 x 360 fps = 15
			cam.start();
		}
		opencv = new OpenCV(app, app.width, app.height);
	//	opencv.startBackgroundSubtraction(2, 3, 0.5);
	}

	public void show() {
		// original
		if (cam.available()) {
			app.set(0, 0, cam);
//			opencv.loadImage(cam);
//			opencv.updateBackground();
//			opencv.dilate();
//			opencv.erode();
		}
		app.noFill();
		app.stroke(0, 255, 0);
		app.strokeWeight(2);
//		for (Contour contour : opencv.findContours()) {
//			contour.draw();
//		}
	}
}