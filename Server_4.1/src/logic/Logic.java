package logic;

import java.net.BindException;

import server.ServerCom;
import visual_interface.MainInterface;

public class Logic {

	public MainInterface theInterface;
	public static ServerCom com;
	private User user;

	public Logic(MainInterface theInterface) {
		this.theInterface = theInterface;
		user = new User();

		try {
			com = new ServerCom(this);
			com.start();
			System.out.println("[ SERVER ON & RUNNING ]");
		} catch (BindException e) {
			System.out.println("[ Theres is another server already running in this port ]");
			com.close();
			// e.printStackTrace();
		} // try catch to initiate the server
	}// CONSTRUCTOR

	// ----------------- FUNCTIONS ------------------

	// ------------- GETTERS & SETTERS --------------
	public User getUser() {
		return user;
	}// get user

	public MainInterface getTheInterface() {
		return theInterface;
	}// get inteface

}// CLASE LOGICA
