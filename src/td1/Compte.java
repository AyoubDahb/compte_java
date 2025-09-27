package td1;

public class Compte {

    // Constantes valeurs par défaut
    public static final double SOLDE_PAR_DEFAUT = 0;
    public static final double DECOUVERT_MAX_PAR_DEFAUT = 800;
    public static final double DEBIT_MAX_PAR_DEFAUT = 1000;

    // Variables de classe et d'instance
    private static int nbcompte = 0; // Compteur de comptes créés

    private int numeroCompte; // Numéro unique du compte

    private String nomTitulaire; // Nom du titulaire

    private double solde; // Solde actuel du compte

    private double decouvertMax; // Découvert maximal autorisé

    private double debitMax; // Débit maximal autorisé

    // Constructeur 1 : uniquement nom car indispensable avec les valeurs par défaut
    public Compte(String nomTitulaire) {

        this(nomTitulaire, SOLDE_PAR_DEFAUT, DECOUVERT_MAX_PAR_DEFAUT, DEBIT_MAX_PAR_DEFAUT);

    }

    // Constructeur 2 : nom et solde (découvert et débit par défaut)
    public Compte(String nomTitulaire, double solde) {

        this(nomTitulaire, solde, DECOUVERT_MAX_PAR_DEFAUT, DEBIT_MAX_PAR_DEFAUT);

    }

    // Constructeur 3 : nom, solde, découvert et débit
    public Compte(String nomTitulaire, double solde, double decouvertMax, double debitMax) {

        this.numeroCompte = ++nbcompte;

        this.nomTitulaire = nomTitulaire;

        this.solde = solde;

        this.decouvertMax = decouvertMax;

        this.debitMax = debitMax;

    }

    // Constructeur 4 : nom, découvert et débit (solde par défaut = 0)
    public Compte(String nomTitulaire, double decouvertMax, double debitMax) {

        this(nomTitulaire, SOLDE_PAR_DEFAUT, decouvertMax, debitMax);

    }

    // permet de récupérer l'information (getter)
    public int getNumeroCompte() {
        return numeroCompte;
    }

    public String getNomTitulaire() {
        return nomTitulaire;
    }

    public double getSolde() {
        return solde;
    }

    public double getDecouvertMax() {
        return decouvertMax;
    }

    public double getDebitMax() {
        return debitMax;
    }

// permet de définir l'information (setters)

    public void setDecouvertMax(double decouvertMax) {
        if (decouvertMax > 0) {
            this.decouvertMax = decouvertMax;
        } else {
            System.out.println("Valeur doit être superieur à 0 ");
        }
    }

    public void setDebitMax(double debitMax) {
        if (debitMax > 0) {
            this.debitMax = debitMax;
        } else {
            System.out.println("Valeur doit être superieur 0 pour débit max");
        }
    }


    // permet de créditer de l'argent (ajouter de l'argent)
    public void crediter(double montant) throws NegatifException {

        if (montant > 0) {

            this.solde += montant;

        } else {

            throw new NegatifException("Le montant ne peut pas être negatif");

        }

    }

    //permet de débiter de l'argent, le montant doit être positif (et il doit respecter le debit max et dévouvert)
    public void debiter(double montant) throws NegatifException, DecouvertException, DebitMaxException {

        if (montant <= 0) {

            throw new NegatifException("le montant ne peut pas être negatif");

        }

        if (montant > this.debitMax) {

            throw new DebitMaxException("Debit max dépassé");

        }

        if ((this.solde - montant) < -this.decouvertMax) {

            throw new DecouvertException("Decouvert dépassé");

        }

        this.solde -= montant;

    }

// Permet de faire un virement entre deux comptes (methode) /// 

    public void virement(double montant, Compte destinataire) throws NegatifException, DecouvertException, DebitMaxException, CompteException {

        if (destinataire == null) {
            throw new CompteException("Le compte destinataire ne peut pas être null");
        }
        if (destinataire == this) {
            throw new CompteException("Impossible de faire un virement vers le même compte");
        }

        this.debiter(montant);
        destinataire.crediter(montant);
    }

    // Méthode pour vérifier si le compte est à découvert
    public boolean verifDecouvert() {

        return this.solde < 0;

    }

    // Méthode pour calculer le débit autorisé
    public double montantDebitAutorise() {

        return Math.min(this.debitMax, this.solde + this.decouvertMax);

    }

    // Méthode toString AJOUTÉE

    @Override
    public String toString() {
        return "Compte{" + "numeroCompte=" + numeroCompte + ", nomTitulaire='" + nomTitulaire + '\'' + ", solde=" + solde + ", decouvertMax=" + decouvertMax + ", debitMax=" + debitMax + ", aDecouvert=" + verifDecouvert() + '}';
    }


}
