package Calcul;

public class Main {

	public static void main(String[] arguments) {

		double longitude_Ville = 0.340375; //Longitude geographique de Poitiers en degres decimaux
		double latitude_Ville = 46.580224; // Latitude geographique de Poitiers en degres decimaux
		double hauteur_Ville = 65; // Hauteur(altitude) de poitiers en mètres
		String demande = "Venus";
		LocalPosition local = new LocalPosition(demande, longitude_Ville, latitude_Ville, hauteur_Ville);
		// GreenwichPosition local = new GreenwichPosition(demande);

		//Alzimute alz  = new Alzimute(demande, longitude_Ville, latitude_Ville, hauteur_Ville);
	}







}
