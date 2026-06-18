package ud2.exercises.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Cuina {

    /**
     * Cuina.java: Guarda List<Client> comandas y List<Client> platosListos.
     * <p>
     * Método synchronized void dejarComanda(Client c) -> Añade comanda, hace notifyAll().
     * <p>
     * Método synchronized Client cogerComanda() -> Si no hay comandas while(...) wait(). Saca comanda y la devuelve.
     * <p>
     * Método synchronized void dejarPlato(Client c) -> Si platosListos.size() == 5 hace while(...) wait(). Añade plato, hace notifyAll().
     */

    private List<Client> comandas;
    private List<Client> platosListos;

    public Cuina() {
        this.comandas = new ArrayList<>();
        this.platosListos = new ArrayList<>();
    }

    public synchronized void dejarComanda(Client c) {
        comandas.add(c);
        notifyAll();
    }

    public synchronized Client cogerComanda() throws InterruptedException {
        while (comandas.isEmpty()) wait();
        return comandas.remove(0);
    }

    public synchronized Client recollirPlat() throws InterruptedException {
        // Si la barra está vacía, el camarero se espera a que los cocineros saquen algo
        while (platosListos.isEmpty()) {
            wait();
        }

        // El camarero coge el primer plato que ve en la barra
        Client clienteConPlato = platosListos.remove(0);

        // ¡LA SEÑAL CLAVE! Avisa a los cocineros de que la barra ya no está llena (ahora hay 4 platos)
        notifyAll();

        return clienteConPlato; // Devuelve al cliente dueño del plato
    }

    public synchronized void dejarPlato(Client c) {
        while (platosListos.size() == 5) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        platosListos.add(c);
        notifyAll();

    }


}
