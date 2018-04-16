# PlanetaryEphemeris


PlanetaryEphemeris calcul les éphémérides des planetes en prenant en compte l'aberation  


### Folder architecture

* -Package Calcul 
   |- JD ( Calcul du Jour Julien ) 
   |- GreenwichPosition ( Calcul des ephemerides des planetes rapporté à Greenwich ) 
   |- LocalPosition ( Calcul des éphemrides des planetes rapporté à la position local (lieu d'observation) )
   |- Main ( Execute le calcul )

* -Package PlanetartyData
   |- Earth
   |- Jupiter
   |- Mars
   |- Mercury
   |- Moon
   |- Neptune
   |- Saturn
   |- Uranus
   |- Venus

## More information about files
Folder dans l'ordre de dépendence:
	1 : JD : Le calcul du jour Julien, indispensable pour le calcul des ephemerides des planetes qui en suit.
	2 : GreenwichPosition : Calcul la position des planetes en partant de la longitude , latitude Radius vector fourni par les class du package : PlanetaryData. Les calculs des éphémérides des planetes prend en compte l'abberation et la nutation.
	3 : LocalPosition : Calcul de la positions des planetes  selon le lieu d'observation, en  partant des valeurs (RA, DEC) calculer dans la class GreenwishPosition.
	4 : Main : Run

## Run

Copier le projet, l'ouvrire dans l'editeur de votre choix. Lancer le fichier "Main" présent dans le Package "Calcul".

Par default le calcul des positions des planetes est rapporté au lieu d'obervation qui est poitiers. 

```
double longitude_Ville = 0.340375; //Longitude geographique de Poitiers en degres decimaux

```
```
double latitude_Ville = 46.580224; // Latitude geographique de Poitiers en degres decimaux

```
```
double hauteur_Ville = 65; // Hauteur(altitude) de poitiers en mètres

```
Si vous voulez les ephémérides rapporté a un lieu d'observation, il vous suffit de remplacer les coordonnées. 

Par default, Les ephemerides de la lune sont demandé. 
```
String demande = "Moon";
```

Si vous voulez les ephémérides d'une autres planetes, il vous suffit de remplacer "Moon" par le nom d'une autres planetes présente dans le Package "PlanetaryData. 

## Authors

* **Serli** 


## Acknowledgments

* [AstroSurf]( http://www.astrosurf.com/topic/114897-help-%C3%A9ph%C3%A9m%C3%A9rides-des-plan%C3%A8tes/ )


