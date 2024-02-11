# Conventions

## Java

Les conventions utilisées sont celles définies par Oracle dans [ce document](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf) (à quelques modifications près), mais voilà quelques points importants :

- Une instruction par ligne.
- Déclarer une variable par ligne.
- Les classes et packages sont nommées en `PascalCase`.
- Les variables et méthodes sont nommées en `camelCase`.
- Les constantes sont nommées en `SNAKE_CASE`.
- On initialise les propriétés d'une classe dans l'ordre suivant :
    1. Membres statiques publics puis privés.
    2. Membres d'instance publics puis privés.
    3. Constructeurs publics puis privés.
    4. Méthodes publiques puis privées.
- On indente avec 4 espaces.
- Pour les instructions de contrôle de flot (fonctions, `if`, `for`, `while`, ...) :
    - L'accolade ouvrante est située sur la même ligne que l'instruction.
    - Dans le cas où il n'y a qu'une ligne dans le bloc, les accolades ne sont pas nécessaires (sauf dans le cas des fonctions où Java en a besoin).

## Projet Gauntlet

Au niveau des conventions spécifiques au projet :

- Les variables sont nommées en anglais correct.
- Chaque membre public (et idéalement privé) doit être documenté en anglais avec JavaDoc.
- Une variable qui contient des coordonnées peut être abréviée en `coords` et pas en `coo` (n'est-ce pas Marie).

## Exemple

### Exemple de classe

Le documentation a été """""oubliée""""" (oups).

```java
public class CoolClass {
    public static int publicStaticMember;
    private static int privateStaticMember;

    public int publicMember;
    private int privateMember;
    private final int PRIVATE_CONSTANT;

    public CoolClass(int coolParameter, String coolName) {
        // ...
    }

    private CoolClass(int coolParameter) {
        // ...
    }

    public void publicMethod() {
        // ...
    }

    private int privateMethod() {
        // ...
    }
}
```
