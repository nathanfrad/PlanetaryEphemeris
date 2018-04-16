package PlanetaryData;

public class Moon {

	public static double[] LBR(double JD) {

		double tmoon = ((JD - 2451545) / 36525); // attention a 36525
		System.out.println(tmoon);

		/* Arguments */
		double A1 = 119.75 + 131.849 * tmoon;
		A1 = VerifDegres(A1);
		System.out.println("A1 : " + A1);
		A1 = degres_radian(A1);
		double A2 = 53.09 + 479264.290 * tmoon;
		A2 = VerifDegres(A2);
		System.out.println("A2 : " + A2);
		A2 = degres_radian(A2);
		double A3 = 313.45 + 481266.484 * tmoon;
		A3 = VerifDegres(A3);
		System.out.println("A3 : " + A3);
		A3 = degres_radian(A3);

		/* excentricité de l'orbite terrestre */
		double e = 1 - 0.002516 * tmoon - 0.0000074 * (tmoon * tmoon);
		System.out.println(e);
		double e2 = e * e;
		System.out.println("E2 : " + e2);

		/* Longitude moyenne de la lune */
		double LL = VerifDegres(LLunelune(tmoon));
		System.out.println("LLunelune : " + LL);
		LL = degres_radian(LL);

		/* Anomalie moyenne du soleil */
		double M = VerifDegres(M(tmoon));
		System.out.println("M : " + M);
		M = degres_radian(M);

		/* Anomalie moyenne de la lune */
		double ML = VerifDegres(MLune(tmoon)); // ML
		System.out.println("ML : " + ML);
		ML = degres_radian(ML);

		/* Elongation moyenne de la lune */
		double D = VerifDegres(ElongationD(tmoon)); // ElongationD
		System.out.println("ElongationD : " + D);
		D = degres_radian(D);

		/* DIstance moyenne de la lune à son noeud ascendant */
		double F = VerifDegres(F(tmoon)); // F
		System.out.println("F : " + F);
		F = degres_radian(F);

		/* sommes des principaux thermes de la theorie ELP2000 pour la longitude */
		double L1 = L1(ML, D, M, F, e, e2, A1, LL, A2);
		double L2 = radian_degres(LL) + L1 / 1000000;
		System.out.println("L2 (deg): " + L1);

		/* sommes des principaux thermes de la theorie ELP2000 pour la latitude */
		double B1 = B1(ML, D, M, F, e, e2, A1, LL, A2, A3);
		double B2 = B1 / 1000000;
		System.out.println("B2 (deg) : " + B1);

		/*
		 * sommes des principaux thermes de la theorie ELP2000 pour la distance terre -
		 * lune
		 */
		double R1 = R1(ML, D, M, F, e, e2);
		double R2 = 385000.56 + R1 / 1000;
		double R3 = R2 / 149597870.700; // 1 Unité astronomique = 149 597 870,700 kilomètres
		// System.out.println("R1 : " + R1);
		System.out.println("R2 (km) : " + R2);
		System.out.println("R3 (ua) : " + R3);

		/*
		 * Initialisation du tableua des valeurs de la lune pour pouvoir l'utiliser dans
		 * la class GreenwichPosition
		 */
		double[] tab = new double[3];
		tab[0] = degres_radian(L2);
		tab[1] = degres_radian(B2);
		tab[2] = R3;
		;

		return tab;

	}

	/* sommes des principaux thermes de la theorie ELP2000 pour la longitude */
	static double L1(double ML, double D, double M, double F, double e, double e2, double A1, double LL, double A2) {

		double L1 = 6288774 * Math.sin(ML) + 1274027 * Math.sin(2 * D - ML) + 658314 * Math.sin(2 * D)
				+ 213618 * Math.sin(2 * ML) - e * 185116 * Math.sin(M) - 114332 * Math.sin(2 * F)
				+ 58793 * Math.sin(2 * D - 2 * ML) + e * 57066 * Math.sin(2 * D - M - ML) + 53322 * Math.sin(2 * D + ML)
				+ e * 45758 * Math.sin(2 * D - M) - e * 40923 * Math.sin(M - ML) - 34720 * Math.sin(D)
				- e * 30383 * Math.sin(M + ML) + 15327 * Math.sin(2 * D - 2 * F) - 12528 * Math.sin(ML + 2 * F)
				+ 10980 * Math.sin(ML - 2 * F) + 10675 * Math.sin(4 * D - ML) + 10034 * Math.sin(3 * ML)
				+ 8548 * Math.sin(4 * D - 2 * ML) - e * 7888 * Math.sin(2 * D + M - ML) - e * 6766 * Math.sin(2 * D + M)
				- 5163 * Math.sin(D - ML) + e * 4987 * Math.sin(D + M) + e * 4036 * Math.sin(2 * D - M + ML)
				+ 3994 * Math.sin(2 * D + 2 * ML) + 3861 * Math.sin(4 * D) + 3665 * Math.sin(2 * D - 3 * ML)
				- e * 2689 * Math.sin(M - 2 * ML) - 2602 * Math.sin(2 * D - ML + 2 * F)
				+ e * 2390 * Math.sin(2 * D - M - 2 * ML)

				- 2348 * Math.sin(D + ML) + e2 * 2236 * Math.sin(2 * D - 2 * M) - e * 2120 * Math.sin(M + 2 * ML)
				- e2 * 2069 * Math.sin(2 * M) + e2 * 2048 * Math.sin(2 * D - 2 * M - ML)
				- 1773 * Math.sin(2 * D + ML - 2 * F) - 1595 * Math.sin(2 * D + 2 * F)
				+ e * 1215 * Math.sin(4 * D - M - ML) - 1110 * Math.sin(2 * ML + 2 * F) - 892 * Math.sin(3 * D - ML)
				- e * 810 * Math.sin(2 * D + M + ML) + e * 759 * Math.sin(4 * D - M - 2 * M)
				- e2 * 713 * Math.sin(2 * M - ML) - e2 * 700 * Math.sin(2 * D + 2 * M - ML)
				+ e * 691 * Math.sin(2 * D + M - 2 * ML) + e * 596 * Math.sin(2 * D - M - 2 * F)
				+ 549 * Math.sin(4 * D + ML) + 537 * Math.sin(4 * ML) + e * 520 * Math.sin(4 * D - M)
				- 487 * Math.sin(D - 2 * ML) - e * 399 * Math.sin(2 * D + M - 2 * F) - 381 * Math.sin(2 * ML - 2 * F)
				+ e * 351 * Math.sin(D + M + ML) - 340 * Math.sin(3 * D - 2 * ML) + 330 * Math.sin(4 * D - 3 * ML)
				+ e * 327 * Math.sin(2 * D - M + 2 * ML) - e2 * 323 * Math.sin(2 * M + ML)
				+ e * 299 * Math.sin(D + M - ML) + 294 * Math.sin(2 * D + 3 * ML) - 275 * Math.sin(2 * D + ML + 2 * F)

				+ 3958 * Math.sin(A1) + 1962 * Math.sin(LL - F) + 318 * Math.sin(A2);
		return L1;

	}

	static double radian_degres(double radian) {
		double degres;
		degres = radian * (180 / Math.PI);
		return degres;
	}

	/* sommes des principaux thermes de la theorie ELP2000 pour la latitude */
	static double B1(double ML, double D, double M, double F, double e, double e2, double A1, double LL, double A2,
			double A3) {
		double B = 5128122 * Math.sin(F) + 280602 * Math.sin(ML + F) + 277693 * Math.sin(ML - F)
				+ 173237 * Math.sin(2 * D - F) + 55413 * Math.sin(2 * D - ML + F) + 46271 * Math.sin(2 * D - ML - F)
				+ 32573 * Math.sin(2 * D + F) + 17198 * Math.sin(2 * ML + F) + 9266 * Math.sin(2 * D + ML - F)
				+ 8822 * Math.sin(2 * ML - F) + e * 8216 * Math.sin(2 * D - M - F) + 4324 * Math.sin(2 * D - 2 * ML - F)
				+ 4200 * Math.sin(2 * D + ML + F) - e * 3359 * Math.sin(2 * D + M - F)
				+ e * 2463 * Math.sin(2 * D - M - ML + F) + e * 2211 * Math.sin(2 * D - M + F)
				+ e * 2065 * Math.sin(2 * D - M - ML - F) - e * 1870 * Math.sin(M - ML - F)
				+ 1828 * Math.sin(4 * D - ML - F) - e * 1794 * Math.sin(M + F) - 1749 * Math.sin(3 * F)
				- e * 1565 * Math.sin(M - ML + F) - 1491 * Math.sin(D + F) - e * 1475 * Math.sin(M + ML + F)
				- e * 1410 * Math.sin(M + ML - F) - e * 1344 * Math.sin(M - F) - 1335 * Math.sin(D - F)
				+ 1107 * Math.sin(3 * ML + F) + 1021 * Math.sin(4 * D - F) + 833 * Math.sin(4 * D - ML + F)

				+ 777 * Math.sin(ML - 3 * F) + 671 * Math.sin(4 * D - 2 * ML + F) + 607 * Math.sin(2 * D - 3 * F)
				+ 596 * Math.sin(2 * D + 2 * ML - F) + e * 491 * Math.sin(2 * D - M + ML - F)
				- 451 * Math.sin(2 * D - 2 * ML + F) + 439 * Math.sin(3 * ML - F) + 422 * Math.sin(2 * D + 2 * ML + F)
				+ 421 * Math.sin(2 * D - 3 * ML - F) - e * 366 * Math.sin(2 * D + M - ML + F)
				- e * 351 * Math.sin(2 * D + M + F) + 331 * Math.sin(4 * D + F) + e * 315 * Math.sin(2 * D - M + ML + F)
				+ e2 * 302 * Math.sin(2 * D - 2 * M - F) - 283 * Math.sin(ML + 3 * F)
				- e * 229 * Math.sin(2 * D + M + ML - F) + e * 223 * Math.sin(D + M - F) + e * 223 * Math.sin(D + M + F)
				- e * 220 * Math.sin(M - 2 * ML - F) - e * 220 * Math.sin(2 * D + M - ML - F)
				- 185 * Math.sin(D + ML + F) + e * 181 * Math.sin(2 * D - M - 2 * ML - F)
				- e * 177 * Math.sin(M + 2 * ML + F) + 176 * Math.sin(4 * D - 2 * ML - F)
				+ e * 166 * Math.sin(4 * D - M - ML - F) - 164 * Math.sin(D + ML - F) + 132 * Math.sin(4 * D + ML - F)
				- 119 * Math.sin(D - ML - F) + e * 115 * Math.sin(4 * D - M - F)
				+ e2 * 107 * Math.sin(2 * D - 2 * M + F)

				- 2235 * Math.sin(LL) + 382 * Math.sin(A3) + 175 * Math.sin(A1 - F) + 175 * Math.sin(A1 + F)
				+ 127 * Math.sin(LL - ML) - 115 * Math.sin(LL + ML);
		return B;

	}

	/*
	 * sommes des principaux thermes de la theorie ELP2000 pour la distance terre -
	 * lune
	 */
	static double R1(double ML, double D, double M, double F, double e, double e2) {
		double R1 = -20905355 * Math.cos(ML) - 3699111 * Math.cos(2 * D - ML) - 2955968 * Math.cos(2 * D)
				- 569925 * Math.cos(2 * ML) + 246158 * Math.cos(2 * D - 2 * ML) - e * 204586 * Math.cos(2 * D - M)
				- 170733 * Math.cos(2 * D + ML) - e * 152138 * Math.cos(2 * D - M - ML) - e * 129620 * Math.cos(M - ML)
				+ 108743 * Math.cos(D) + e * 104755 * Math.cos(M + ML) + 79661 * Math.cos(ML - 2 * F)
				+ e * 48888 * Math.cos(M) - 34782 * Math.cos(4 * D - ML) + e * 30824 * Math.cos(2 * D + M)
				+ e * 24208 * Math.cos(2 * D + M - ML) - 23210 * Math.cos(3 * ML) - 21636 * Math.cos(4 * D - 2 * ML)
				- e * 16675 * Math.cos(D + M) + 14403 * Math.cos(2 * D - 3 * ML) - e * 12831 * Math.cos(2 * D - M + ML)
				- 11650 * Math.cos(4 * D) - 10445 * Math.cos(2 * D + 2 * ML)

				+ 10321 * Math.cos(2 * D - 2 * F) + e * 10056 * Math.cos(2 * D - M - 2 * ML)
				- e2 * 9884 * Math.cos(2 * D - 2 * M) + 8752 * Math.cos(2 * D - ML - 2 * F) - 8379 * Math.cos(D - ML)
				- e * 7003 * Math.cos(M - 2 * ML) + 6322 * Math.cos(D + ML) + e * 5751 * Math.cos(M + 2 * ML)
				- e2 * 4950 * Math.cos(2 * D - 2 * M - ML) - 4421 * Math.cos(2 * ML - 2 * F)
				+ 4130 * Math.cos(2 * D + ML - 2 * F) - e * 3958 * Math.cos(4 * D - M - ML)
				+ 3258 * Math.cos(3 * D - ML) - 3149 * Math.cos(2 * F) + e * 2616 * Math.cos(2 * D + M + ML)
				+ e2 * 2354 * Math.cos(2 * D + 2 * M - ML) - e2 * 2117 * Math.cos(2 * M - ML)
				- e * 1897 * Math.cos(4 * D - M - 2 * ML) - 1739 * Math.cos(D - 2 * ML) - e * 1571 * Math.cos(4 * D - M)
				- 1423 * Math.cos(4 * D + ML) + e2 * 1165 * Math.cos(2 * M + ML) - 1117 * Math.cos(4 * ML);
		return R1;

	}

	/* LongitudeMoyenneLune */
	static double LLunelune(double t) {
		double LLune = (218.31644735 + 481267.88122838 * t - 0.00159944 * (t * t) + (t * t * t) / 538841);
		return LLune;
	}

	/* anomalie moyenne du soleil (M) */
	static double M(double t) {
		double M = 357.5291092 + 35999.0502909 * t - 0.0001536 * (t * t) + (t * t * t) / 24490000;
		return M;
	}

	/* anomalie moyenne de la lune */

	static double MLune(double t) {
		double MLune = (134.96339622 + 477198.86750067 * t + 0.00872053 * (t * t) + (t * t * t) / 69699);
		return MLune;
	}

	/* longitude moyenne du noeud ascendant de la lune */
	static double ElongationD(double t) {
		double D = (297.85019172 + 445267.11139756 * t - 0.00190272 * (t * t) + (t * t * t) / 545868);
		return D;
	}

	/* longitude geocentrique moyenne du soleil ( LO) */
	static double F(double t) {
		double F = 93.27209769 + 483202.01756053 * t - 0.00367481 * (t * t) - (t * t * t) / 3525955;
		return F;
	}

	static double VerifDegres(double Deg) {
		if (Deg < 0) {
			Deg = Deg + (360 * Math.floor(Deg / 360) * -1);
		} else if (Deg > 360) {
			Deg = Deg - (360 * Math.floor(Deg / 360));
		}
		return Deg;
	}

	static double degres_radian(double degres) {
		double radian;
		radian = degres * Math.PI / 180;
		return radian;
	}

}
