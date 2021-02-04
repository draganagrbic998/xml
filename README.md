# Projekat iz predmeta XML i WEB servisi

Članovi tima:
1. Dragana Grbić SW22/2017
2. Jovan Svorcan SW29/2017
3. Marko Mijatović SW30/2017
4. Petar Nikolić SW31/2017

Napomene:
1. URL backenda organa vlasti - http://localhost:8081
2. URL backenda poverenika - http://localhost:8082
3. URL email servica - http://localhost:8083
4. URL frontenda organa vlasti - http://localhost:4200
5. URL frontenda poverenika - http://localhost:4201 (pokrece se komandom "ng serve --port 4201")
6. URL xml baze organa vlasti - http://localhost:8080/exist1
7. URL xml baze poverenika - http://localost:8080/exist2
8. URL rdf baze organa vlasti - http://localhost:8080/fuseki1
9. URL rdf baze poverenika - http://localhost:8080/fuseki2
10. email default-nog gradjanina: draganaasd@gmail.com, lozinka: asd
11. email default-nog sluzbenika: sluzbenik@gmail.com, lozinka: asd
12. email default-nog poverenika: poverenik@gmail.com, lozinka: asd

Uputstva:
1. Otici na link https://tomcat.apache.org/download-80.cgi i preuzeti tomcat
2. Otici na link https://bintray.com/existdb/releases/exist/4.6.1/view i preuzeti "exist-4.6.1.war" fajl
3. Otici na link https://jena.apache.org/download/index.cgi#apache-jena-fuseki i preuzeti "apache-jena-fuseki-3.17.0.zip" zip
4. Preuzeti "exist-4.6.1.war" fajl kopirati dvaput u webapps folder preuzetog tomcat servera, fajlove nazvati "exist1" i "exist2"
5. Iz preuzetog "apache-jena-fuseki-3.17.0.zip" zipa kopirati "fuseki.war" fajl dva puta u webapps folder preuzetog tomcat servera, fajlove nazvati "fuseki1" i "fuseki2"
6. Pokrenuti tomcat server i sacekati da se deploy-uju sve cetiri baze
7. Otici na URL lokacije XML/RDF baza (tacke broj 6, 7, 8 i 9 u sekciji "Napomene" sadrze koje su to putanje) i proveriti da li su baze inicijalizovane. Kod obe RDF baze (i fuseki1 i fuseki2) napraviti dataset pod nazivom "PersonDataset"
8. Importovati "backend-organ-vlasti", "backend-poverenik" i "email-service" spring projekte u Eclipse/IntelliJ/sta vec volite
9. Pokrenuti sva tri projekta
10. Projekti "backend-organ-vlasti" i "backend-poverenik" pri pokretanju pokusavaju da uspostave vezu sa svojom XML/RDF bazom. Ukoliko pokretanje bude neuspesno, znaci da baze niste dobro podesili
11. Pozicionirajte se u "frontend-organ-vlasti" projekat i pokrenite komande "npm install" i "ng serve"
12. Pozicionirajte se u "frontend-poverenik" projekat i pokrenite komande "npm install" i "ng serve --port 4201" (obavezno navesti port jer "backend-poverenik" komunicira samo sa frontend aplikacijom koja trci na tom portu)
13. Otvorite Vas omiljeni browser i ukucajte URL frontenda organa vlasti/poverenika (URL putanje napisane su u sekciji "Napomene"). Ulogujte se kao gradjanin/sluzbenik/poverenik sa kredencijalima koji su napisani u sekciji "Napomene"

