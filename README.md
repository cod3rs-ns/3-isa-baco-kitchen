# Predmetni projekat ISA i MRS
Predmetni projekat iz Internet softverskih arhitektura i Metodologije razvoja softvera.

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
**Napomena:** IntellJ Community ne podržava Spring.

Nisam dodavao **.idea** fajl, tako da ćete vjerovatno morati napraviti novi **Maven** projekat i samo importovati ovo sa repozitorijuma.

Nakon dodavanja projekta, idite na **View -> Tool Windows -> Maven Projects**.
Dalje, u **Maven konzoli** kucajte ```mvn clean install```, pa zatim ```mvn spring-boot:run```.

U fajlu **application.properties** se nalaze podešavanja vezana za aplikaciju. Trenutno, jedino podešavanje je **server.port = 8091** što znači da će se server startovati na tom portu.

Nakon svih koraka bi trebalo [ovdje](http://localhost:8091/index.html) da vidite *index.html* stranu.
