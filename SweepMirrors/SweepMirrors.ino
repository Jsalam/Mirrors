
#include <Servo.h> 
Servo myservo;  // create servo object to control a servo
float pos = 0;    // variable to store the servo current position 
float posAbanico = 0;
int target = 90;    // variable to store the servo destination 
float K = 0.01; //Constante que se va a usar para la formula de la aceleracion en los espejos cuando se mueven hacia una persona
String incomingByte = 0;   // for incoming serial data
boolean inMotion = false;
float origin;
float elapsedTime = 0;
int cont = 0;
// Pin 13 has an LED connected on most Arduino boards.
int led = 13;


void setup() 
{ 
  myservo.attach(2);  // attaches the servo on pin 9 to the servo object 
  Serial.begin(9600);
} 


void loop() 
{ 
  // Receive data from Serial Monitor
  if (Serial.available() > 0) {
    incomingByte = Serial.readString();
    target = incomingByte.toInt();
    Serial.println(target);
  }

  // If pos is != than target
  if (pos <= target-1 || pos >= target+1){
    // reinicio contadore del abanico
    cont = 0;
    //if in motion
    if (!inMotion){
      // Stores the initial angle to calculate acceleration
      origin = pos;
      pos = posAbanico;
      inMotion = true;
    }
    else{
      elapsedTime = millis();
      pos += aceleracion1(); // There are 2 acceleration controls try both!!!
      myservo.write(pos);
      delay(15); 
    }
  }
  // if at target
  else{
    // turn the LED on (HIGH is the voltage level)
    digitalWrite(led, HIGH);
    // keep it on for 0.1 seconds
    delay (100);
    // turn the LED off by making the voltage LOW
    digitalWrite(led, LOW);    
    inMotion = false;
    if (millis()-elapsedTime > 10000){
      abanico();
    }
  }
} 


// ****** Acceleration control
float aceleracion2 () {
  float result = 0;
  int midPoint = (target - origin)/2;

  // If the target is above the origin
  if (midPoint > 0){
    // if pos is halfway
    if(pos < (origin+midPoint)){
      // accelerate
      result = (1/(pos - target))/-K;
    }
    else{
      // deccelerate
      result = ((-K) * (pos - target));
    }
    // if the target is below the origin
  }
  else{
    // if pos is halfway
    if(pos > (origin-midPoint)){
      // accelerate
      result = ((-K) * (pos - target));
    }
    else{
      // deccelerate
      result = (1/(pos - target))/-K;
    }
  }
  Serial.print("origen: ");
  Serial.print(origin);
  Serial.print("  target: ");
  Serial.print(target);
  Serial.print("  pos: ");
  Serial.println(pos);

  return result;
}

float aceleracion1(){
  float result = ((-K) * (pos - target));
  return result;
}

int transicion(int dest){
  int result = ((-K) * (pos - dest));
  return result;
}

// ******* METODO DE ABANICO
void abanico(){
  // para la primera vez retome desde donde esta por
  if (cont == 0){
    for( int i = pos ; i < 180; i += 1)  // goes from 0 degrees to 180 degrees 
    {                                  // in steps of 1 degree 
      myservo.write(i);              // tell servo to go to position in variable 'pos' 
      delay(15);                       // waits 15ms for the servo to reach the position 
    } 
    cont ++;
  }
  // para las siguientes desde cero
  else{
    for(int i = 0; i < 180; i += 1)  // goes from 0 degrees to 180 degrees 
    {                                  // in steps of 1 degree 
      myservo.write(i);              // tell servo to go to position in variable 'pos' 
      posAbanico = i; 
      delay(15);         // waits 15ms for the servo to reach the position   

    }     
  }
  for(int i = 180; i>=1; i-=1)     // goes from 180 degrees to 0 degrees 
  {                                
    myservo.write(i);              // tell servo to go to position in variable 'pos' 
    posAbanico = i;
    delay(15);        // waits 15ms for the servo to reach the position 
  } 
}




























