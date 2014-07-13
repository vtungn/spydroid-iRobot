package net.majorkernelpanic.spydroid.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class iRobotController {
	
	public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	// MAC address of FireFly
	public static String iRobotaddress = "00:06:66:0A:AB:27";
	private static final String TAG = "iRobotController";
	public OutputStream outStream = null;
	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	
	public iRobotController() {
		// TODO Auto-generated constructor stub
		btAdapter = BluetoothAdapter.getDefaultAdapter();
//		if(!btAdapter.isEnabled()){
//			Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//			startActivityForResult(turnOn, 0);
//		}
		//connect to device iRobot
//		setupbluetoothConnect();
		
		Log.e(TAG, "+++DONE CREATE +++");
	}
	// bluetooth connect/reconnect device
	public void setupbluetoothConnect() {
    	BluetoothDevice device = btAdapter.getRemoteDevice(iRobotaddress);
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
        	Log.e(TAG, "ON RESUME: Socket creation failed.", e);
        }
        //blocking connect
        btAdapter.cancelDiscovery();
        
        Log.d(TAG, "+ ON RESUME : connect socket +");
        try {
            btSocket.connect();
            Log.e(TAG, "ON RESUME: BT connection established, data transfer link open.");
        } catch (IOException e) {
        	try {
        		btSocket.close();
        	} catch (IOException e2) {
        		Log.e(TAG, "ON RESUME: Unable to close socket during connection failure", e2);
        	}
        }
        
        //say something to server
        Log.d(TAG, "+ DONE RESUME ready to say sth +");
        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "ON SEND MSG: Output stream creation failed.", e);
        }
    }
	public void sendCommandtoiRobot(char msg){
		try {
            outStream.write(msg);
        } catch (IOException e) {
            Log.e(TAG, "ON SEND MSGE: Exception during write.", e);
        }
	}
	public void setOutputStream(OutputStream outputStream){
		outStream = outputStream;
	}
	public void drive(String moveCode,char velocity, char rotatespeed) {
		sendCommandtoiRobot((char)137);
		
		if (moveCode == "Forward")
			rotatespeed= (char) 32768;
		else if(moveCode == "Rotate")
			rotatespeed = (char)1;
		else if(moveCode == "Stop"){
			rotatespeed = (char)32767;
			velocity = (char)0;
		}
		sendCommandtoiRobot(toHighBytes(velocity));
		sendCommandtoiRobot(toLowBytes(velocity));
		sendCommandtoiRobot(toHighBytes(rotatespeed));
		sendCommandtoiRobot(toLowBytes(rotatespeed)); 
	}
	
	char toHighBytes(char value ){
//	    """ returns two bytes (ints) in high, low order
//	    whose bits form the input value when interpreted in
//	    two's complement
//	    """
		char eqBitVal;
//	    # if positive or zero, it's OK
	    if (value >= 0)
	        eqBitVal = value;
//	    # if it's negative, I think it is this
	    else
	        eqBitVal = (char) ((1<<16) + value);
	    char charreturn = (char)( (eqBitVal >> 8) & 0xFF );
	    return charreturn;
	}
	char toLowBytes(char value ){
//	    """ returns two bytes (ints) in high, low order
//	    whose bits form the input value when interpreted in
//	    two's complement
//	    """
		char eqBitVal;
//	    # if positive or zero, it's OK
	    if (value >= 0)
	        eqBitVal = value;
//	    # if it's negative, I think it is this
	    else
	        eqBitVal = (char) ((1<<16) + value);
		char charreturn = (char)( eqBitVal & 0xFF  );
	    return charreturn;
	    //return ( (eqBitVal >> 8) & 0xFF, eqBitVal & 0xFF )
	}
	public void getControlDev() {
		Log.d(TAG, "...Data to send: " + "128" + "...");	
		Log.d(TAG, "+ send msg +");
        
        
//        String message = "abc";
        char charmsg = 128;
        sendCommandtoiRobot(charmsg);
        charmsg = 132;
        sendCommandtoiRobot(charmsg);
        
		
	}
}
