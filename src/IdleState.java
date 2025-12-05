/**
 * Стан очікування (Idle). Автомат перебуває в цьому стані, коли немає грошей на балансі.
 * У цьому стані можна лише внести гроші.
 */
public class IdleState implements VendingMachineState {

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        machine.doInsertMoney(amount); // Викликаємо внутрішній метод
        System.out.printf("Внесено: %.2f грн. Баланс: %.2f грн%n", amount, machine.getCurrentBalance());
        // Зміна стану на HasMoneyState
        machine.setCurrentState(machine.getHasMoneyState());
    }

    @Override
    public void selectProduct(VendingMachine machine, int slotNumber) throws VendingMachineException {
        // Ми не можемо викинути VendingMachineException, бо він абстрактний.
        // Замість цього, ми можемо викинути один з його конкретних нащадків,
        // але простіше просто вивести повідомлення.
        // Щоб задовольнити сигнатуру методу, ми нічого не викидаємо,
        // але користувач отримує повідомлення.
        System.out.println("Спочатку внесіть гроші.");
    }

    @Override
    public void dispenseProduct(VendingMachine machine) {
        System.out.println("Неможливо видати товар, спочатку оберіть його.");
    }

    @Override
    public double returnChange(VendingMachine machine) {
        System.out.println("Баланс порожній, решта не повертається.");
        return 0;
    }
}
