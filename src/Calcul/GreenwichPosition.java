package Calcul;

import PlanetaryData.*;

/* Calcul la position des planetes en partant de la longitude , latitude Radius vector fourni par les class du package : PlanetaryData  
 * Les calculs des éphémérides des planetes ci-dessous prend en compte l'abberation et la nutation   */
public class GreenwichPosition extends JD {

	protected String demande;

	protected double RA_deg;
	protected double DEC_deg;

	protected double L;
	protected double B;
	protected double R;

	protected double L1;
	protected double B1;
	protected double R1;

	protected double tTL;

	public GreenwichPosition(String demande) {
		super();
		/* On affiche les resultats de class que nous étendons : JD.java */
		System.out.println("Julian day : " + JDD);
		System.out.println("t : " + t);

		/*
		 * On recupere le tableau contenant la longitude (rad), la latitude(rad) et la
		 * distance terre-planete (au)
		 */
		double[] tabLBR = Demande(demande, t, JDD);
		L = tabLBR[0];
		B = tabLBR[1];
		R = tabLBR[2];

		/* Coordonnées écliptiques héliocentriques de la planete en deg */
		System.out.println("Coordonnées écliptiques héliocentriques :");
		L = radian_degres(L);
		L = VerifDegres(L);

		B = radian_degres(B);

		/*
		 * Si la planete en question est la Lune on ne transforme pas les degres negatif
		 * en superieur
		 */
		if (demande != "Moon") {
			B = VerifDegres(B);
		}

		System.out.println("	Planète " + demande + "  : λ = " + L + "°  β = " + B + "°  ρ = " + R + " ua ");

		/*
		 * on initialise les variables , c'est utile car si la demande = Moon alors la
		 * condition suivante n'est pas exécuté
		 */

		double longitude_deg = L;
		double latitude_deg = B;
		R1 = R;
		/*
		 * Si la planete n'est pas la Lune, alors on calcul le temps que la lumière
		 * prend pour atteindre la planete selon sa distance par rapport à la terre
		 */
		if (demande != "Moon") {

			/* Coordonnées écliptiques heliocentrique de planete en rad */
			L = degres_radian(L);
			B = degres_radian(B);

			/* Coordonnées écliptiques héliocentriques de la terre en deg */
			String Earth = "Earth";
			double[] tabEarth = Demande(Earth, t, JDD);
			double Lo = tabEarth[0];
			double Bo = tabEarth[1];
			double Ro = tabEarth[2];

			Lo = radian_degres(Lo);
			Lo = VerifDegres(Lo);

			Bo = radian_degres(Bo);
			Bo = VerifDegres(Bo);

			System.out.println("	Planète Terre :  λo = " + Lo + "°  βo = " + Bo + "°  ρo = " + Ro + " ua ");

			/* Coordonnées écliptiques heliocentrique de la terre en rad */
			Lo = degres_radian(Lo);
			Bo = degres_radian(Bo);

			/* Coordonnées cartésiennes heliocentrique de la planete */
			System.out.println("Coordonnées cartésiennes héliocentriques :");

			/* Calcul */
			double X = X(L, B, R, Lo, Bo, Ro);
			double Y = Y(L, B, R, Lo, Bo, Ro);
			double Z = Z(B, R, Bo, Ro);

			System.out.println("	Planète " + demande + " :  X = " + X + "  Y = " + Y + "  Z = " + Z);

			/* Vrai distance terre - planete */
			double VraiDis = VraiDis(X, Y, Z);

			/* Temps lumière (T) */
			double TempLum = TempLum(VraiDis);
			// System.out.println("Temps lumiere : " + TempLum);

			/*
			 * Coordonnées écliptiques héliocentriques de la planete pour l'instant t-T
			 * (aberration) en deg
			 */
			System.out.println("Coordonnées écliptiques héliocentriques pour l'instant t-T (aberration) :");

			tTL = (((JDD - TempLum) - 2451545) / 365250);

			/*
			 * On recupere le tableau contenant la longitude (rad), la latitude(rad) et la
			 * distance terre-planete (au)
			 */
			double[] tabLBR2 = Demande(demande, tTL, JDD);
			L1 = tabLBR2[0];
			B1 = tabLBR2[1];
			R1 = tabLBR2[2];

			L1 = radian_degres(L1);
			L1 = VerifDegres(L1);

			B1 = radian_degres(B1);
			B1 = VerifDegres(B1);

			System.out.println("	Planète " + demande + "  : λ1 = " + L1 + "°  β1 = " + B1 + "°  ρ1 = " + R1 + "° ");

			/*
			 * Coordonnées écliptiques héliocentriques de la planete pour l'instant t-T
			 * (aberration) en rad
			 */
			L1 = degres_radian(L1);
			B1 = degres_radian(B1);

			/* Coordonnées cartésiennes heliocentrique de la planete */
			System.out.println("Coordonnées cartésiennes héliocentriques pour l'instant t-T (aberration)  :");

			double X1 = X(L1, B1, R1, Lo, Bo, Ro);
			double Y1 = Y(L1, B1, R1, Lo, Bo, Ro);
			double Z1 = Z(B1, R1, Bo, Ro);

			System.out.println("	Planète " + demande + " :  X1 = " + X1 + "  Y1 = " + Y1 + "  Z1 = " + Z1);

			/* Vrai distance terre - venus ( avec t-T ) */
			double VraiDis1 = VraiDis(X1, Y1, Z1);

			/* Temps lumière (T) ( avec t-T ) */
			double TempLum1 = TempLum(VraiDis1);

			/*
			 * Coordonnées écliptiques héliocentriques de la planete pour l'instantt-T en
			 * deg
			 */

			System.out.println(" Coordonnées écliptiques héliocentriques pour l'instant t-T  :");
			/* calcul longitude de la planete */
			double longitude = Math.atan2(Y1, X1);
			longitude_deg = radian_degres(longitude);
			longitude_deg = VerifDegres(longitude_deg);

			/* calcul latitude de la planete */
			double tan_latitude = Z1 / (Math.sqrt((X1 * X1) + (Y1 * Y1)));
			double latitude = Math.atan(tan_latitude);
			latitude_deg = radian_degres(latitude);
			latitude_deg = VerifDegres(latitude_deg);

		}
		/* fin de la condition */

		System.out.println("	Planète " + demande + "  : λ1 = " + longitude_deg + "°  β1 = " + latitude_deg
				+ "°  ρ1 = " + R1 + "° ");

		double tparallaxe = tparallaxe(JDD); // t est le t de base

		/* ******** l'exentricité de le l'orbite de la terre (e) ********** */
		double e = exentricite(tparallaxe);

		/* ******** longitude du perihelie ( pi ) ********** */
		double pi = pi(tparallaxe);

		double piRad = degres_radian(pi);

		/* longitude geocentrique moyenne du soleil ( LO) */
		double L0 = L0(tparallaxe);

		/* anomalie moyenne du soleil (M) */
		double M = M(tparallaxe);

		/* centre du soleil (C) */
		double C = CentreSoleil(tparallaxe, M);

		/* ******** Longitude Vrai du Soleil ********** */
		double LongVraiSoleil = LongVraiSoleil(L0, C);
		LongVraiSoleil = VerifDegres(LongVraiSoleil);
		double LongVraiSoleilRad = degres_radian(LongVraiSoleil);

		/* constante de l'abberation */
		double K = K(L0, C);
		K = degres_radian(K);

		/*
		 * Coordonnées écliptiques héliocentriques de la planete pour l'instant t-T +
		 * aberration en deg
		 */
		System.out.println("Coordonnées écliptiques héliocentriques pour l'instant t-T + abberation  :");
		double Aberration_Longitude = Aberration_Longitude(K, LongVraiSoleilRad, longitude_deg, LongVraiSoleilRad,
				piRad, latitude_deg);
		double Aberration_Longitude_deg = radian_degres(Aberration_Longitude);

		double Aberration_Latitude = Aberration_Latitude(K, LongVraiSoleilRad, longitude_deg, LongVraiSoleilRad, piRad,
				latitude_deg);
		double Aberration_Latitude_deg = radian_degres(Aberration_Latitude);

		/* Longitude et latitude moins l'Aberration */
		double longitude_aberration = longitude_deg - Aberration_Longitude_deg;
		double latitude_aberration = latitude_deg - Aberration_Latitude_deg;

		System.out.println("	Planète " + demande + "  : λ1 = " + longitude_aberration + "°  β1 = "
				+ latitude_aberration + "°  ρ1 = " + R1 + "° ");

		/* *********** LongitudeMoyenneSoleil = L0 ******************/
		L0 = degres_radian(L0);
		/* *********** LongitudeMoyenneLune ***************** */
		double LLune = LLune(tparallaxe);
		LLune = degres_radian(LLune);
		/* *********** anomalie moyenne du soleil = M ***************** */
		M = degres_radian(M);
		/* *********** anomalie moyenne de la lune ***************** */
		double MLune = MLune(tparallaxe);
		MLune = degres_radian(MLune);

		/* *********** anomalie moyenne de la lune ***************** */
		double omega = Omega(tparallaxe);
		omega = degres_radian(omega);

		/*
		 * Coordonnées écliptiques héliocentriques de la planete pour l'instant t-T +
		 * aberration en deg
		 */
		System.out.println("Coordonnées écliptiques héliocentriques pour l'instant t-T + abberation + nutation  :");

		/* nutation de la longitude */
		double nutation_longitude = nutation_longitude(tparallaxe, omega, L0, LLune, M, MLune);

		/* nutation de la latitude */
		double nutation_latitude = nutation_latitude(tparallaxe, omega, L0, LLune, M, MLune);

		/* longitude corrigé pour la nutation */
		double Longitude_nutation = longitude_aberration + nutation_longitude;

		System.out.println("	Planète " + demande + "  : λ1 = " + Longitude_nutation + "°  β1 = "
				+ latitude_aberration + "°  ρ1 = " + R1 + "° ");

		/* obliquité de l'écliptique */
		double E = degres_radian(23.4392911);

		/* Conversion de Longitude_nutation en radian */
		Longitude_nutation = degres_radian(Longitude_nutation);
		/* Conversion de latitude_aberration en radian */
		latitude_aberration = degres_radian(latitude_aberration);

		/* équatoriales pour l'équinoxe : */
		System.out.println("Coordonnées équatoriales moyennes pour l'équinoxe greenwichPosition :");

		/* Calcul de l'Ascension droite */
		double ra_rad = Ra(Longitude_nutation, latitude_aberration, E);
		RA_deg = radian_degres(ra_rad);
		RA_deg = VerifDegres(RA_deg);

		/* Calcul de la Déclinaison */
		double dec_rad = Dec(Longitude_nutation, latitude_aberration, E);
		DEC_deg = radian_degres(dec_rad);

		System.out.println("	Planète " + demande + "  : α = " + RA_deg + "°  α heure = " + RA_deg * 0.0667
				+ " h  δ = " + DEC_deg + "° ");

	}

	/* Recuperation des valeurs ( longitude, latitude , radius vecteur ) */
	static double[] Demande(String demande, double t, double JDD) {
		double L = 0;
		double B = 0;
		double R = 0;
		switch (demande) {
		case "Uranus":
			Uranus uranus = new Uranus();
			L = uranus.Luranus(t);
			B = uranus.Buranus(t);
			R = uranus.Ruranus(t);
			break;
		case "Venus":

			Venus venus = new Venus();
			L = venus.Lvenus(t);
			B = venus.Bvenus(t);
			R = venus.Rvenus(t);

			break;
		case "Mercury":

			Mercury mercury = new Mercury();
			L = mercury.Lmercury(t);
			B = mercury.Bmercury(t);
			R = mercury.Rmercury(t);

			break;
		case "Jupiter":

			Jupiter jupiter = new Jupiter();
			L = jupiter.Ljupiter(t);
			B = jupiter.Bjupiter(t);
			R = jupiter.Rjupiter(t);

			break;
		case "Mars":

			Mars mars = new Mars();
			L = mars.Lmars(t);
			B = mars.Bmars(t);
			R = mars.Rmars(t);

			break;
		case "Neptune":

			Neptune neptune = new Neptune();
			L = neptune.Lneptune(t);
			B = neptune.Bneptune(t);
			R = neptune.Rneptune(t);

			break;
		case "Saturn":
			Saturn saturn = new Saturn();
			L = saturn.Lsaturn(t);
			B = saturn.Bsaturn(t);
			R = saturn.Rsaturn(t);

			break;
		case "Earth":

			Earth earth = new Earth();
			L = earth.Learth(t);
			B = earth.Bearth(t);
			R = earth.Rearth(t);

			break;
		case "Moon":
			Moon moon = new Moon();
			double[] tabLBRmoon = moon.LBR(JDD);
			L = tabLBRmoon[0];
			B = tabLBRmoon[1];
			R = tabLBRmoon[2];

			break;

		default:
			break;

		}

		double[] tab;
		tab = new double[3];
		tab[0] = L;
		tab[1] = B;
		tab[2] = R;

		return tab;

	}

	/* obliquité moyenne ( qui est non utilisé dans mon cas ) */
	static double ObliquitM(double t) { // tparallaxe
		double ObliquitM = (23 + 26 / 60 + 21.448 / 3600) - (46.8150 / 3600) * t - (0.00059 / 3600) * (t * t)
				+ (0.001813 / 3600) * (t * t * t);
		return ObliquitM;
	}

	/* Temps universel */
	static double tparallaxe(double JD) {
		double t = ((JD - 2451545.0) / 36525);
		return t;
	}

	/* longitude geocentrique moyenne du soleil ( LO) */
	static double L0(double t) {
		double L0 = 280.46646 + 36000.76983 * t + 0.0003032 * (t * t);
		return L0;
	}

	/* anomalie moyenne du soleil (M) */
	static double M(double t) {
		double M = 357.52911 + 35999.05029 * t - 0.0001537 * (t * t);
		return M;
	}

	/* *********** nutation de la longitude ***************** */
	static double nutation_longitude(double t, double omega, double L0, double LLune, double M, double MLune) {
		double nutation_longitude = 0;
		nutation_longitude += -(17.1996 + 0.01742 * t) * Math.sin(omega);
		nutation_longitude += -(1.3187 + 0.00016 * t) * Math.sin(2 * L0);
		nutation_longitude += -0.2274 * Math.sin(2 * LLune);
		nutation_longitude += +0.2062 * Math.sin(2 * omega);
		nutation_longitude += +(0.1426 - 0.00034 * t) * Math.sin(M);
		nutation_longitude += +0.0712 * Math.sin(MLune);
		nutation_longitude += -(0.0517 - 0.00012 * t) * Math.sin(2 * L0 + M);
		nutation_longitude += -0.0386 * Math.sin(2 * LLune - omega);
		nutation_longitude += -0.0301 * Math.sin(2 * LLune + MLune);
		nutation_longitude += +0.0217 * Math.sin(2 * L0 - M);
		nutation_longitude += -0.0158 * Math.sin(2 * L0 - 2 * LLune + MLune);
		nutation_longitude += +0.0129 * Math.sin(2 * L0 - omega);
		nutation_longitude += +0.0123 * Math.sin(2 * LLune - MLune);
		nutation_longitude = nutation_longitude / 3600;
		return nutation_longitude;
	}

	/* nutation de la latitude */
	static double nutation_latitude(double t, double omega, double L0, double LLune, double M, double MLune) {
		double nutation_latitude = 0;
		nutation_latitude += +(9.2025 + 0.00089 * t) * Math.cos(omega);
		nutation_latitude += +(0.5736 - 0.00031 * t) * Math.cos(2 * L0);
		nutation_latitude += +0.0977 * Math.cos(2 * LLune);
		nutation_latitude += -0.0895 * Math.cos(2 * omega);
		nutation_latitude += +0.0224 * Math.cos(2 * L0 + M);
		nutation_latitude += +0.0200 * Math.cos(2 * LLune - omega);
		nutation_latitude += +0.0129 * Math.cos(2 * LLune + MLune);
		nutation_latitude += -0.0095 * Math.cos(2 * L0 - M);
		nutation_latitude += -0.0070 * Math.cos(2 * L0 - omega);
		return nutation_latitude;
	}
	/* anomalie moyenne de la lune */

	static double MLune(double t) {
		double MLune = (134.9634 + 477198.8675 * t + 0.008721 * (t * t));
		return MLune;
	}

	/* longitude moyenne du noeud ascendant de la lune */
	static double Omega(double t) {
		double omega = (125.0443 - 1934.1363 * t + 0.002075 * (t * t));
		return omega;
	}

	/* LongitudeMoyenneLune */
	static double LLune(double t) {
		double LLune = (218.3164 + 481267.8812 * t + 0.001599 * (t * t));
		return LLune;
	}

	/* Calcul de la Déclinaison */
	static double Dec(double Longitude_nutation, double latitude, double E) {
		double sin_dec = ((Math.sin(E)) * Math.sin(Longitude_nutation) * Math.cos(latitude)
				+ (Math.cos(E) * Math.sin(latitude)));
		double dec_rad = Math.asin(sin_dec);
		return dec_rad;
	}

	/* Calcul de l'Ascension droite */
	static double Ra(double Longitude_nutation, double latitude, double E) {
		double F;
		F = Math.cos(Longitude_nutation) * Math.cos(latitude);
		double G;
		G = Math.cos(E) * Math.sin(Longitude_nutation) * Math.cos(latitude) - Math.sin(E) * Math.sin(latitude);
		double ra_rad = Math.atan2(G, F); // ATTENTION à l'ordre : G,F
		return ra_rad;

	}

	/* Calcul Aberration */
	static double Aberration_Longitude(double K, double LongVraiSoleilRad, double longitude, double e, double piRad,
			double latitude) {
		double Aberration_Longitude = (-K * Math.cos(LongVraiSoleilRad - longitude)
				+ e * K * Math.cos(piRad - longitude)) / Math.cos(latitude);
		return Aberration_Longitude;
	}

	static double Aberration_Latitude(double K, double LongVraiSoleilRad, double longitude, double e, double piRad,
			double latitude) {
		double Aberration_Latitude = -K * Math.sin(latitude)
				* (Math.sin(LongVraiSoleilRad - longitude) - e * Math.sin(piRad - longitude));
		return Aberration_Latitude;
	}

	/* Longitude Vrai du Soleil */

	static double LongVraiSoleil(double L0, double C) {
		double LongVraiSoleil = (L0 + C);
		return LongVraiSoleil;
	}

	/* constante de l'abberation */

	static double K(double L0, double C) {
		double K = (20.49552 / 3600);
		return K;
	}

	/* centre du soleil (C) */
	static double CentreSoleil(double t, double M) {
		double C = (1.914602 - 0.004817 * t - 0.000014 * (t * t)) * Math.sin(M)
				+ (0.019993 - 0.000101 * t) * Math.sin(2 * M) + 0.000289 * Math.sin(3 * M);
		return C;
	}

	/* l'exentricité de le l'orbite de la terre (e) */
	static double exentricite(double t) {
		double e = 0.016708634 - 0.000042037 * t - 0.0000001267 * (t * t);
		return e;
	}

	/* longitude du perihelie ( pi ) */
	static double pi(double t) {
		double pi = 102.93735 + 1.71947 * t + 0.00046 * (t * t);
		return pi;
	}

	/* Vrai distance terre - venus */

	static double VraiDis(double X, double Y, double Z) {
		double VraiDis = Math.sqrt((X * X) + (Y * Y) + (Z * Z));
		return VraiDis;
	}

	/* Temps lumière ( T) */
	static double TempLum(double VraiDis) {
		double TempLum = 0.0057755183 * VraiDis;
		return TempLum;
	}

	/* calcul Coordonnées cartésiennes */

	static double X(double L, double B, double R, double Lo, double Bo, double Ro) {
		double X = (R * Math.cos(B) * Math.cos(L)) - (Ro * Math.cos(Bo) * Math.cos(Lo));
		return X;
	}

	static double Y(double L, double B, double R, double Lo, double Bo, double Ro) {
		double Y = (R * Math.cos(B) * Math.sin(L)) - (Ro * Math.cos(Bo) * Math.sin(Lo));
		return Y;
	}

	static double Z(double B, double R, double Bo, double Ro) {
		double Z = (R * Math.sin(B)) - (Ro * Math.sin(Bo));
		return Z;
	}

	/* conversion radian en degres */
	static double radian_degres(double radian) {
		double degres;
		degres = radian * (180 / Math.PI);
		return degres;
	}

	/* conversion degres en radian */
	static double degres_radian(double degres) {
		double radian;
		radian = degres * Math.PI / 180;
		return radian;
	}

	/* Verification des angles en degres */
	static double VerifDegres(double Deg) {
		if (Deg < 0) {
			Deg = Deg + (360 * Math.floor(Deg / 360) * -1);
		} else if (Deg > 360) {
			Deg = Deg - (360 * Math.floor(Deg / 360));
		}
		return Deg;
	}
}
