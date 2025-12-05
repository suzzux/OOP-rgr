/**
 * Стан видачі товару. Перехідний стан, який виконує видачу і одразу ж змінює стан далі.
 */
public class SoldState implements VendingMachineState {

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("Зачекайте, йде видача товару...");
    }

    @Override
    public void selectProduct(VendingMachine machine, int slotNumber) {
        System.out.println("Зачекайте, йде видача товару...");
    }

    @Override
    public void dispenseProduct(VendingMachine machine) {
        Product product = machine.getProductToDispense();
        if (product != null) {
            // Тут могла б бути логіка фізичної видачі
        }

        // Перевіряємо, чи залишилась решта
        if (machine.getCurrentBalance() > 0) {
            // Замість автоматичної видачі решти, перейдемо в стан HasMoney
            // щоб користувач міг зробити ще одну покупку
            machine.setCurrentState(machine.getHasMoneyState());
        } else {
            // Якщо грошей не залишилось, переходимо в стан очікування
            machine.setCurrentState(machine.getIdleState());
        }
    }

    @Override
    public double returnChange(VendingMachine machine) {
        System.out.println("Зачекайте, йде видача товару...");
        return 0;
    }
}
