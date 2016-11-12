
#include <Servo.h>

Servo servo;
int posActual = 0;
int target = 90;
int minDelay = 20;

//Constante que se va a usar para la formula de la aceleracion en los espejos cuando se mueven hacia una persona
float K = 0.7; // entre mas cercano a 1 mas lenta la aceleracion

void setup() {
  Serial.begin(9600);
  servo.attach(10);  // attaches the servo on pin 10 to the servo object
  initialize();
  posActual = servo.read();
}

void loop() {
  // Esta parte es solo para intruducir angulos con el teclado.
  // Simplemente digite un caracter en el serial monitor
  // Se debe reemplazar por el angulo que se reciba del bluetooth
  if (Serial.available() > 0) {
    // read the incoming byte:
    target = Serial.read();

    // say what you got:
    Serial.print("I received: ");
    Serial.println(target, DEC);
  }


  if (posActual != target) {
    // A favor de las manecillas
    if (posActual < target) {
      rotatePositive(target);
    }
    // Contrario a las manecillas
    if (posActual > target) {
      rotateNegative(target);
    }
  }
  //printMonitor();
}

void rotatePositive(int target) {
  do {
    posActual ++;
    servo.write(posActual);
    int acel = aceleracionPositiva();
    delay(acel);
    Serial.print("Aceleracion: ");
    Serial.println(acel);
  } while (posActual < target);
}

void rotateNegative(int target) {
  do {
    posActual --;
    servo.write(posActual);
    int acel = aceleracionNegativa();
    delay(acel);
    Serial.print("Aceleracion: ");
    Serial.println(acel);
  } while (posActual > target);
}

//
int aceleracionPositiva() {
  int result = ((-K) * (posActual - target));
  if (result < minDelay) {
    result = minDelay;
  }
  return result;
}

int aceleracionNegativa() {
  int result = ((-K) * (target - posActual));
  if (result < minDelay) {
    result = minDelay;
  }
  return result;
}
//
void initialize() {
  servo.write(180);
  delay(100);
  for (int i = 0; i <= 180; i++) { // turn the servo all one way
    servo.write(i);
    delay(20);
  }
  delay(100);
  for (int i = 180; i > 0; i--) { // then the other
    servo.write(i);
    delay(20);
  }
  delay(100);
  Serial.println("Initialization done");
}
//
void printMonitor() {
  Serial.print("servo: ");
  Serial.println(posActual);
}
