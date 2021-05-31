public class Test {

    public static void main(String[] args) {
        School enseeiht = new School("ENSEEIHT");
        School insa = new School("INSA");
        School polytech = new School("POLYTECH");

        Student diego = new Student("Diego");
        Student thomas = new Student("Thomas");
        Student kilian = new Student("Kilian");

        // school preferences
        enseeiht.addPreference(diego, 2);
        enseeiht.addPreference(thomas, 3);
        enseeiht.addPreference(kilian, 1);
        insa.addPreference(diego, 3);
        insa.addPreference(thomas, 2);
        insa.addPreference(kilian, 1);
        polytech.addPreference(diego, 1);
        polytech.addPreference(thomas, 3);
        polytech.addPreference(kilian, 2);

        // student preferences
        diego.addPreference(enseeiht, 1);
        diego.addPreference(insa, 2);
        diego.addPreference(polytech, 3);
        kilian.addPreference(enseeiht, 2);
        kilian.addPreference(insa, 3);
        kilian.addPreference(polytech, 1);
        thomas.addPreference(enseeiht, 3);
        thomas.addPreference(insa, 2);
        thomas.addPreference(polytech, 1);

        School[] schools = {enseeiht, insa, polytech};
        Student[] students = {diego, thomas, kilian};

        /*tant que le truc qui choisit est libre et a encore celui qui choisit qu'il n'a pas encore choisie
        Chaque sérénadeur à un attribut qui commence à 1 et qui s'incrémente de 1 a chaque fois qu'il est viré
        Parcourir les lignes des matrices, chercher la valeur 1 du premier numéro du couple
        Associer à chaque personne qui choisie une liste de valeur
        Si la liste de valeurs contient plus d'une valeur
        Regarder le couple associé à la valeurs
        mettre une variable de celle qu'on tej pas, et si on croise mieux, on écrase la var
        si celui qui choisit est libre => (demandeur, choisit) engagés
        sinon si une paire existe déjà alors on vérifie la préférence et s'il y a une préférence, ils s'engagent et l'autre devient libre
        sinon ils restent engagés*/



    }
}
