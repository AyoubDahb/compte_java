package td1;


public class programme {
    public static void main(String[] args) {
        // Création des comptes
        Compte c1 = new Compte("J. DUPONT", 1000);
        Compte c2 = new Compte("C. DURANT", 50000, 5000, 6000);

        // Affichage des caractéristiques initiales des comptes
        System.out.println("Caractéristiques initiales des comptes :");
        afficherCaracteristiques(c1);
        afficherCaracteristiques(c2);

        try{c1.crediter(-100);}catch (NegatifException e){System.out.println("montant negatif");}
/// Mettre un get message ici
        try{c1.debiter(2000);}catch (NegatifException e){System.out.println("montant negatif");}catch(DecouvertException e){System.out.println("t en decouvert");}catch(DebitMaxException e){System.out.println("t > au debit max");}
///  Mettre un get message ici

        // Retirer 300 € du compte c1
        System.out.println("\nRetrait de 300 € du compte c1...");
      ///  c1.debiter(300);

        // Retirer 600 € du compte c2
        System.out.println("Retrait de 600 € du compte c2...");
       /// c2.debiter(600);

        // Déposer 500 € sur le compte c1
        System.out.println("Dépôt de 500 € sur le compte c1...");
      ///  c1.crediter(500);

        // Affichage des caractéristiques après les opérations
        System.out.println("\nCaractéristiques des comptes après les opérations :");
        afficherCaracteristiques(c1);
        afficherCaracteristiques(c2);

        // Virer 1000 € du compte c2 vers le compte c1
        System.out.println("\nVirement de 1000 € du compte c2 vers le compte c1...");
       /// c2.virement(1000, c1);

        // Affichage des caractéristiques finales des comptes
        System.out.println("\nCaractéristiques finales des comptes :");
        afficherCaracteristiques(c1);
        afficherCaracteristiques(c2);
    }

    // Méthode pour afficher les caractéristiques d'un compte
    public static void afficherCaracteristiques(Compte compte) {
        System.out.println("Numéro du compte : " + compte.getNumeroCompte());
        System.out.println("Nom du titulaire : " + compte.getNomTitulaire());
        System.out.println("Découvert maximal autorisé : " + compte.getDecouvertMax() + " €");
        System.out.println("Débit maximal autorisé : " + compte.getDebitMax() + " €");
        System.out.println("Solde du compte : " + compte.getSolde() + " €");
        if (compte.verifDecouvert()) {
            System.out.println("Le compte est à découvert !");
        } else {
            System.out.println("Le compte n'est pas à découvert.");
        }
        System.out.println();


    }

}