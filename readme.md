# READ ME - Pay My Buddy

## Dépendences

- [Java 8 (ou supérieur)](https://adoptium.net/ "Java 8 (ou supérieur)") ;
- [Maven (3.8.1 ou supérieur)](https://maven.apache.org/download.cgi "Maven (3.8.1 ou supérieur)") ;
- [Node.js (pour le package npm)](https://nodejs.org/en/ "Node.js (pour le package npm)") ;
- [Vue (via npm)](https://fr.vuejs.org/v2/guide/installation.html#npm "Vue (via npm)") ;
- [Wampserver (3.2.3 ou supérieur)](https://www.wampserver.com/en/download-wampserver-64bits/ "Wampserver (3.2.3 ou supérieur)") ;

Note : ces dépendances sont uniquement les dépendances **directes** de l'application. Certaines d'entre elle peuvent nécessiter l'installation d'autres dépendances ;

## Installation

1. Installez toute les dépendances **dans l'ordre** ;
2. Téléchargez le code du [backend](https://github.com/EyNimor/pay-my-buddy-backend "backend") et du [frontend](https://github.com/EyNimor/pay-my-buddy-frontend "frontend") sur GitHub ;
3. Extraire les deux archives, vous devriez avoir deux dossier avec "-master" à la fin, le code est à l'intérieur ;
4. Lancez Wampserver et dans PHPMyAdmin, Importez le script .sql pour créez la base de donnée ;

## Lançement

1. Lancez Wampserver s'il ne l'est pas déjà ;
2. Allez dans le dossier du backend, ouvrez un terminal et lancez la commande `mvn spring-boot:run` ;
3. Allez dans le dossier du frontend, ouvrez un terminal et lancer la commande `npm run dev` ;
4. Allez à l'adresse affiché dans le terminal du frontend, la page de LogIn de l'application devrait s'afficher ;
