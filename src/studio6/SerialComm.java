package studio6;

import jssc.*;

public class SerialComm {

	SerialPort port;
	boolean a;

	private boolean debug;  // Indicator of "debugging mode"
	
	// This function can be called to enable or disable "debugging mode"
	void setDebug(boolean mode) {
		debug = mode;
	}	
	

	// Constructor for the SerialComm class
	public SerialComm(String name) throws SerialPortException {
		port = new SerialPort(name);		
		port.openPort();
		port.setParams(SerialPort.BAUDRATE_9600,
			SerialPort.DATABITS_8,
			SerialPort.STOPBITS_1,
			SerialPort.PARITY_NONE);
		
		debug = false; // Default is to NOT be in debug mode
	}
		
	// TODO: Add writeByte() method from Studio 5
	
	public boolean writeByte(byte b){
		if (debug) {
			System.out.println(Integer.toHexString(b & 0xFF));
			try {
				a = port.writeByte(b);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			
			try {
				a = port.writeByte(b);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(a);
		}
		
		return a;
	}
	
	// TODO: Add available() method
	
	public boolean available() {
		int bytes = 0;
		try {
			bytes = port.getInputBufferBytesCount();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bytes > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// TODO: Add readByte() method	
	
	public byte readByte() {
		byte[] readings = new byte[1];
		try {
		readings = port.readBytes(1);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (debug) {
			System.out.println(String.format("%02x", readings[0]));
		}
		return readings[0];
	}
	
	// TODO: Add a main() method
	public static void main(String[] args) {
		SerialComm comm = null;
		try {
			comm = new SerialComm("/dev/cu.usbserial-DN02B7OZ");
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comm.setDebug(false);
		
		while(true) {
			boolean isAvailable = comm.available();
			if (isAvailable) {
				byte b = comm.readByte();
				if (!comm.debug) {
					System.out.print((char) b);
				}
			}
		}
	}
}
