package ud2.exercises.restaurant;

public class Cambrer extends Thread {
    private final Sala sala;
    private final Cuina cuina;

    public Cambrer(String nombre, Sala sala, Cuina cuina) {
        super(nombre);
        this.sala = sala;
        this.cuina = cuina;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 1. Va a la sala a por un cliente (se espera si está vacía)
                Client cliente = sala.atenderCliente();

                // 2. Le toma nota y le despierta (clienteAtendido)
                cliente.serAtendido();

                // 3. Lleva la nota a la cocina
                cuina.dejarComanda(cliente);
                System.out.printf("🏃‍♂️ %s: He llevado la comanda de %s a la cocina.\n", getName(), cliente.getName());

                // ================= ¡LO QUE NOS FALTABA! =================

                // 4. El camarero va a la barra de la cocina a por un plato listo
                // (Si los cocineros aún no han terminado ningún plato, Luis esperará aquí)
                Client clienteListo = cuina.recollirPlat();

                // 5. Luis corre a la mesa del cliente y le sirve la comida ("le pulsa su bón de vibrar")
                clienteListo.serServido();
                System.out.printf("🚀 %s: ¡He servido el plato en la mesa de %s!\n", getName(), clienteListo.getName());

                Thread.sleep(150); // Un pequeño respiro antes del siguiente cliente
            }
        } catch (InterruptedException e) {
            System.out.printf("❌ %s ha terminado su turno (interrumpido).\n", getName());
        }
    }
}