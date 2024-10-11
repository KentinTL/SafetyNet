# SafetyNet

SafetyNet est une application de secours qui permet de récupérer des informations relatives aux personnes, dossiers médicaux et stations de secours (firestations). Cette application facilite la gestion des secours en reliant les personnes à des postes de secours spécifiques et en fournissant des informations essentielles pour les services d'urgence.

## Table des matières

- [Fonctionnalités](#fonctionnalités)
- [Technologies utilisées](#technologies-utilisées)
- [Installation](#installation)
- [Exécution des tests](#exécution-des-tests)
- [Endpoints API](#endpoints-api)
- [Auteurs](#auteurs)

## Fonctionnalités

- Récupération des informations sur les personnes associées à une station de secours donnée.
- Gestion des dossiers médicaux pour chaque personne.
- Association des personnes à une firestation spécifique.
- Récupération des numéros de téléphone des résidents en fonction de leur station de secours.
- Gestion des informations résidentes lors d'une inondation (basée sur la proximité des stations de secours).
- Ajout, mise à jour et suppression des stations de secours.

## Technologies utilisées

- **Java 11**
- **Spring Boot** - Cadre utilisé pour développer l'application
- **Maven** - Gestionnaire de dépendances
- **JUnit & JaCoCo** - Pour les tests unitaires et la couverture du code
- **Surefire** - Plugin Maven pour l'exécution des tests unitaires

## Installation

1. Clonez le repository GitHub sur votre machine locale :

   ```bash
   git clone https://github.com/KentinTL/SafetyNet.git
2. Accédez au répertoire du projet :
   
   ```bash
   cd SafetyNet/SafetyNet
3. Construisez le projet avec Maven :
   
   ```bash
   mvn clean install
4. Lancez l'application :
   
   ```bash
   mvn spring-boot:run

## Exécution des tests

1. Pour exécuter les tests unitaires, utilisez la commande suivante :

    ```bash
    mvn test

Un rapport de couverture de code est généré par JaCoCo dans `target/site/jacoco`.

Si vous avez configuré Surefire pour les rapports de tests, vous pourrez trouver le rapport complet dans `target/site/surefire-report.html`.

### Rapports

- **JaCoCo** : Le rapport de couverture du code est généré par JaCoCo et disponible au format HTML dans `target/site/jacoco`.
- **Surefire** : Si vous avez configuré le plugin Surefire, vous pouvez trouver les résultats des tests sous forme de rapports dans le répertoire `target/surefire-reports`. Le fichier `surefire-report.html` contient un résumé des résultats des tests.

## Endpoints API

Voici une liste des principaux endpoints disponibles dans l'application :

- **GET** `/firestation?stationNumber={stationNumber}` : Récupère la liste des personnes couvertes par la station de secours spécifiée.
- **GET** `/phoneAlert?firestation={stationNumber}` : Récupère les numéros de téléphone des résidents desservis par une station de secours donnée.
- **GET** `/flood/stations?stations={stationNumbers}` : Récupère les informations des résidents associés à une liste de stations de secours pendant une inondation.
- **POST** `/firestation` : Ajoute une nouvelle station de secours.
- **PUT** `/firestation/{address}` : Met à jour une station de secours existante en fonction de l'adresse.
- **DELETE** `/firestation/{address}` : Supprime une station de secours par son adresse.

### Autres Endpoints

- **GET** `/personInfo?firstName={firstName}&lastName={lastName}` : Récupère des informations détaillées (y compris les antécédents médicaux) sur une personne spécifique.
- **GET** `/communityEmail?city={city}` : Récupère les adresses email de tous les résidents d'une ville donnée.
- **GET** `/childAlert?address={address}` : Récupère une liste d'enfants vivant à une adresse donnée ainsi que les membres de leur famille.

## Auteurs

- **KentinTL** - [GitHub Profile](https://github.com/KentinTL)
