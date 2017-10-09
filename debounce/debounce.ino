const int buttonPin = 2;

int counter = 0;
int buttonState;
int lastButtonState = LOW;
unsigned long lastDebounceTime = 0; 
unsigned long debounceDelay = 50;

void buttonPressed() {
  int reading = digitalRead(buttonPin);
  
  if ((millis() - lastDebounceTime) > debounceDelay) {
    if (reading != buttonState) {
      buttonState = reading;
      if (buttonState == HIGH) {
         counter++;
      }
    }
  }
  if (reading != lastButtonState) {
    lastDebounceTime = millis();
  }
  
  lastButtonState = reading;
}
 

void setup() {
  pinMode(buttonPin, INPUT_PULLUP);
  // Interrupts can happen on "edges" of signals.  
  // Three edge types are supported: CHANGE, RISING, and FALLING 
  attachInterrupt(digitalPinToInterrupt(buttonPin), buttonPressed, CHANGE);
  Serial.begin(9600);
}

void loop() {
  Serial.println(counter);
  delay(1000);
}
