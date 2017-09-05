package logic;

import common.Message;
import communication.*;
import logic.User;
import visual_interface.MainClientInterface;

public class Logic {

	private MainClientInterface theInterface;
	private Communication_to_Server communication;
	private User user;

	public Logic(MainClientInterface theInterface) {
		this.theInterface = theInterface;
		user = new User();
		communication = Communication_to_Server.getInstance(user);
	}// CONSTRUCTOR

	// ----------------- FUNCTIONS ------------------

	// here write the code you need

	public void buyGoat() {
		user.setNumberOfGoats(user.getNumberOfGoats() + 1);
		send_numberOfGoats();
	}// buy goat

	public void sacrificeGoat() {
		user.setNumberOfGoats(user.getNumberOfGoats() - 1);
		send_numberOfGoats();
	}// buy goat

	public void energy(int value) {
		send_energy();
	}// increase energy

	// ----------------- MESSAGES ------------------

	public void send_numberOfGoats() {
		Message numberOfGoats = new Message(user.getId(), "goats", user.getNumberOfGoats());
		communication.sendMessage(numberOfGoats);
	}// send number of goats

	public void send_energy() {
		Message energy = new Message(user.getId(), "energy", user.getEnergy());
		communication.sendMessage(energy);
	}// send number of goats

	// ------------- GETTERS & SETTERS --------------

	public Communication_to_Server getCom() {
		return communication.getMyCom();
	}// get communication

	public User getUser() {
		return user;
	}// get user

	public MainClientInterface getTheInterface() {
		return theInterface;
	}// get inteface

}// CLASE LOGICA
