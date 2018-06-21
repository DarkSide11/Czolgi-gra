package client;
/**
 * Klasa zawierajaca metody przetwarzajace strumien danych.
 */
public class DataProcessing {

	/**
	 * Metoda wy�uskuj�ca przekazywane przez klienta argumenty.
	 * Wejsciowy strumien zawiera wyslane przez klienta parametry ruchu gracza, oddzielone dwukropkiem ":"
	 * np. dla strza�u:  power 10, angle 15, x =30... - > 10:15:30
	 * przekazywane parametry numerowane s� od jedynki
	 * Na potrzeby przekazywania parametrow strzalu przyjeto kolejnosc: power, angle, x, y
	 * 
	 * @param numberOfParameter - numer parametru kt�ry zwraca metoda
	 * @param input - wejsciowy �a�cuch znak�w
	 * @return - zwracany parametr.
	 */
	public static  synchronized String parseMoveData (int numberOfParameter, String input) {
		String[] parameters = input.split(":"); 
		return parameters[numberOfParameter-1];
	}
	
	
}