import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Cabra {
	private PApplet app;
	public int energia;
	private boolean conVida;
	private float x, y;
	private int ratio;
	private int timer, time, timeGo, timeOr;
	private int comer;
	private PVector pos, mov;
	private PImage image, hambre;

	public Cabra(Potrero potrero) {
		app = potrero.getApp();
		energia = 150;
		conVida = true;
		ratio = 50;
		image = app.loadImage("../data/dataMine/Murloc.png");
		hambre = app.loadImage("../data/dataMine/Murloc con hambre.png");

		x = (float) ((Math.random() * (Logica.widthL-10)) - ratio);
		y = (float) ((Math.random() * (Logica.heightL-10)) - ratio);
		pos = new PVector(x, y);
		float prex = (float) ((app.random(-2, 2)));
		float prey = (float) ((app.random(-2, 2)));
		mov = new PVector(prex, prey);
		timeGo = time;

	}

	public int setEnergia(int energiaDelPotrero) {
		if (energiaDelPotrero > 0) {
			if (energia < 148) {
				comer = 15;
				energia += comer;
			} else {
				comer = 0;

			}
		}
		if (energia > 150) {
			energia = 150;
		}
		return comer;
	}

	private void mover() {
		float imageX = image.width/2;
		float imageY = image.height/2;
		if (pos.x < imageX || pos.x > app.width- imageX) {
			mov.x *= -1;
		}
		if (pos.y < imageY || pos.y > app.height- imageY) {
			mov.y *= -1;
		}
		pos.add(mov);

	}

	public void pintar() {
		app.pushStyle();
		app.fill(255, 0, 0);
		if (conVida) {
			app.imageMode(PConstants.CENTER);
			if (energia < 50) {
				image = hambre;
			}
			app.image(image, pos.x, pos.y);
			// app.ellipse(pos.x, pos.y, ratio, ratio);
			barraVida();

		}
		app.popStyle();

		mover();
	}

	private void barraVida() {
		app.pushStyle();
		float x = pos.x - ((ratio / 2) + 25);
		float y = pos.y - ((ratio / 2) + 25);

		app.fill(0, 255, 0);
		float map = PApplet.map(energia, 0, 150, 0, 100);
		app.rect(x, y, map, 5);
		app.stroke(255);
		app.noFill();
		app.rect(x, y, 100, 5);

		app.popStyle();
	}

	public void existir(PApplet app) {
		timer();
		// Cada 2 segundos debe consumir 2 unidades de energia
		// Si el nivel de energia es <= 0 debe invocar el metodo morir()

		if (timeGo != time) {
			timeGo = time;
			timeOr++;
			if (timeOr % 2 == 0 && timeOr != 0) {
				energia -= 2;
			}
			morir();
		}

	}

	public void morir() {
		if (energia <= 0) {
			conVida = false;
		}
	}

	//// ****** Getters & Setters

	/**
	 * La cabra vive?
	 * 
	 * @return false si esta muerta
	 */
	public boolean isConVida() {
		return conVida;
	}

	public void timer() {
		if (app.millis() - timer >= 1000) {
			timer = app.millis();
			time++;
		}
	}
}