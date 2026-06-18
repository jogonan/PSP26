package ud2.exercises.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    /**
     * Sala.java: Guarda una List<Client> clientesEsperando.
     * <p>
     * Método synchronized void entrarCliente(Client c) -> Añade cliente, hace notifyAll().
     * <p>
     * Método synchronized Client atenderCliente() -> Si está vacía while(...) wait(). Si hay, saca al cliente y lo devuelve.
     */


    private List<Client> clientesEsperando;

    public Sala() {
        this.clientesEsperando = new ArrayList<>();
    }

    public synchronized void entrarCliente(Client c) {
        clientesEsperando.add(c);
        notifyAll();
    }

    public synchronized Client atenderCliente() throws InterruptedException {
        while (clientesEsperando.isEmpty()) {
            wait();
        }
        return clientesEsperando.remove(0);
    }

}
