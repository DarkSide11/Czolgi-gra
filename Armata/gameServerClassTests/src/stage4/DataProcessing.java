package src.stage4;

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

