# Un template d'Application en Ligne de Commande (CLI) avec Java 25 et les JEP 458, 511 et 512

Le template **basic-java-25-quickstart** permet de démarrer une nouvelle application en commande ligne avec Java sans
utiliser aucun système de build (autre que `make`).

Ce template nécessite d'utiliser Java 25 ou une version supérieure car il utilise les JEPs suivantes :

* La [JEP 458: Launch Multi-File Source-Code Programs](https://openjdk.org/jeps/458) qui permet de lancer un programme
  Java dont le code est réparti dans plusieurs fichiers sources sans avoir besoin de les compiler préalablement
* La [JEP 511: Module Import Declarations](https://openjdk.org/jeps/511) qui permet d'importer tous les packages
  exportés par un module java
* La [JEP 512: Compact Source Files and Instance Main Methods](https://openjdk.org/jeps/512) qui permet de :
  * Se passer de déclaration de classe
  * Simplifier la déclaration de la méthode `main`
  * Utiliser les méthodes `println` et `readln` de la classe `java.lang.IO`

Le template utilise la librairie [record-args](https://github.com/nipafx/record-args), développée par
[Nicolai Parlog](https://nipafx.dev), et qui utilise les `record` et les interfaces `sealed` pour définir les arguments
de la ligne de commande et en effectuer le parsing.

* [Créer une application depuis ce template](#créer-une-application-depuis-ce-template)
* [Construire votre application](#construire-votre-application)
  * [Changer le nom de l'application](#changer-le-nom-de-lapplication)
  * [Lancer l'application localement](#lancer-lapplication-localement)
  * [Afficher l'aide de l'application](#afficher-laide-de-lapplication)
  * [Construire le livrable de l'application](#construire-le-livrable-de-lapplication)
* [Installer l'application](#installer-lapplication)
  * [Shell de lancement](#shell-de-lancement)
  * [Lancer l'application installée](#lancer-lapplication-installée)
* [Autres templates](#autres-templates)

## Créer une application depuis ce template

Pour créer une application depuis ce template, on procédera de la même façon que pour le template
[basic-java-23-quickstart](https://github.com/java-cli-apps/basic-java-23-quickstart) :

<a href="https://asciinema.org/a/669372" target="_blank"><img src="https://asciinema.org/a/669372.svg" /></a>

## Construire votre application

Après avoir [créé le dépôt GitHub](https://github.com/new?template_name=basic-java-25-quickstart&template_owner=java-cli-apps)
de votre nouvelle application à partir de ce template, vous pouvez suivre les étapes suivantes pour construire votre application.

### Changer le nom de l'application

Pour changer le nom de l'application, initialement nommée _BasicQuickstart_, il faut positionner la variable `APP_NAME`
dans le terminal comme suit :

```bash
$ export APP_NAME=MyCmdLine
```

### Lancer l'application localement

```bash
$ make run-app
```

```console
./bin/Application.sh --language French
Bonjour 🇫🇷
```

### Afficher l'aide de l'application

```bash
$ make run-app-help
```

```console
./bin/Application.sh --help
Usage: Application [--language French | English] [--help]
```

### Construire le livrable de l'application

Le livrable de l'application comprend les sources ainsi que ses dépendances.

```bash
$ make package
```

```console
mkdir --parents build/MyCmdLine/src build/MyCmdLine/lib build/MyCmdLine/bin
cp --update --recursive src lib bin build/MyCmdLine
cd build/MyCmdLine \
	&& mv src/Application.java src/MyCmdLine.java \
	&& mv bin/Application.sh bin/MyCmdLine.sh
cd build \
	&& zip --quiet --recurse-paths MyCmdLine.zip MyCmdLine
```

## Installer l'application

```bash
$ DEST_DIR=/home/user make install
```

```console
unzip -q -d /home/user build/MyCmdLine.zip
```

### Shell de lancement

Le script de lancement [Application.sh](bin/Application.sh), dont le rôle est de lancer le fichier
[Application.java](src/Application.java), est renommé lors de la construction du livrable en `MyCmdLine.sh`.

Cela permet d'ajouter plusieurs applications dans le `PATH` et donc d'invoquer directement `MyCmdLine.sh`.

### Lancer l'application installée

```bash
$ DEST_DIR=/home/user make run-installed-app
```

```console
PATH=/home/user/MyCmdLine/bin:/usr/lib/jvm/jdk-25-ea/bin:/home/fopy/.local/bin:... MyCmdLine.sh
Bonjour 🇫🇷
```

Il ne nous reste plus qu'à :

- Implémenter notre métier dans [Application.java](src/Application.java)
- Ajouter les fichiers qui déclarent les classes utilisées par `Application.java` dans le répertoire [src](src)
- Ajouter les jars de nos dépendances dans le répertoire [lib](lib)

## Autres templates

Si vous ne disposez pas de Java 25, vous avez deux possibilités :

- Utiliser le template [basic-java-23-quickstart](https://github.com/java-cli-apps/basic-java-23-quickstart) qui ne
requiert que la version 23 de Java.
- Utiliser le template [basic-java-11-quickstart](https://github.com/java-cli-apps/basic-java-11-quickstart)
qui ne requiert que la version 11 de Java. Il est alors nécessaire que tout le code Java réside dans le même fichier
comme décrit dans la [JEP 330](https://openjdk.org/jeps/330).
