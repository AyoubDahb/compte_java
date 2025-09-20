package td1;


public class Compte {

    // Variables de classe et d'instance
    private static int nbcompte = 0; // Compteur de comptes créés
    private int numeroCompte; // Numéro unique du compte
    private String nomTitulaire; // Nom du titulaire
    private double solde; // Solde actuel du compte
    private double decouvertMax; // Découvert maximal autorisé
    private double debitMax; // Débit maximal autorisé

    // Constructeur 1 : uniquement nom car indispensable avec les valeurs par défaut
    public Compte(String nomTitulaire) {
        this(nomTitulaire, 0, 800, 1000);
    }

    // Constructeur 2 : nom et solde (découvert et débit par défaut)
    public Compte(String nomTitulaire, double solde) {
        this(nomTitulaire, solde, 800, 1000);
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
        this(nomTitulaire, 0, decouvertMax, debitMax);
    }

    // permet de récupérer l’information (getter)

    public int getNumeroCompte() { return numeroCompte;}

    public String getNomTitulaire() { return nomTitulaire;}

    public double getSolde() { return solde;}

    public double getDecouvertMax() {return decouvertMax;}

    public double getDebitMax() { return debitMax;}

    // permet de définir l’information (setters)

    public void setDecouvertMax(double decouvertMax) { this.decouvertMax = decouvertMax;}

    public void setDebitMax(double debitMax) {this.debitMax = debitMax;}

    // permet de créditer de l’argent (ajouter de l'argent)
    public void crediter(double montant)throws NegatifException {
        {
            if (montant > 0) {
                this.solde += montant;


            } else {
                throw new NegatifException("Le montant ne peut pas être negatif");
            }

        }
    }

    // (methode) permet de débiter de l’argent, le montant doit être positif (et il doit respecter le debit max et dévouvert)
    public void debiter(double montant)throws  NegatifException, DecouvertException, DebitMaxException {
        if (montant > 0 && (this.solde - montant) >= -this.decouvertMax && montant <= this.debitMax) {
            this.solde -= montant;

        }
        if(montant<=0) {
            throw new NegatifException("le montant ne peut pas être negatif");
        }
        if((this.solde - montant) < -this.decouvertMax) {
            throw new DecouvertException("Decouvert dépassé");
        }
        if(montant > this.debitMax) {
            throw new DebitMaxException("Debit max dépassé");
        }


    }

    // Permet de faire un virement entre deux comptes (methode)

    public void virement(double montant, Compte destinataire)  {
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


}