package ud2.exercises.bizum;

import java.util.List;

public class Bizum {
    public static void main(String[] args) {
        // 1. Creem les persones amb diferents saldos inicials
        Persona joan = new Persona("Joan", 50);
        Persona marta = new Persona("Marta", 100);
        Persona lluis = new Persona("Lluís", 20);
        Persona elena = new Persona("Elena", 80);

        List<Persona> tots = List.of(joan, marta, lluis, elena);

        // 2. Ajuntem a tots com a amics de tots de manera encreuada
        for (Persona p : tots) {
            for (Persona amic : tots) {
                p.afegirAmic(amic);
            }
        }

        System.out.println("=== INICIANT SIMULACIÓ DE BIZUM CONCURRENT ===\n");

        // 3. Llancem tots els fils simultàniament
        for (Persona p : tots) {
            p.start();
        }

        // 4. Sincronitzem el fil principal perquè espere a que acaben tots els Bizums
        for (Persona p : tots) {
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 5. Auditoria final de control
        System.out.println("\n=============================================");
        System.out.println("   SIMULACIÓ FINALITZADA. AUDITORIA DE SALDOS");
        System.out.println("=============================================");

        int dinersTotalsFinals = 0;
        for (Persona p : tots) {
            System.out.printf("• %s: %d€\n", p.getName(), p.getCompte().getSaldo());
            dinersTotalsFinals += p.getCompte().getSaldo();
        }

        System.out.println("---------------------------------------------");
        System.out.printf("Diners totals en el sistema: %d€\n", dinersTotalsFinals);
        System.out.println("(Nota: Hauria de coincidir exactament amb els 250€ inicials)");
    }
}