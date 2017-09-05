package communication;

import java.io.*;
import java.net.*;
import common.Message;
import logic.Logic;
import logic.User;

public class Communication_to_Server implements Runnable {

	private static Communication_to_Server ref;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public Logic logic;
	private User user;

	private String localIp = "127.0.0.1";
	//private String ip = "BUSCAR";// TODO

	private int port = 5000;

	private boolean running;
	private boolean connected;
	private boolean reset;
	private boolean notifyError;
	private  Message lastMessage;

	private Communication_to_Server(Logic logic) {
		this.logic = logic;
		socket = null;
		running = true;
		connected = true;
		reset = false;
		notifyError = false;
		lastMessage = new Message();
	}// constructor

	public Communication_to_Server(User user) {
		this.user = user;
		socket = null;
		running = true;
		connected = true;
		reset = false;
		notifyError = false;
		lastMessage = new Message();
	}// constructor

	// -------------- CONFIGURATION ----------------

	public static Communication_to_Server getInstance(Logic log) {
		if (ref == null) {
			ref = new Communication_to_Server(log);
			Thread t = new Thread(ref);
			t.start();
		} // if not created
		return ref;
	}// get instance

	public static Communication_to_Server getInstance(User user) {
		if (ref == null) {
			ref = new Communication_to_Server(user);
			Thread t = new Thread(ref);
			t.start();
		} // if not created
		return ref;
	}// get instance

	private boolean connect() {
		try {
			InetAddress dirServidor = InetAddress.getByName(localIp);
			socket = new Socket(dirServidor, port);

			try {
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				// System.out.println("[ Se abren OOS y OIS en Cliente ]");
			} catch (IOException e) {
				System.out.println("[ error iniciating OOS or OIS]");
				e.printStackTrace();
			} // try catch
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("[ UNKNOWN SERVER ]");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[ ERROR establishing conexion ]");
			if (!notifyError) {
				notifyError = true;
			} // if
			return false;
		} // try catch
		return true;
	}// connect

	public void retry() {
		connected = true;
		reset = true;
		notifyError = false;
	}// retry

	// --------------- FUNCTIONALITIES -----------------

	@Override
	public void run() {
		System.out.println("[ COMUNICATION THREAD INICIATED ]");
		while (running) {
			try {
				if (connected) {
					if (reset) {
						if (socket != null) {
							try {
								socket.close();
							} catch (IOException e) {
								System.out.println("[ CONEXION TO SERVER RESETED ]");
							} finally {
								socket = null;
							} // try catch
						} // socket no nulo
						reset = false;
					} // if reset
					connected = !connect();
				} else {
					if (socket != null) {
						receiveMessages();
					} // if socket null
				} // if is connected
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				System.out.println("[ INTERRUPTION ]");
			} catch (SocketTimeoutException e) {
				System.out.println("[ TIME TO CONNECT FINISHED ]");
				// e.printStackTrace();
			}
		} // online

		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("[ ERROR CLOSING SOCKET ]");
			e.printStackTrace();
		} finally {
			socket = null;
		} // try catch for closing socket
	}// RUN

	private void receiveMessages() throws SocketTimeoutException {
		try {
			if (!socket.isClosed()) {
				System.out.println("[ RECEIVING ]");
				Object obj = ois.readObject();

				if (obj.getClass() == Message.class) {
					Message msmRecibido = (Message) obj;
					manageMessages(msmRecibido);
				} // is a Message

			} // port is open
		} catch (IOException e) {
			System.out.println("[ IO Error while Receiving ]");
		} catch (NullPointerException e1) {
			System.out.println("[ Nullpinter receiving ]");
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[ class not found]");
			e.printStackTrace();
		} // try catch
	}// Receive Message

	private void manageMessages(Message msm) {
		String type = msm.getType();

		System.out.println("[ Message from : " + msm.getUserId() + ", of type: " + type + " ]");

		switch (type) {
		case "cambiarId":
			user.setId(msm.getData());
			System.out.println("[ My Id is now " + user.getId() + " ]");
			break;

		case "empezar":
			user.setEmpezar(msm.getEmpezar());
			System.out.println("[ Empezar ]");
		}// main switch

	}// Received Message

	public void sendMessage(Message msm) {
	//	System.out.println("CCS " + lastMessage.equals(msm) + " " + lastMessage.getStringValues() + " : " + msm.getStringValues());
		if (socket != null && !lastMessage.equals(msm)) {
			System.out.println("[ El cliente envia Message: " + msm.getType() + " ]");
			try {
				oos.writeObject(msm);
				oos.flush();
				lastMessage = msm;
			} catch (IOException e) {
				// e.printStackTrace();
				System.out.println("[ ERROR AL ENVIAR ]");
			} // try catch
		} // socket is open
	}// send Message

	// --------- GETTERS Y SETTERS -----------
	public void setIp(String ip) {
		this.localIp = ip;
	}

	public void setport(int port) {
		this.port = port;
	}

	public Communication_to_Server getMyCom() {
		return ref;
	}// retorna la referencia

	public boolean isrunning() {
		return running;
	}

	public void setrunning(boolean running) {
		this.running = running;
	}

}//
