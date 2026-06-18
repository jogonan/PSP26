package ud2.exercises.ExtinguishFire;

public class ExtinguishFire {
    public static void main(String[] args) {
        System.out.println("=== INICIANT SIMULACIÓ D'EXTINCIÓ D'INCENDIS ===\n");

        // 1. Inicialitzem els components (Capacitat piscina, Intensitat foc)
        Pool piscina = new Pool(1000);
        Fire incendi = new Fire(3000);

        // 2. Paràmetres d'ompliment: afegir 250L cada 400 mil·lisegons
        PoolFiller sistemaOmpliment = new PoolFiller(piscina, 250, 400, incendi);

        // L'helicòpter necessita una capacitat de 1000L per a operar
        Helicopter helicopter = new Helicopter("Helicòpter Bombers GIRO", piscina, incendi, 1000);

        // 3. Arrancada concurrent
        sistemaOmpliment.start();
        helicopter.start();

        // 4. Sincronització final
        try {
            helicopter.join();
            sistemaOmpliment.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== SIMULACIÓ FINALITZADA: L'entorn està segur ===");
    }
}