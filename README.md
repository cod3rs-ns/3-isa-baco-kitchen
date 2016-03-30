# Predmetni projekat ISA i MRS
Predmetni projekat iz Internet softverskih arhitektura i Metodologije razvoja softvera.

### Članovi tima:
* **SW3/2013**   Stefan Ristanović
* **SW9/2013**   Bojan Blagojević
* **SWF/2013**   Dragutin Marjanović


### AngularJS smjernice
* Jedna komponenta po jednom fajlu (manje do 400 linija)
* Funkcije treba da imaju manje od 75 linija koda.
* Koristiti ContorllerAs umjesto Controllrer.
* **vm** varijable nazivajte relativno kratko, ali opisno. :)
* Sve promjenljive na vrh *kontrolera*, a onda poslije idu definicije funkcija.
* Svi **AngularJS factory** su **singleton**.
* Što se tiče pisanja kontrolera, pogledajte **LoginController**

Dio vezan za MRS se nalazi na BaseCamp-u. **BaseCamp projekat** možete posjetiti klikom [ovdje](https://3.basecamp.com/3300796/projects/596139).

U projektu će se koristiti sljedeće tehnologije:
* MySql baza podataka
* Java Spring Framework
* REST servisi
* AngularJS
* Bootstrap

*Klonirajte ovaj rezpozitorijom komandom:*
```
git clone git@github.com:dmarjanovic94/isa-mrs-project.git
```

### Uputstvo za pokretanje

IDE projekta je **IntelliJ IDEA Ulitmate**. 
**NAPOMENA:** IntelliJ Community ne podržava Spring.

Nisam dodavao **.idea** fajl, importujte **Maven** projekat i odaberite klonirani repozitorijum.

Nakon dodavanja projekta, idite na **View -> Tool Windows -> Maven Projects**.
Dalje, u **Maven konzoli** kucajte ```mvn clean install```, pa zatim ```mvn spring-boot:run```.

U fajlu **application.properties** se nalaze podešavanja vezana za aplikaciju. Trenutno, jedino podešavanje je **server.port = 8091** što znači da će se server startovati na tom portu.

Nakon svih koraka bi trebalo [ovdje](http://localhost:8091/index.html) da vidite *index.html* stranu.
