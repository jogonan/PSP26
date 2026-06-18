package ud2.exercises.bizum;

public class Cuenta {
    private int saldo;

    public Cuenta(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Mètode sincronitzat per a consultar el saldo de manera segura
    public synchronized int getSaldo() {
        return saldo;
    }

    // Ingressar diners (Sincronitzat perquè varis amics poden fer-li Bizum a la vegada)
    public synchronized void ingressar(int quantitat) {
        this.saldo += quantitat;
    }

    // Retirar diners. Torna 'true' si s'ha pogut fer, o 'false' si no hi ha prou saldo
    public synchronized boolean retirar(int quantitat) {
        if (this.saldo >= quantitat) {
            this.saldo -= quantitat;
            return true;
        }
        return false;
    }
}
