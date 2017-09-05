package logic;

import java.awt.Color;
import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 13L;

	private String userName;
	private int id;
	private int goats;
	private int energy;
	public Color color;

	public User() {
		goats = 0;
		energy = 0;
		color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
		System.out.println((int) (Math.random() * 255));
	}// empty constructor

	public User(int id, String userName) {
		goats = 0;
		energy = 0;
		this.userName = userName;
		this.id = id;
		color = new Color((int) Math.random() * 255, (int) Math.random() * 255, (int) Math.random() * 255);
		System.out.println(Math.random() * 255);
	}// basic constructor

	// ----------- GETTERS Y SETTERS -------------
	public void setId(int id) {
		this.id = id;
	}// change id

	public int getId() {
		return id;
	}// get id

	public String getUserName() {
		return userName;
	}// get user name

	public String getValuesForRecord() {
		String rtn = id + "," + userName + "," + goats + "," + energy;
		return rtn;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}// set user name

	public int getGoats() {
		return goats;
	}// get goats

	public void setGoats(int goats) {
		this.goats = goats;
	}// set Goats

	public int getEnergy() {
		return energy;
	}// get Energy

	public void setEnergy(int energy) {
		this.energy = energy;
	}// set Energy

}// USER
