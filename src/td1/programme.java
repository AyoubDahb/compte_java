package td1;

public class programme {
    public static void main(String[] args) {
        // Création des comptes
        Compte c1 = new Compte("J.DUPONT", 1000);
        Compte c2 = new Compte("C.DURANT", 50000, 5000, 6000);
        Compte c10 = new Compte("J.POIRE");
        Compte c11 = new Compte("B.POMME", 1200, 1500);



        // Affichage des caractéristiques initiales des comptes
        System.out.println("Caractéristiques initiales des comptes :");
        afficherCaracteristiques(c1);
        afficherCaracteristiques(c2);
        afficherCaracteristiques(c10);
        afficherCaracteristiques(c11);




        // Affichage montant débit autorisé
        System.out.println("Montant max autorisé c1: " + c1.montantDebitAutorise() + "€");


        // Test setters > 0
        System.out.println("\nTest setters sup 0:");
        System.out.println("Avant: découvert=" + c1.getDecouvertMax() + ", débit=" + c1.getDebitMax());

        c1.setDecouvertMax(1500);
        c1.setDebitMax(2000);
        System.out.println("Valeurs > 0: découvert=" + c1.getDecouvertMax() + ", débit=" + c1.getDebitMax());

        c1.setDecouvertMax(0);
        c1.setDebitMax(-100);
        System.out.println("Valeurs ≤ 0: découvert=" + c1.getDecouvertMax() + ", débit=" + c1.getDebitMax());

        // Test crédit négatif
        try {
            c1.crediter(-100);
        } catch (NegatifException e) {
            System.out.println(e.getMessage());
        }

        // Test débit négatif
        try {
            c1.debiter(-2000);
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Test débit = 0
        try {
            c1.debiter(0);
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Test débit trop grand (debit max)
        try {
            c1.debiter(1500); // c1 a un debit max de 2000 maintenant
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Test découvert dépassé
        try {
            c1.debiter(2000); // Tester selon nouvelles valeurs
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Retirer 300 € du compte c1 (doit marcher)
        System.out.println("\nRetrait de 300 € du compte c1...");
        try {
            c1.debiter(300);
            System.out.println("retrait ok");
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Retirer 600 € du compte c2
        System.out.println("Retrait de 600 € du compte c2...");
        try {
            c2.debiter(600);
            System.out.println("retrait ok");
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Déposer 500 € sur le compte c1
        System.out.println("Dépôt de 500 € sur le compte c1...");
        try {
            c1.crediter(500);
            System.out.println("depot ok");
        } catch (NegatifException e) {
            System.out.println(e.getMessage());
        }

        // Test crédit 0
        try {
            c1.crediter(0);
        } catch (NegatifException e) {
            System.out.println(e.getMessage());
        }

        // Affichage après operations
        System.out.println("\nCaractéristiques des comptes après les opérations :");
        afficherCaracteristiques(c1);
        afficherCaracteristiques(c2);

        // Test virement avec debit max dépassé - REGROUPÉ
        try {
            c2.virement(7000, c1); // c2 a debit max 6000
        } catch (NegatifException | DecouvertException | DebitMaxException | CompteException e) {
            System.out.println(e.getMessage());
        }

        // Test virement négatif - REGROUPÉ
        try {
            c2.virement(-500, c1);
        } catch (NegatifException | DecouvertException | DebitMaxException | CompteException e) {
            System.out.println(e.getMessage());
        }

        // Test virement vers compte null - REGROUPÉ
        try {
            c2.virement(100, null);
        } catch (NegatifException | DecouvertException | DebitMaxException | CompteException e) {
            System.out.println(e.getMessage());
        }

        // Test auto-virement - REGROUPÉ
        try {
            c1.virement(50, c1);
        } catch (NegatifException | DecouvertException | DebitMaxException | CompteException e) {
            System.out.println(e.getMessage());
        }

        // Virer 1000 € du compte c2 vers le compte c1 (doit marcher) - REGROUPÉ
        System.out.println("\nVirement de 1000 € du compte c2 vers le compte c1...");
        try {
            c2.virement(1000, c1);
            System.out.println("virement ok");
        } catch (NegatifException | DecouvertException | DebitMaxException | CompteException e) {
            System.out.println(e.getMessage());
        }

        // Test avec compte à découvert
        Compte c3 = new Compte("P.FOUAD", 100, 200, 500);
        System.out.println("\nTest avec compte c3:");
        afficherCaracteristiques(c3);

        // Debit qui met à découvert mais ok
        try {
            c3.debiter(150); // solde devient -50, ok car découvert 200
            System.out.println("débit ok, compte à découvert maintenant");
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Debit qui dépasse le découvert
        try {
            c3.debiter(200); // solde deviendrait -250 > 200
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Test limite exacte du découvert
        Compte c4 = new Compte("B.HARRADA", 50, 50, 1000);
        try {
            c4.debiter(100); // 50 - 100 = -50, exactement la limite
            System.out.println("débit à la limite exacte ok");
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Un euro de plus doit échouer
        try {
            c4.debiter(1);
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Test plusieurs debits successifs
        Compte c5 = new Compte("A.DAHBI", 500, 100, 200);
        System.out.println("\nTest débits successifs sur c5:");
        try {
            c5.debiter(200); // solde = 300
            System.out.println("1er débit ok, solde = " + c5.getSolde());
            c5.debiter(200); // solde = 100
            System.out.println("2eme débit ok, solde = " + c5.getSolde());
            c5.debiter(200); // solde = -100 (découvert ok)
            System.out.println("3eme débit ok, solde = " + c5.getSolde());
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Test debit avec debit max = 0
        Compte c6 = new Compte("A.DANISTEKIN", 1000, 500, 0);
        try {
            c6.debiter(1);
        } catch (NegatifException | DecouvertException | DebitMaxException e) {
            System.out.println(e.getMessage());
        }

        // Test virement 0 - REGROUPÉ
        try {
            c2.virement(0, c1);
        } catch (NegatifException | DecouvertException | DebitMaxException | CompteException e) {
            System.out.println(e.getMessage());
        }

        // Affichage final
        System.out.println("\nCaractéristiques finales des comptes :");
        afficherCaracteristiques(c1);
        afficherCaracteristiques(c2);
        afficherCaracteristiques(c3);
        afficherCaracteristiques(c4);
        afficherCaracteristiques(c5);
        afficherCaracteristiques(c6);
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
