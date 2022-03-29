#include <ArduinoBLE.h>
#include <Arduino_LSM9DS1.h>
int heart_rate;
long int now;
float start,reader,accel_x,accel_y,accel_z,gyro_x,gyro_y,gyro_z;
int temp1, temp2, temp3, temp4, temp5, temp6;
BLEService customService2("2101");
BLEService customService3("3101");



BLEUnsignedIntCharacteristic customtemp1Char("2101", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customtemp2Char("2102", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customtemp3Char("2103", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customtemp4Char("2104", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customtemp5Char("2105", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customtemp6Char("2106", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customaccel_x("3101", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customaccel_y("3102", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customaccel_z("3103", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customgyro_x("3104", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customgyro_y("3105", BLERead | BLENotify);
BLEUnsignedIntCharacteristic customgyro_z("3106", BLERead | BLENotify);
void setup() {
IMU.begin();
Serial.begin(57600); 

pinMode(LED_BUILTIN, OUTPUT);
bluetooth();
Serial.println("Bluetooth device is now active, waiting for connections...");
}
void loop() {
BLEDevice central = BLE.central();
if (central) {
Serial.print("Connected to central: ");
Serial.println(central.address());
digitalWrite(LED_BUILTIN, HIGH);
while (central.connected()) {
  delay(1);

read_temp();
read_IMU();


customtemp1Char.writeValue(temp1);
customtemp2Char.writeValue(temp2);
customtemp3Char.writeValue(temp3);
customtemp4Char.writeValue(temp4);
customtemp5Char.writeValue(temp5);
customtemp6Char.writeValue(temp6);
customaccel_x.writeValue(accel_x);
customaccel_y.writeValue(accel_y);
customaccel_z.writeValue(accel_z);
customgyro_x.writeValue(gyro_x);
customgyro_y.writeValue(gyro_y);
customgyro_z.writeValue(gyro_z);
Serial.print("At Main Function");

    Serial.print(temp1);
    Serial.print('\t');
    Serial.print(temp2);
    Serial.print('\t');
    Serial.print(temp3);
    Serial.print('\t');
    Serial.print(temp4);
    Serial.print('\t');
    Serial.print(temp5);
    Serial.print('\t');
    Serial.print(temp6);
    Serial.print('\t');
    Serial.print(accel_x);
    Serial.print('\t');
    Serial.print(accel_y);
    Serial.print('\t');
    Serial.print(accel_z);
    Serial.print('\t');
    Serial.print(gyro_x);
    Serial.print('\t');
    Serial.print(gyro_y);
    Serial.print('\t');
    Serial.print(gyro_z);
    Serial.print('\t');
}
}
digitalWrite(LED_BUILTIN, LOW);
Serial.print("Disconnected from central: ");
Serial.println(central.address());
}


void bluetooth()
{
if (!BLE.begin()) {
Serial.println("BLE failed to Initiate");
delay(500);
while (1);
}
BLE.setLocalName("Indigo");
BLE.setAdvertisedService(customService2);
BLE.setAdvertisedService(customService3);
customService2.addCharacteristic(customtemp1Char);
customService2.addCharacteristic(customtemp2Char);
customService2.addCharacteristic(customtemp3Char);
customService2.addCharacteristic(customtemp4Char);
customService2.addCharacteristic(customtemp5Char);
customService2.addCharacteristic(customtemp6Char);
customService3.addCharacteristic(customaccel_x);
customService3.addCharacteristic(customaccel_y);
customService3.addCharacteristic(customaccel_z);
customService3.addCharacteristic(customgyro_x);
customService3.addCharacteristic(customgyro_y);
customService3.addCharacteristic(customgyro_z);

BLE.addService(customService2);
BLE.addService(customService3);
customtemp1Char.writeValue(temp1);
customtemp2Char.writeValue(temp2);
customtemp3Char.writeValue(temp3);
customtemp4Char.writeValue(temp4);
customtemp5Char.writeValue(temp5);
customtemp6Char.writeValue(temp6);
customaccel_x.writeValue(accel_x);
customaccel_y.writeValue(accel_y);
customaccel_z.writeValue(accel_z);
customgyro_x.writeValue(gyro_x);
customgyro_y.writeValue(gyro_y);
customgyro_z.writeValue(gyro_z);

BLE.advertise();
}

void read_temp()
{
int temp1Pin = 0;
int temp2Pin = 1;
int temp3Pin = 2;
int temp4Pin = 3;
int temp5Pin = 4;
int temp6Pin = 5;
temp1 = analogRead(temp1Pin) ;
temp2 = analogRead(temp2Pin) ;
temp3 = analogRead(temp3Pin) ;
temp4 = analogRead(temp4Pin) ;
temp5 = analogRead(temp5Pin) ;
temp6 = analogRead(temp6Pin) ;
}
void read_IMU() {


  if (IMU.accelerationAvailable()) {
    IMU.readAcceleration(accel_x,accel_y,accel_z);
  }
   if (IMU.gyroscopeAvailable()) {
    IMU.readGyroscope(gyro_x, gyro_y, gyro_z);
  }
}
