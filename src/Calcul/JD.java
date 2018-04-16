package Calcul;

import java.util.Calendar;

/* Calcul du jour Julien, indispensable pour le calcul des ephemerides des planetes qui en suit */

public class JD {

	protected double JDD;
	protected double t;

	public JD() {
		Calendar c = Calendar.getInstance();
		double year = c.get(Calendar.YEAR);
		double month = c.get(Calendar.MONTH) + 1;

		double hour = c.get(Calendar.HOUR);
		double minutes = (double) c.get(Calendar.MINUTE);
		double second = (double) c.get(Calendar.SECOND);

		double hourmodif = ((double) (hour) / (double) (24));
		double minutesmodif = ((double) (minutes) / (double) (1440));
		double secondemodif = ((double) (second) / (double) (86400));

		double day = c.get(Calendar.DAY_OF_MONTH) + hourmodif + minutesmodif + secondemodif; 
																								

		if (month == 1 || month == 2) {
			year = year - 1;
			month = month + 12;
		}

		double A = Math.floor(year / 100);
		double B = 2 - A + Math.floor(A / 4);
		JDD =  Math.floor(365.25 * (year + 4716)) + Math.floor(30.6001 * (month + 1)) + day + B - 1524.5;
		t = t(JDD);

	}

	/* Temps dynamique */
	public static double t(double JD) {
		double t = ((JD - 2451545.0) / 365250);
		return t;
	}

}
