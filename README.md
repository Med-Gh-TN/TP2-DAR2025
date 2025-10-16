# ☕ TP2-DAR2025: Sockets TCP - Échange de Données Complexes

*Évolution du projet client-serveur avec gestion de chaînes de caractères et d'objets sérialisables pour le cours de LSI3.*

**🧑‍💻 Auteur :** Mouhamed Gharsallah  
**🎓 Classe :** LSI3 - TP2 1

---

## 📝 Aperçu du Projet

Ce projet est la suite du TP1 et s'inscrit dans le cadre du module d'Applications Réparties. L'objectif est de faire évoluer notre application client-serveur pour gérer des communications plus complexes que l'échange d'un simple octet.

Le projet se concentre sur deux mécanismes de communication essentiels en Java :
1.  L'échange de **chaînes de caractères** (CC) pour envoyer des commandes complètes.
2.  L'échange d'**objets sérialisables** pour une communication structurée et orientée objet.

## 📂 Structure du Projet

Le projet est organisé autour de la nouvelle architecture de packages et de deux branches Git, chacune correspondant à une activité du TP2.

### Nouvelle structure de packages :
- **`clientPackage`**: Contient la classe `Client`.
- **`serverPackage`**: Contient la classe `Server`.
- **`model`**: Contient la classe `Operation` (utilisée dans l'Activité 2.2).

---

### 1️⃣ Activité 2.1: Échange de Chaînes de Caractères (CC) via Sockets TCP 💬
*(Branche: `Med-Gh-TN-Activite_2_1`)*

> L'objectif est de dépasser la limite de l'échange d'un seul octet. L'application est modifiée pour que le client puisse envoyer une opération mathématique complète (ex: "55 * 25") sous forme de chaîne de caractères. Le serveur reçoit cette chaîne, la traite, effectue le calcul et renvoie le résultat. Cette étape utilise les classes `BufferedReader` et `PrintWriter` pour la gestion des flux de texte.

### 2️⃣ Activité 2.2: Échange d'Objets Sérialisables 📦
*(Branche: `Med-Gh-TN-Activite_2_2`)*

> Pour une communication plus propre et robuste, cette activité remplace l'échange de chaînes par l'échange d'objets.
> - Une classe `Operation` est créée et implémente l'interface `Serializable` pour encapsuler les données de l'opération.
> - Le client instancie cet objet, l'envoie au serveur en utilisant `ObjectOutputStream`.
> - Le serveur le reçoit via `ObjectInputStream`, effectue le calcul, met à jour l'objet avec le résultat et le renvoie au client.

---

## 🚀 Comment Compiler et Exécuter

Assurez-vous d'avoir le **JDK (Java Development Kit)** installé. Le projet utilisant des packages, les commandes sont légèrement différentes du TP1.

Supposons que vos fichiers `.java` sont dans un dossier `src` organisé par package.

```
Projet_TP2/
└── src/
    ├── clientPackage/
    │   └── Client.java
    ├── serverPackage/
    │   └── Server.java
    └── model/
        └── Operation.java
```

### Compilation (une seule fois pour tout le projet)
Ouvrez un terminal à la racine (`Projet_TP2/`) et compilez tous les fichiers. L'option `-d bin` crée un dossier `bin` pour les fichiers `.class` compilés.

```bash
# Crée un dossier 'bin' s'il n'existe pas
mkdir bin

# Compile tous les .java du dossier src et place les .class dans bin
javac -d bin src/clientPackage/*.java src/serverPackage/*.java src/model/*.java
```

### Exécution

#### Côté Serveur 🖥️
Ouvrez un terminal et exécutez la commande suivante depuis la racine du projet :

```bash
# Lance la classe Server en indiquant que les packages sont dans le dossier 'bin'
java -cp bin serverPackage.Server
```
Le serveur se mettra en attente d'une connexion.

#### Côté Client 💻
Ouvrez un **nouveau** terminal et exécutez cette commande depuis la racine du projet :
```bash
# Lance la classe Client
java -cp bin clientPackage.Client
```
Le client se connectera au serveur et vous pourrez commencer à envoyer des opérations.
