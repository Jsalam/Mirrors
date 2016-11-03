#include <Servo.h>
String recibido;
Servo servo1;  // Crea un Objeto servo
int pos;    // Variable de la posicion del servo
int anguloTemporal, anguloFinal;
boolean activar, activarIzq, reseteo;
int anguloLlega;

void setup() {
  recibido = "";
  Serial1.begin(9600);
  Serial.begin(9600);
  // pinMode(13, OUTPUT);
  // digitalWrite(13, HIGH);

  servo1.attach(9);  // Selecionamos el pin 2 como el pin de control para els ervo
  activar = true;
  activarIzq = true;
  reseteo = true;
  anguloTemporal = 0;
  anguloFinal = 0;
}

void loop() {

  //Bluetooth
  while (Serial1.available()) {
    char c = Serial1.read();
    recibido += c;
    delay(10);
  }

  if (recibido.length() > 0) {
    //Serial.println(recibido);
    int n = recibido.toInt();
    anguloLlega = n;
    activar = true;
    activarIzq = true;
    recibido = "";
    Serial.println("llega:");
    Serial.println(n);
    Serial.println("Final:");
    Serial.println(anguloLlega);
    Serial.println("Temporal:");
    Serial.println(anguloTemporal);
  }

  //------------------------

  delay(120);
  //posicion = 80;            // Establecemos el valor de la posicion a 150º
  // posicion = map(posicion, 0, 1023, 0, 179);     // Establecemos la relacion entre los grados de giro y el PWM
  /* Con el mapa de valores anterior establecemos una relacin proporcional */
  /* entre el pulso minimo 0 con el grado minimo, 0 tambien y el pulso maximo*/
  /* 1023 con el grado maximo, 179*/
  //servo1.write(90);                  // Escribimos la posicion con el mapa de valores al servo
  // delay(2000);                           // Y le damos un tiempo para que sea capaz de moverse

  //if(reseteo==true){
  //   for (pos = 180; pos >=0; pos -= 1) { // goes from 0 degrees to 180 degrees
  //      // in steps of 1 degree
  //      servo1.write(pos);              // tell servo to go to position in variable 'pos'
  //      delay(12);                       // waits 15ms for the servo to reach the position
  //
  //      if (pos == angulo) {
  //        activar = false;
  //      }
  //    }
  //  }


  anguloTemporal = anguloFinal;
  anguloFinal = anguloLlega;


  calculoAngulo (anguloTemporal, anguloLlega);


}


void giros (boolean mov, int angulo) {
  if (mov == true) {
    if (activar == true) {
      for (pos = anguloTemporal; pos <= angulo; pos += 1) { // goes from 0 degrees to 180 degrees
        // in steps of 1 degree
        servo1.write(pos);              // tell servo to go to position in variable 'pos'
        delay(30);                       // waits 15ms for the servo to reach the position

        if (pos == angulo) {
          activar = false;
        }
      }
    }
  } else {
    if (activarIzq == true) {
      for (pos = anguloTemporal; pos >= angulo; pos -= 1) {
        servo1.write(pos);
        delay(30);

        if (pos == angulo) {
          activarIzq = false;
        }
      }
    }
  }
}

void calculoAngulo (int  posicion1, int posicion2) {

  int posicionInicial = posicion1 - posicion2;
  if (posicionInicial < 0 ) {

    giros(true, posicion2);

  } else {

    giros(false, posicion2);

  }

}
