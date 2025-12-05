/**
 * Інтерфейс для патерну State. Визначає дії, які може виконувати торговий автомат,
 * і реалізація яких залежить від поточного стану автомата.
 */
public interface VendingMachineState {
    
    /**
     * Внесення грошей в автомат.
     * @param machine Контекст (сам автомат).
     * @param amount Сума для внесення.
     */
    void insertMoney(VendingMachine machine, double amount);
    
    /**
     * Вибір продукту.
     * @param machine Контекст (сам автомат).
     * @param slotNumber Номер обраного слота.
     * @throws VendingMachineException якщо виникає помилка під час вибору.
     */
    void selectProduct(VendingMachine machine, int slotNumber) throws VendingMachineException;
    
    /**
     * Видача продукту.
     * @param machine Контекст (сам автомат).
     */
    void dispenseProduct(VendingMachine machine);
    
    /**
     * Повернення решти.
     * @param machine Контекст (сам автомат).
     * @return Сума решти.
     */
    double returnChange(VendingMachine machine);
}
