package visual_interface;

import javax.swing.JOptionPane;
import logic.Logic;
import processing.core.*;
import server.ServerCom;

public class MainInterface extends PApplet {

	public Logic logic;

	public Statistics stats;

	private int width, height;

	int timer, time;

	private int contador;
	private int timerD;

	public static void main(String[] args) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new MainInterface());

	} // main

	public void settings() {
		width = 1200;
		height = 600;
		size(width, height);

		timerD = 0;
		contador = 0;
	}// SETTINGS

	public void setup() {
		logic = new Logic(this);
		stats = new Statistics(this);
	}// SETUP

	public void draw() {
		background(180);
		stats.draw();
		// cada 1 segundos
		if (millis() - timer >= 1000) {
			timer = millis();
			time++;
			if (ServerCom.clients.size() >= ServerCom.minClients) {
				ServerCom.write_time_in_file(time);
				contador(10);
			}
		}
	}// DRAW

	public void contador(int limit) {
		fill(255);
		textSize(25);

		if (millis() - timerD >= 1000) {
			timerD = millis();
			contador += 1;
			if (contador == limit) {
				contador = 0;
			}
		}

		text("TIMER: " + contador, 50, 20);

	}

	// public void addGroup() {
	// stats.addGroup();
	// }// add group

	public void mousePressed() {

	}// mouse pressed

	public void mouseClicked() {

	}// mouse clicked

	public void panelInformacion(String mensaje, String titulo) {
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
	}// pop up

	public void keyPressed() {

	}// key pressed

	public String panelDialogo(String titulo, String pregunta) {
		String inputValue = JOptionPane.showInputDialog(null, pregunta, titulo, JOptionPane.QUESTION_MESSAGE);
		while (inputValue == null || inputValue.toCharArray().length == 0 || inputValue == " ") {
			inputValue = JOptionPane.showInputDialog(null, pregunta, titulo, JOptionPane.QUESTION_MESSAGE);
		} //
		return inputValue;
	}// popUp

	public String panelDialogoOpciones(String titulo, String[] opciones, String pregunta) {
		String selectedValue = (String) JOptionPane.showInputDialog(null, pregunta, titulo,
				JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
		while (selectedValue == null) {
			selectedValue = (String) JOptionPane.showInputDialog(null, pregunta, titulo, JOptionPane.QUESTION_MESSAGE,
					null, opciones, opciones[0]);
		} //
		return selectedValue;
	}// popUp

	public int panelDialogoOpcionesNumericas(String titulo, String[] opciones, String pregunta) {
		String selectedValue = (String) JOptionPane.showInputDialog(null, pregunta, titulo,
				JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
		while (selectedValue == null) {
			selectedValue = (String) JOptionPane.showInputDialog(null, pregunta, titulo, JOptionPane.QUESTION_MESSAGE,
					null, opciones, opciones[0]);
		} //
		int eleccion = Integer.parseInt(selectedValue);
		return eleccion;
	}// popUp

	// --------------- GETTERS & SETTERS ---------------

}// INTERFAZ PRINCIPAL