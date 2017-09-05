package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import common.Message;
import logic.Logic;

public class ServerCom extends Thread {

	// Server variables
	public Logic logic;

	private ServerSocket serverSocket;
	public static ArrayList<Client_communication_thread> clients;
	public static int minClients = 2;
	private boolean online;

	// Users
	private int usersConnected;
	private String[] usuarios = { "User 1", "User 2", "User 3", "User 4", "User 5", "User 6", "User 7", "User 8",
			"User 9", };

	// Writer
	// private String file_path = "/Users/juansalamanca/Google
	// Drive/Courses/Pregrado/HCI 2/HCI2 2017 - 2 (Fall)/Project 1/results.txt";
	private String local_file_path = "./data/data/results.txt";
	public FileWriter fileWriter;
	private static BufferedWriter writer = null;
	private File file;

	// time
	int time;
	long timer;

	public ServerCom(Logic logic) throws BindException {

		this.logic = logic;
		clients = new ArrayList<Client_communication_thread>();

		try {
			serverSocket = new ServerSocket(5000);

			InetAddress local_inet = InetAddress.getLocalHost();
			System.out.println("{ LOCAL INET ADRESS: " + local_inet + " }");

			InetAddress[] ips = InetAddress.getAllByName(local_inet.getCanonicalHostName());
			/*
			 * if (ips != null) { for (int i = 0; i < ips.length; i++) {
			 * System.out.println("{ ALL SISTEM INET ADRESS: " + ips[i] + " }");
			 * } // for } // if
			 */

			online = true;
		} catch (IOException e) {
			e.printStackTrace();
		} // try catch for opening service

		initializeFile();
		time = 0;
		timer = System.currentTimeMillis();
	}// CONSTRUCTOR

	public void initializeFile() {
		try {
			file = new File(local_file_path);
			fileWriter = new FileWriter(file, true);
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath()), "utf-8"));
		} catch (IOException e) {
			System.out.println("[ ERROR while creating the file to be written ]");
			e.printStackTrace();
		} // try catch

		createFile();
	}// initialize file

	@Override
	public void run() {
		while (online) {
			try {
				if (usersConnected < usuarios.length) {
					System.out.println("[WAITING FOR CLIENTS]");
					Socket socket = serverSocket.accept();

					Client_communication_thread newClient = new Client_communication_thread(socket, this);

					Message msm = new Message(0, "cambiarId", usersConnected);
					newClient.start();
					newClient.sendMessage(msm);
					newClient.getClient().setId(usersConnected);
					newClient.getClient().setUserName(usuarios[usersConnected]);
					usersConnected++;
					clients.add(newClient);

					if (clients.size() >= minClients) {
						System.out.println("record saved before");
						msm = new Message(0, "empezar", true);
						send_everybody(msm);
					}

					System.out.println("[CLIENT CONNECTED]");
					System.out.println("[Attending " + usersConnected + " clients]");
					sleep(100);
				}
			} catch (InterruptedException e) {
				System.out.println("[ Se ha interrumpido la conexion con el cliente ]");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("[ ERROR en la conexion del cliente ]");
				e.printStackTrace();
			} // try catch
		} // while online

	}// RUN

	public static void write_time_in_file(int time) {
		for (Client_communication_thread cct : ServerCom.clients) {
			write_into_file(time + "," + cct.getClient().getValuesForRecord());
		}
	}

	// ------------- RECEIVING INFORMATION --------------

	public void notifyMessage(Message msm) {
		// Type of message
		String type = msm.getType();
		// Identify the message's owner (who send the message)
		int id = msm.getUserId();

		switch (type) {
		case "goats":
			// TODO
			String line = id + " has now " + msm.getData() + " goats.";
			// user.setGoats(msm.getData());
			write_into_file(line);
			break;
		case "energy":
			line = id + " has now " + msm.getData() + " energy.";
			// user.setEnergy(msm.getData());
			write_into_file(line);
			// TODO
			break;
		}// switch
	}// notify

	// ------------- SENDING INFORMATION ---------------

	public void send_everybody_except(Message msm, int id) {
		System.out.println("[ Do not send Message to:" + id + " ]");
		for (int i = 0; i < clients.size(); i++) {
			// int actualId = groups.get(i).getGroupId();
			// if (id != actualId) {
			clients.get(i).sendMessage(msm);
			// } // if except
		} // for of clients
	}// send everybody except to

	public void send_everybody(Message msm) {
		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).sendMessage(msm);
		} // for
	}// send to everybody

	public void send_only_to(Message msm, int idDestino) {
		clients.get(idDestino - 1).sendMessage(msm);
	}// send only to

	// ------------- WRITING FILES --------------

	public void createFile() {
		try {
			writer.write("HCI II, Proyect 2");
			writer.newLine();
			writer.write("Universidad Icesi, Cali-Colombia"); // */
			writer.write("time,id,userName,goats,energy");
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			System.out.println("[ERROR when writing into file]");
			e.printStackTrace();
		} // try catch to create the file
	}// guardar datos

	public static boolean write_into_file(String line) {
		boolean written = false;
		try {
			writer.write(line);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			System.out.println("[IO ERROR when writing into the file]");
			e.printStackTrace();
			try {
				writer.close();
			} catch (IOException e1) {
				System.out.println("[ Error closing the file writer ]");
			} // try catch for closing
		} // try catch of write file
		return written;
	}// write into file

	public void close() {
		try {
			writer.close();
			System.out.println("**** File Saved *****");
		} catch (IOException e) {
			System.out.println("**** ERROR while saving the file ****");
			e.printStackTrace();
		} // try catch
	}// close

}// SERVER
