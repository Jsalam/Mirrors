/* Sweep
  by BARRAGAN <http://barraganstudio.com>
  This example code is in the public domain.

  modified 8 Nov 2013
  by Scott Fitzgerald
  http://www.arduino.cc/en/Tutorial/Sweep
*/

#include <Servo.h>

Servo myservo;  // create servo object to control a servo
Servo myservo2;  // create servo object to control a servo
// twelve servo objects can be created on most boards

int pos = 1;    // variable to store the servo position
int delayValue = 100;
int angle = 1;
void setup() {
  myservo.attach(9);  // attaches the servo on pin 9 to the servo object
  myservo2.attach(10);  // attaches the servo on pin 9 to the servo object
  initialize(180);
}

void loop() {

  pos = rotate(180, 1);
  // myservo.write(rotate(90, 3));             // tell servo to go to position in variable 'pos'
   myservo2.write(pos);             // tell servo to go to position in variable 'pos'
  delay(delayValue);
  Serial.println("pos:");
  Serial.println(pos);
  Serial.println("servo2:");
  Serial.print(myservo2.read());
  Serial.println(" ");
}
int rotate(int target, int stepSize) {
  if (target > angle) {
    angle = angle + stepSize;
  } else {
    angle = angle - stepSize;
  }
  return angle;
}

void initialize(int target) {
  myservo2.write(target);
  delay(100);
  for (int i = 0; i <= target; i++) { // turn the servo all one way
    myservo2.write(i);
    delay(20);
  }
  delay(100);
  for (int i = target; i > 0; i--) { // then the other
    myservo2.write(i);
    delay(20);
  }
  delay(100);
}

