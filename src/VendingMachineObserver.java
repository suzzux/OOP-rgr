/**
 * Інтерфейс для патерну Observer. Визначає метод, який викликатиметься
 * у спостерігачів при зміні стану суб'єкта (VendingMachine).
 */
public interface VendingMachineObserver {
    /**
     * Метод, що викликається, коли в автоматі відбулася подія.
     * @param message Повідомлення про подію.
     */
    void update(String message);
}
