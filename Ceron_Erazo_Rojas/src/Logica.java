import common.Message;
import communication.Communication_to_Server;
import logic.User;
import processing.core.PApplet;
import processing.core.PConstants;

public class Logica extends PApplet {

	Potrero potrero;
	int time;
	int timer = 0;

	boolean empezarServidor;

	public static float widthL;
	public static float heightL;

	User user;
	Communication_to_Server cts;

	public static void main(String[] args) {
		PApplet.main("Logica");
	}

	public void settings() {
		size(1200, 700);
	}

	public void setup() {
		widthL = width;
		heightL = height;

		potrero = new Potrero(this);
		time = 0;
		user = new User();
		String IPL = "127.0.0.1";
		String IP = "172.30.174.64";

		 cts = Communication_to_Server.getInstance(user);
		 cts.setIp(IPL);
	}

	public void draw() {
		// System.out.println(cts.lastMessage.getStringValues());
		background(0);

		potrero.draw();

		// Datos de identificaci�n y reporte
		text("Mi id: " + user.getId(), 100, 90);
		text("Cabras: " + potrero.getNumeroCabras(), 100, 110);
		text("Energia potrero: " + potrero.getTotalEnergia(), 100, 130);
		text("Tiempo: " + time, 100, 150);

		// La variable user.getEmpezar es un boleano controlado en el servidor.
		// Se vuelve true cuando se terminan de conectar todos los jugadores
		// invitados. Esto con el fin de que todos los potreros inicien a juguar
		// al tiempo
		if (user.getEmpezar()) {

			// Pobla el potrero y le asigna energia inicial
			potrero.init();

			text("Empez� ", 100, 170);

			// cada 1 segundos
			if (millis() - timer >= 1000) {
				timer = millis();
				time++;

				// Envia energia al servidor
				int UserID = user.getId();

				// Preparamos datos para enviar el mensaje de este potrero al
				// servidor

				// 1 identificar que el mensaje es del potrero con la palabra
				// "energy".
				String tipo = "energy";

				// 2 Tomar el valor de energ�a del potrero
				int energia = potrero.getTotalEnergia();

				// 3 Enviar el mesaje
				 cts.sendMessage(new Message(UserID, tipo, energia));
				 System.out.println(tipo +" "+ energia);
			}

			// Actualice el valor de energ�a del potrero de acuerdo a la
			// cantidad de cabras que haya en este momento
			potrero.addEnergia();
			potrero.actualizarEnergia();
		} else { text("En Pausa ", 100, 170);
		}
	}

	public void keyReleased() {

		// cuando se adicione o se remueva una cabra
		if (keyCode == PConstants.UP || keyCode == PConstants.DOWN) {

			// Este metodo simplemente adiciona o remueve cabras dependiendo de
			// la
			// flecha que oprima el jugador
			potrero.administrarCabra(keyCode);

			// Preparamos datos para enviar el mensaje de este potrero al
			// servidor cada vez que se adicionen o remuevan cabras
			 int UserID = user.getId();

			// identificar que el mensaje relativo al numero de cabras con la
			// palabra "goats"
			String tipo = "goats";
			int cabras = potrero.getNumeroCabras();
			 cts.sendMessage(new Message(UserID, tipo, cabras));
		}
	}

}