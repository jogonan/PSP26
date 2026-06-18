package ud2.exercises.restaurant;

public class Cuiner extends Thread {
    private final Cuina cuina;

    public Cuiner(String nombre, Cuina cuina) {
        super(nombre);
        this.cuina = cuina;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 1. El cocinero va al tablero de la cocina a por una comanda.
                // (Si no hay notas, el cocinero se queda aquí haciendo 'wait')
                Client cliente = cuina.cogerComanda();
                System.out.printf("👨‍🍳 %s: He cogido la comanda de %s. ¡Oídos cocina!\n",
                        getName(), cliente.getName());

                // 2. Simula el tiempo que tarda en preparar el plato
                Thread.sleep(2000);

                // 3. Deja el plato terminado en el mostrador de platos listos.
                // (Si la barra está llena con 5 platos, aquí se hará un 'wait')
                cuina.dejarPlato(cliente);
                System.out.printf("🍔 %s: Plato listo para %s. ¡Pasa a la barra!\n",
                        getName(), cliente.getName());
            }
        } catch (InterruptedException e) {
            System.out.printf("❌ %s ha apagado los fogones (interrumpido).\n", getName());
        }
    }
}