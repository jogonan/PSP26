package ud2.exercises.restaurant;

public class Client extends Thread {
    private boolean atendido;
    private boolean servido;
    private final Sala sala;

    public Client(String nombre, Sala sala) {
        super(nombre); // Le damos el nombre al hilo para los prints
        this.sala = sala;
        this.atendido = false;
        this.servido = false;
    }

    // 1. MÉTODOS DE ESPERA (Los ejecuta el propio Cliente para bloquearse)

    public synchronized void esperarSerAtendido() throws InterruptedException {
        while (!atendido) {
            wait(); // El cliente se duerme en su propio candado
        }
    }

    public synchronized void esperarPlato() throws InterruptedException {
        while (!servido) {
            wait(); // El cliente espera a que el camarero le traiga el plato
        }
    }

    // 2. MÉTODOS DE ACCIÓN (Los llamará el CAMARERO sobre este cliente para despertarlo)

    public synchronized void serAtendido() {
        this.atendido = true;
        notifyAll(); // El camarero despierta al cliente: "¡Ya te he tomado nota!"
    }

    public synchronized void serServido() {
        this.servido = true;
        notifyAll(); // El camarero despierta al cliente: "¡Aquí tienes tu plato!"
    }

    // Método para resetear los estados si el cliente quiere pedir más platos
    public synchronized void prepararSiguienteRonda() {
        this.atendido = false;
        this.servido = false;
    }

    @Override
    public void run() {
        try {
            // El enunciado dice que pide platos "cada cierto tiempo", simulemos 3 platos
            for (int i = 1; i <= 2; i++) {
                System.out.printf("Entrada 🚪 | %s entra a la sala.\n", getName());
                sala.entrarCliente(this); // Se pone en la cola de la sala

                // Fase 1: Esperar al camarero
                esperarSerAtendido();
                System.out.printf("📝 %s: El camarero me ha tomado nota del plato %d.\n", getName(), i);

                // Fase 2: Esperar la comida
                esperarPlato();
                System.out.printf("🍔 %s: ¡Me han servido el plato %d! Comiendo...\n", getName(), i);

                Thread.sleep(1500); // Simula el tiempo que tarda en comer

                prepararSiguienteRonda(); // Resetea booleanos para el siguiente plato
            }
            System.out.printf("🏁 %s ha terminado de cenar y se va del restaurante.\n", getName());

        } catch (InterruptedException e) {
            System.out.printf("❌ %s ha sido interrumpido.\n", getName());
        }
    }
}