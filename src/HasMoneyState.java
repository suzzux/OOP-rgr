/**
 * Стан, коли гроші внесені. Автомат очікує вибору продукту.
 */
public class HasMoneyState implements VendingMachineState {

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        machine.doInsertMoney(amount); // Викликаємо внутрішній метод
        System.out.printf("Внесено ще: %.2f грн. Загальний баланс: %.2f грн%n", amount, machine.getCurrentBalance());
    }

    @Override
    public void selectProduct(VendingMachine machine, int slotNumber) throws VendingMachineException {
        // Делегуємо перевірку логіці самого автомата
        Product product = machine.doSelectProduct(slotNumber);
        
        // Якщо винятку не було, переходимо до стану видачі
        machine.setProductToDispense(product);
        machine.setCurrentState(machine.getSoldState());
        machine.dispenseProduct(); // Одразу викликаємо видачу
    }

    @Override
    public void dispenseProduct(VendingMachine machine) {
        System.out.println("Спочатку потрібно обрати товар.");
    }

    @Override
    public double returnChange(VendingMachine machine) {
        double change = machine.doReturnChange();
        System.out.printf("Повернено решту: %.2f грн%n", change);
        machine.setCurrentState(machine.getIdleState());
        return change;
    }
}
