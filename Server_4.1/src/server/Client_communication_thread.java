package server;

import java.io.*;
import java.net.Socket;
import common.Message;
import logic.User;

public class Client_communication_thread extends Thread {

	private int id;
	private boolean online;

	public ServerCom server;
	private Socket socket;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private User client;

	public Client_communication_thread(Socket ref, ServerCom server) {
		this.server = server;
		socket = ref;

		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			// System.out.println("[ Se abren OOS y OIS en Servidor ]");

		} catch (IOException e) {
			System.out.println("[ error al iniciar OOS u OIS en Servidor]");
			e.printStackTrace();
		} // try catch

		online = true;
		client = new User();
		// System.out.println("[ Nueva Atencion creada]");
	}// Constructor

	@Override
	public void run() {
		while (online) {
			receiveMessages();
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // try catch
		} // while
	}// run

	private void receiveMessages() {
		try {
			if (!socket.isClosed()) {
				// System.out.println("[ CCT RECEIVING ]");
				Object obj = ois.readObject();
				if (obj.getClass() == Message.class) {
					Message msm = (Message) obj;
					manageMessages(msm);
				} // is a Message
			} // socket is open

		} catch (IOException e) {

			online = false;
			try {
				ois.close();
				socket.close();
				socket = null;
				// myGroup.deleteClient(id);
			} catch (IOException e1) {
				e1.printStackTrace();
			} // try catch

		} catch (ClassNotFoundException e) {
			System.out.println("[ Clase no encontrada en Servidor]");
			e.printStackTrace();
		} // TRY CATCH

	}// Receive Message

	public void manageMessages(Message msm) {

		String type = msm.getType();
		// int userId = msm.getUserId();

		switch (type) {
		case "goats":
			// TODO
			System.out.println(this.getClass().getName() + " " + msm.getData());
			client.setGoats(msm.getData());
			break;
		case "energy":
			// TODO
			System.out.println(this.getClass().getName() + " " + msm.getData());
			client.setEnergy(msm.getData());
			break;
		}// switch
	}// manage received messages

	public void sendMessage(Message msm) {
		if (socket != null) {
			System.out.println("[ El servidor envia Message: " + msm.getType() + " ]");
			try {
				oos.writeObject(msm);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[ ERROR while sending message]");
			} // try catch
		} // socket is open
	}// send Message

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public int getMyId() {
		return id;
	}

}// ARENCION client