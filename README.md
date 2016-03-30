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
* Sve što ima veze sa komunikacijom sa serverom izdvajamo u **service** i **factory**.
* Koliko sam ja shvatio, **factory** i **service** je gotovo isti, ali se preporučuje da se koriste **factory**. Ispod je primjer koda koji predstavlja jedan *Factory* i čuva se kao *factoryname.service.js*
    ```
    angular
        .module('app.core')
        .factory('dataservice', dataservice);

    dataservice.$inject = ['$http', 'logger'];

    function dataservice($http, logger) {
        var someValue = '';
        var service = {
            save: save,
            someValue: someValue,
            validate: validate
        };
        return service;

        function save() {
            /* */
        };

        function validate() {
            /* */
        };
    }
    ```
* Kada kupimo podatke, ne radimo to direktno u kontroleru, nego napravimo funkciju ```activate()```, pozovemo je poslije navođenja *bindable* elemenata, a implementiramo je poslije u kontroleru. (objasniću vam usmeno bolje :) )
* **Inject** radite kao što sam ostavio zakomentarisano u **LoginController**-u.
* Konvencije za ime:
  * __Imena fajlova:__ ```feature.type.js```, recimo ```main.controller.js``` ili ```products.service.js```
  * __Imena kontrolera:__ ```MainController``` *prvo slovo veliko*
  * __Imena servisa/factory-a:__ ```someService``` *prvo slovo malo*
* Što se tiče pisanja kontrolera, pogledajte **LoginController**
* Za samu strukuru projekta, to ćemo malo narušiti, jer oni predlažu da za svaki **view** pravimo folder u kome čuvamo sve vezano za taj **view**, ali meni se više sviđa ovo sa **controllers**, **views**, itd. :)

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
