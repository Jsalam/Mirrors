package visual_interface;

import java.awt.Color;

import logic.User;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Bar implements Comparable<Bar> {
	private float width, maxHeight;
	private float currentValue, height, minValue, maxValue;
	private int id;
	private Color color;
	public PApplet app;

	public Bar(PApplet app, int maxHeight, float minValue, float maxValue, int id) {
		this.app = app;
		this.maxHeight = maxHeight;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.id = id;
		width = 5;
		height = 5;
		color = new Color(70,70,70);
	}

	public void showGoats(User user, PVector pos) {
		if (user.getGoats() < 0) {
			currentValue = -1;
			app.fill(200, 30);
		} else {
			app.fill(user.color.getRGB());
			currentValue = PApplet.map(user.getGoats(), minValue, maxValue, 0, maxHeight);
		}
//		if (currentValue > maxHeight){
//			height = maxHeight;
//		}
		height = currentValue/2;
		app.rect(pos.x, pos.y, width, height);
		app.fill(255);
		app.textSize(16);
		app.pushMatrix();
		app.translate( pos.x +5, pos.y - 4);
		app.rotate(-PConstants.HALF_PI);
		app.text("ID: "+user.getId() + ", " +user.getUserName() , 0, width/2);
		app.popMatrix();
		app.text( user.getGoats(), pos.x +5, pos.y + height - 4);
	}

	public void showEnergy(User user, PVector pos) {
		if (user.getEnergy() < 0) {
			currentValue = -1;
			app.fill(200, 30);
		} else {
			app.fill(user.color.getRGB());
			currentValue = PApplet.map(user.getEnergy(), minValue, maxValue, 0, maxHeight);
		}
//		if (currentValue > maxHeight){
//			height = maxHeight;
//		}
		height = currentValue / 2;
		app.rect(pos.x, pos.y, width, height);
		app.fill(255);
		app.textSize(16);
		app.pushMatrix();
		app.translate( pos.x +5, pos.y - 4);
		app.rotate(-PConstants.HALF_PI);
		app.text("ID: "+user.getId() + ", " +user.getUserName() , 0, width/2);
		app.popMatrix();
		app.text( user.getEnergy(), pos.x +5, pos.y + height - 4);
		//app.text( height, pos.x +5, pos.y + height - 4);
	}

	public boolean equals(Bar bar) {
		if (id == bar.getId())
			return true;
		else
			return false;
	}

	public void setValueA(float val) {
		currentValue = val;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public int compareTo(Bar o) {
		Bar other = o;
		return this.id - other.id;
	}

}
