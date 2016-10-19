package utilities;

import blobDetection.*;
import processing.core.*;
import processing.video.*;

public class BlobThreshold {

	PApplet app;
	Movie mov;
	BlobDetection theBlobDetection; // http://www.v3ga.net/processing/BlobDetection/

	public BlobThreshold(PApplet app, Movie mov) {
		this.app = app;
		this.mov = mov;
		mov.play();
		PImage img = mov;
		theBlobDetection = new BlobDetection(img.width, img.height);
		theBlobDetection.setPosDiscrimination(true);
		// will detect bright areas whose luminosity > 0.12f;
		theBlobDetection.setThreshold(0.12f);

	}

	/**
	 * Este método se implementa en el draw del ejecutable. Recibe un booleano
	 * que es verdadero cuando el cuadro de la pelicula en el buffer es
	 * diferente al ultimo cuadro almacenado.
	 * 
	 * @param newFrame
	 */
	public void show(boolean newFrame) {
		if (newFrame) {
			newFrame = false;
			PImage img = mov;
			app.image(img, 0, 0, app.width, app.height);
		//	fastblur(img, 2);
			theBlobDetection.computeBlobs(img.pixels);
			drawBlobsAndEdges(true, true);
		}
		String mensaje = theBlobDetection.getBlobNb() + " blobs";
		app.text(mensaje, 20, 20);
	}

	/**
	 * Metodo para pintar los blobs
	 * 
	 * @param drawBlobs
	 * @param drawEdges
	 */
	void drawBlobsAndEdges(boolean drawBlobs, boolean drawEdges) {
		app.noFill();
		Blob b;
		EdgeVertex eA, eB;
		for (int n = 0; n < theBlobDetection.getBlobNb(); n++) {
			b = theBlobDetection.getBlob(n);
			if (b != null) {
				// Edges
				if (drawEdges) {
					app.strokeWeight(3);
					app.stroke(0, 255, 0);
					for (int m = 0; m < b.getEdgeNb(); m++) {
						eA = b.getEdgeVertexA(m);
						eB = b.getEdgeVertexB(m);
						if (eA != null && eB != null)
							app.line(eA.x * app.width, eA.y * app.height, eB.x
									* app.width, eB.y * app.height);
					}
				}

				// Blobs
				if (drawBlobs) {
					app.strokeWeight(1);
					app.stroke(255, 0, 0);
					app.rect(b.xMin * app.width, b.yMin * app.height, b.w
							* app.width, b.h * app.height);
				}

			}

		}
	}

	// ==================================================
	// Super Fast Blur v1.1
	// by Mario Klingemann
	// <http://incubator.quasimondo.com>
	// ==================================================
	void fastblur(PImage img, int radius) {
		if (radius < 1) {
			return;
		}
		int w = img.width;
		int h = img.height;
		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;
		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, p1, p2, yp, yi, yw;
		int vmin[] = new int[PApplet.max(w, h)];
		int vmax[] = new int[PApplet.max(w, h)];
		int[] pix = img.pixels;
		int dv[] = new int[256 * div];
		for (i = 0; i < 256 * div; i++) {
			dv[i] = (i / div);
		}

		yw = yi = 0;

		for (y = 0; y < h; y++) {
			rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + PApplet.min(wm, PApplet.max(i, 0))];
				rsum += (p & 0xff0000) >> 16;
				gsum += (p & 0x00ff00) >> 8;
				bsum += p & 0x0000ff;
			}
			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				if (y == 0) {
					vmin[x] = PApplet.min(x + radius + 1, wm);
					vmax[x] = PApplet.max(x - radius, 0);
				}
				p1 = pix[yw + vmin[x]];
				p2 = pix[yw + vmax[x]];

				rsum += ((p1 & 0xff0000) - (p2 & 0xff0000)) >> 16;
				gsum += ((p1 & 0x00ff00) - (p2 & 0x00ff00)) >> 8;
				bsum += (p1 & 0x0000ff) - (p2 & 0x0000ff);
				yi++;
			}
			yw += w;
		}

		for (x = 0; x < w; x++) {
			rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = PApplet.max(0, yp) + x;
				rsum += r[yi];
				gsum += g[yi];
				bsum += b[yi];
				yp += w;
			}
			yi = x;
			for (y = 0; y < h; y++) {
				pix[yi] = 0xff000000 | (dv[rsum] << 16) | (dv[gsum] << 8)
						| dv[bsum];
				if (x == 0) {
					vmin[y] = PApplet.min(y + radius + 1, hm) * w;
					vmax[y] = PApplet.max(y - radius, 0) * w;
				}
				p1 = x + vmin[y];
				p2 = x + vmax[y];

				rsum += r[p1] - r[p2];
				gsum += g[p1] - g[p2];
				bsum += b[p1] - b[p2];

				yi += w;
			}
		}
	}
}
