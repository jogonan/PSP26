package ud2.exercises.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    public static void main(String[] args) {
        System.out.println("=== COMIENZA EL DÍA EN EL RESTAURANTE ===\n");

        // 1. Almacenes
        Sala sala = new Sala();
        Cuina cuina = new Cuina();

        // 2. Actores permanentes (Trabajadores)
        Cambrer cambrer = new Cambrer("Luis", sala, cuina);
        Cuiner cui1 = new Cuiner("Jeronimo", cuina);
        Cuiner cui2 = new Cuiner("Alberto", cuina);

        cambrer.start();
        cui1.start();
        cui2.start();

        // 3. Creamos una lista para recordar a los clientes
        List<Client> listaClientes = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Client cli = new Client("Cli&" + i, sala);
            listaClientes.add(cli); // Lo guardamos en la lista antes de lanzarlo
            cli.start();
        }

        // 4. EL TRUCO: El main se congela aquí esperando a que terminen los CLIENTES
        for (Client cli : listaClientes) {
            try {
                cli.join(); // El main espera a que este cliente acabe sus 3 platos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 5. Si el programa llega a esta línea, significa que ya NO QUEDAN CLIENTES
        System.out.println("\n=== TODOS LOS CLIENTES HAN CENADO Y SE HAN IDO ===");
        System.out.println("Cerrando el restaurante. Despachando a los trabajadores...\n");

        // 6. ¡Hora de irse a casa! Interrumpimos los bucles infinitos de los trabajadores
        cambrer.interrupt();
        cui1.interrupt();
        cui2.interrupt();

        System.out.println("=== RESTAURANTE CERRADO DE FORMA SEGURA ===");
    }
}