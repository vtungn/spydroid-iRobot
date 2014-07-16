
/*
create Jun 2nd,2014
by Tung Nguyen
*/

const int DigOutLight=13;
//const int analogIn
//SoftwareSerial mySerial(2,3);
void setup() {
  Serial.begin(9600);
  Serial.println("Start up light control device!");
  Serial.println("Waiting for input:");
  pinMode(13,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(11,OUTPUT);
  
  
}

void loop() {
   if(Serial.available()) {
       char Sin;
       //Serial.read(read);
       //Serial.write(read);
       Sin = Serial.read();
       Serial.print("Serial input:");
       Serial.println(Sin);
       if(Sin == 48) {
         digitalWrite(DigOutLight,HIGH);
         Serial.println("L1ON");
       }
       else if(Sin == 50) {
         digitalWrite(12,HIGH);
         Serial.println("L2ON");
       }
       else if (Sin==49) {
         digitalWrite(DigOutLight,LOW);
         Serial.println("L1OFF");
       }
       else if(Sin==51) {
         digitalWrite(12,LOW);
         Serial.println("L2OFF");
       }
       else if(Sin==52) {
         digitalWrite(11,HIGH);
         Serial.println("L3ON");
       }
       else if(Sin==53) {
         digitalWrite(11,LOW);
         Serial.println("L3OFF");
       }
       else {
         digitalWrite(13,LOW);
         digitalWrite(12,LOW);
         digitalWrite(11,LOW);
         Serial.println("ALL OFF");
       }
       
   }
   
  //delay(100);
}
