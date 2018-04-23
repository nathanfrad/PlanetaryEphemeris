package Calcul;

/* Calcul de la positions des planetes  selon le lieu d'observation, en  partant des valeurs (RA, DEC) calculer dans la class GreenwishPosition 
 *  Les calculs des éphémérides des planetes ci-dessous prend en compte l'abberation,  la nutation et la position local d'observation   */
public class LocalPosition extends GreenwichPosition {


	public LocalPosition(String demande ,double longitude_Ville, double latitude_Ville, double hauteur_Ville) {
		super(demande);

		/* position de la planete a greenwish */
		double ra_deg = RA_deg;
		/* position de la planete a greenwish */
		double dec_deg = DEC_deg;
		/*
		 * distance de la planete par rapport a greenwish qui ne change pas dans la
		 * class GreenwichPosition
		 */
		double R_ua = R1;
		double JD = JDD;

		/* parallaxe horizontal equatorial du corp */
		double pi = Parallaxe_horizontal(R_ua);

		/* chapitre 7 angle horaire geocentreique (h) */
		double TSM = Ang_Horaire_Geo( JD_5 , JD);
		TSM = VerifDegres(TSM);

		double tparallaxe = tSideral(JD);

		/* longitude geocentrique moyenne du soleil ( L0) */
		double L0 = L0(JD);
		L0 = degres_radian(L0);

		/* anomalie moyenne du soleil (M) */
		double M = M(tparallaxe);
		M = degres_radian(M);

		/* LongitudeMoyenneLune */
		double LLune = LLune(tparallaxe);
		LLune = degres_radian(LLune);

		/* anomalie moyenne de la lune */
		double MLune = MLune(tparallaxe);
		MLune = degres_radian(MLune);

		/* longitude moyenne du noeud ascendant de la lune */
		double omega = Omega(tparallaxe);
		omega = degres_radian(omega);

		/* nutation de la longitude */
		double nutation_longitude = nutation_longitude(tparallaxe, omega, L0, LLune, M, MLune);

		/* nutation de la latitude */
		double nutation_latitude = nutation_latitude(tparallaxe, omega, L0, LLune, M, MLune);

		double ObliquitM = ObliquitM(tparallaxe);

		/* obliquité vrai de lecliptique */
		double ObliquitVrai = ObliquitVrai(ObliquitM, nutation_latitude);

		TSM = TSMapparent(nutation_longitude, ObliquitVrai, TSM);

		/* H angle horaire geocentrique */
		double H = H(longitude_Ville, ra_deg, TSM);
		double H_rad = degres_radian(H);

		double a = 6378.1370; // rayon équatorial
		double b = 6356.7523; // rayon polaire
		double tan_u = (b / a) * Math.tan(latitude_Ville * Math.PI / 180);
		double u = Math.atan(tan_u); // radian

		/* Coordonnées équatoriales pour l'équinoxe */
		System.out.println("Coordonnées équatoriales pour l'équinoxe LocalPosition :");

		double ra2 = Ra_Parallaxe(b, tan_u, u, hauteur_Ville, latitude_Ville, pi, H_rad, dec_deg);
		double ra2_deg = radian_degres(ra2);

		double ra_position = ra_deg + ra2_deg;

		double dec2 = Dec_Parallaxe(b, tan_u, u, hauteur_Ville, latitude_Ville, pi, H_rad, dec_deg, ra2);
		double dec2_deg = radian_degres(ra2);

		double dec_position = dec_deg + dec2_deg;

		System.out.println("	Planète " + demande + "  : α = " + ra_position + "°  α heure = " + ra_position * 0.0667
				+ " h  δ = " + dec_position + "° ");

		/* Coordonnées équatoriales en decimal * */
		double RA_num = num(ra_position);

		double DEC_num = num(dec_position);

		/* Coordonnées équatoriales en hexadecimal * */
		System.out.println("Coordonnées équatoriales en hexadecimal :");

		String RA_hexa = hexa(RA_num);

		String DEC_hexa = hexa(DEC_num);

		System.out.println("	Planète " + demande + "en hexadecimal  : α = " + RA_hexa + "  δ = " + DEC_hexa + "° ");

	}

    // num --> hexa
	static String hexa(double num) {
		String hexa = (Integer.toHexString((int) num));
		hexa = hexa.toUpperCase();
		return hexa;
	}

	// degres --> int_num
	static double num(double deg) {

		Double num;
		num = (Math.abs(deg) * 65536 / 360);
		return num;
	}

	/* declinaison local */
	static double Dec_Parallaxe(double b, double a, double u, double hauteur_Ville, double latitude_Ville, double pi,
			double H_rad, double dec_deg, double ra2) {

		double PsinP = PsinP(b, a, u, hauteur_Ville, latitude_Ville);
		double PcosP = PcosP(u, hauteur_Ville, latitude_Ville);
		double tan_dec2_1 = (Math.sin(dec_deg * Math.PI / 180) - PsinP * Math.sin(pi)) * Math.cos(ra2);
		double tan_dec2_2 = (Math.cos(dec_deg * Math.PI / 180) - PcosP * Math.sin(pi) * Math.cos(H_rad));
		double tan_dec2 = tan_dec2_1 / tan_dec2_2;
		double dec2 = Math.atan(tan_dec2);
		return dec2;
	}

	/* Acension droite local */
	static double Ra_Parallaxe(double b, double a, double u, double hauteur_Ville, double latitude_Ville, double pi,
			double H_rad, double dec_deg) {

		double PsinP = PsinP(b, a, u, hauteur_Ville, latitude_Ville);
		double PcosP = PcosP(u, hauteur_Ville, latitude_Ville);
		double tan_ra2_1 = (-PcosP * Math.sin(pi) * Math.sin(H_rad));
		double tan_ra2_2 = (Math.cos(dec_deg * Math.PI / 180) - PcosP * Math.sin(pi) * Math.cos(H_rad));
		double tan_ra2 = tan_ra2_1 / tan_ra2_2;
		double ra2 = Math.atan(tan_ra2);
		return ra2;
	}

	static double PsinP(double b, double a, double u, double hauteur_Ville, double latitude_Ville) {
		double PsinP = (b / a) * Math.sin(u) + (hauteur_Ville / 6378140) * Math.sin(latitude_Ville);
		return PsinP;
	}

	static double PcosP(double u, double hauteur_Ville, double latitude_Ville) {
		double PcosP = Math.cos(u) + (hauteur_Ville / 6378140) * Math.cos(latitude_Ville);
		return PcosP;
	}

	/* H angle horaire geocentrique */
	static double H(double longitude_Ville, double ra_deg, double TSM) {
		double H = TSM + longitude_Ville - ra_deg;
		return H;
	}

	static double TSMapparent(double nutation_longitude, double ObliquitVrai, double TSM) {
		double apparent = (nutation_longitude * (Math.cos(ObliquitVrai * Math.PI / 180)) * (180 / Math.PI)) / 15;
		TSM = TSM + apparent;
		return TSM;
	}

	/* obliquité vrai de lecliptique */
	static double ObliquitVrai(double ObliquitM, double nutation_latitude) {
		double ObliquitVrai = ObliquitM + nutation_latitude;
		return ObliquitVrai;
	}

	/* chapitre 7 angle horaire geocentreique (h) degresb */
	static double Ang_Horaire_Geo(double JD_5, double JD) {
		double t = ((JD_5 - 2451545.0) / 36525);
		double TSM = 280.46061837 + 360.98564736629 * (JD - 2451545.0) + 0.000387933 * (t * t) - (t * t * t) / 38710000;
		return TSM;
	}

	/* parallaxe horizontal equatorial du corp (pi) */
	static double Parallaxe_horizontal(double R_ua) {
		double sin_pi = Math.sin(8.794 / 206264.8062471) / R_ua;
		double pi = Math.asin(sin_pi);
		return pi;
	}

}
