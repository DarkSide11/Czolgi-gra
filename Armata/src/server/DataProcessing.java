package server;

public class DataProcessing {

	// wyluskuje okreslony parametr z lancucha parametrow
	// parametry oddzielone sa dwukropkami - np power 10, angle 15, x =30... -> 10:15:30
	// parametry numerowane s¹ od jedynki
	// Kolejnosc: power, angle, x, y
	public static  synchronized String parseMoveData (int numberOfParameter, String input) {
		String[] parameters = input.split(":"); 
		return parameters[numberOfParameter-1];
	}
	
	
}
//
//enum Parameters {
//
//	POWER(1), 
//	ANGLE(2),
//	X_POS(3),
//	Y_POS(4);
//	
//	private final int value;
//    
//	Parameters(int value) { 
//		this.value = value; 
//		}
//    
//	public int getValue() {
//		return value; 
//		}
//	
//	
//}
