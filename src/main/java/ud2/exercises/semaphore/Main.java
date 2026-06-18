package ud2.exercises.semaphore;

public class Main {
    public static void main(String[] args) {
        // Creem el nostre semàfor amb un màxim de 2 permisos simultanis
        MySemaphore semafor = new MySemaphore(2);

        // Creem 5 fils que simulen l'accés al recurs
        for (int i = 1; i <= 5; i++) {
            final String nomFil = "Fil " + i;
            new Thread(() -> {
                try {
                    semafor.acquire();

                    // Simulem que el fil es queda treballant amb el recurs durant 1,5 segons
                    Thread.sleep(1500);

                    semafor.release();
                } catch (InterruptedException e) {
                    System.err.println(nomFil + " interromput.");
                }
            }, nomFil).start();
        }
    }
}