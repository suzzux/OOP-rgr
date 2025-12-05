/**
 * Конкретна реалізація спостерігача (Observer), яка виводить повідомлення
 * про події в консоль. Імітує дисплей на автоматі.
 */
public class ConsoleDisplayObserver implements VendingMachineObserver {
    @Override
    public void update(String message) {
        System.out.println("[DISPLAY]: " + message);
    }
}
