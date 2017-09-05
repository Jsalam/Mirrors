package logic;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 13L;

	private String userName;
	private int id;
	private boolean empezar;

	private int goats;
	// Energy is a %
	private float energy;

	public User() {

	}// empty constructor

	public User(int id, String userName) {
		this.userName = userName;
		this.id = id;
		goats = 0;
		// Energy is a %
		energy = 0;
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
	}// get user name

	public int getNumberOfGoats() {
		return goats;
	}// get number of goats

	public void setNumberOfGoats(int numberOfGoats) {
		this.goats = numberOfGoats;
	}// set number of goats

	public float getEnergy() {
		return energy;
	}// get energy

	public void setEnergy(float energy) {
		this.energy = energy;
	} // set energy

	public boolean getEmpezar() {
		return empezar;
	}

	public void setEmpezar(boolean data) {
		this.empezar = data;

	}

}// USER
