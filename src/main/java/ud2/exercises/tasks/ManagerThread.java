package ud2.exercises.tasks;

public class ManagerThread extends Thread {
    private Team team;

    public ManagerThread(Team team) {
        super();
        this.team = team;
        this.setName(String.format("%sManager", team.getName()));
    }

    @Override
    public void run() {
        // TODO: Make all your assigned employees do their tasks
        System.out.printf("%s: L'equip %s comença.\n", this.getName(), team.getName());

        // Correcte: Primer llanço a tots els empleats (tots comencen a treballar alhora)
        for (EmployeeThread employee : team.getEmployees()) {
            employee.start();
        }

// Després espero a que tots acabin (el Manager es queda esperant fins que l'últim acabi)
        for (EmployeeThread employee : team.getEmployees()) {
            try {
                employee.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //TODO: end
        System.out.printf("%s: L'equip %s ha realitzat totes les tasques.\n", this.getName(), team.getName());
        System.out.printf("%s: L'equip %s ha realitzat totes les tasques.\n", this.getName(), team.getName());
    }
}