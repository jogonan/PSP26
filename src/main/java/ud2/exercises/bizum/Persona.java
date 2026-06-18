package ud2.exercises.bizum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Persona extends Thread {
    private final Cuenta compte;
    private final List<Persona> amics;

    public Persona(String nom, int saldoInicial) {
        super(nom); // Assignem el nom al Thread
        this.compte = new Cuenta(saldoInicial);
        this.amics = new ArrayList<>();
    }

    public void afegirAmic(Persona amic) {
        if (amic != this && !amics.contains(amic)) {
            amics.add(amic);
        }
    }

    public Cuenta getCompte() {
        return compte;
    }

    @Override
    public void run() {
        int maxBizums = 20;

        for (int i = 1; i <= maxBizums; i++) {
            // Condició d'eixida: si no tenim amics, no podem operar
            if (amics.isEmpty()) break;

            // Condició d'eixida: comprovem si ens hem quedat sense diners suficients (10€)
            if (compte.getSaldo() < 10) {
                System.out.printf("[!] %s ha parat en l'intent %d perquè no té saldo suficient (%d€).\n",
                        getName(), i, compte.getSaldo());
                break;
            }

            // Triem un amic aleatori de la llista
            int indexAleatori = ThreadLocalRandom.current().nextInt(amics.size());
            Persona amicTriat = amics.get(indexAleatori);

            // Intentem retirar de manera segura. Si es pot, fem l'ingrés a l'amic
            if (this.compte.retirar(10)) {
                amicTriat.getCompte().ingressar(10);
                System.out.printf("💸 %s ha enviat un Bizum de 10€ a %s (Saldo actual de %s: %d€).\n",
                        getName(), amicTriat.getName(), getName(), compte.getSaldo());
            }

            // Pausa curta aleatòria per a permetre que els fils s'entremesclen de veritat
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50, 150));
            } catch (InterruptedException e) {
                System.out.printf("[!] El fil de %s ha sigut interromput.\n", getName());
                break;
            }
        }
        System.out.printf("🏁 %s ha acabat de operar. Saldo final: %d€\n", getName(), compte.getSaldo());
    }
}