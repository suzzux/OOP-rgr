/**
 * Контролер (Controller) для управління торговим автоматом.
 * Він приймає введення від користувача (або іншого клієнта), створює відповідні
 * команди (Command Pattern) і виконує їх.
 * 
 * GRASP Patterns:
 * - Controller: Є точкою входу для взаємодії з UI (в даному випадку, консоллю)
 *   та делегує виконання запитів до моделі (VendingMachine). Обробляє винятки.
 * 
 * Behavioral Patterns:
 * - Command (Invoker): Створює та виконує об'єкти команд.
 */
public class VendingMachineController {
    private final VendingMachine machine;
    private final PaymentProcessor paymentProcessor;

    public VendingMachineController() {
        this.machine = VendingMachine.getInstance();
        this.paymentProcessor = new PaymentProcessor();
    }

    /**
     * Виконує передану команду.
     * @param command Команда для виконання.
     */
    public void executeCommand(Command command) {
        try {
            command.execute();
        } catch (VendingMachineException e) {
            System.err.println("[ПОМИЛКА]: " + e.getMessage());
        }
    }

    /**
     * Ініціалізує автомат початковими даними.
     */
    public void initializeVendingMachine() {
        // Використовуємо ProductFactory для створення продуктів
        Product coke = ProductFactory.createDrink("Coca-Cola", 25.0, 0.5);
        Product pepsi = ProductFactory.createDrink("Pepsi", 24.5, 0.5);
        Product snickers = ProductFactory.createFoodWithExtras("Snickers", 18.0, 50, 250, "2025-12-31");
        Product lays = ProductFactory.createFood("Lays", 32.5, 80);

        machine.addSlot(new Slot(coke, 10));
        machine.addSlot(new Slot(pepsi, 5));
        machine.addSlot(new Slot(snickers, 1)); // Спеціально для тестування out of stock
        machine.addSlot(new Slot(lays, 8));
        
        // Додаємо спостерігача
        machine.addObserver(new ConsoleDisplayObserver());
    }

    public VendingMachine getMachine() {
        return machine;
    }
    
    /**
     * Демонстраційний метод для ілюстрації роботи патерну Strategy.
     * Імітує повний цикл покупки товару з вибором способу оплати.
     * @param slotNumber номер слота
     * @param paymentStrategy стратегія оплати
     */
    public void purchaseProductWithPayment(int slotNumber, PaymentStrategy paymentStrategy) {
        System.out.println("\n--- Починаємо процес покупки товару №" + slotNumber + " ---");
        try {
            // 1. Отримуємо ціну товару (без фактичної видачі)
            if (slotNumber <= 0 || slotNumber > machine.getSlots().size()) {
                throw new InvalidSlotException("Невірний номер слота.");
            }
            Slot slot = machine.getSlots().get(slotNumber - 1);
            if (slot.getQuantity() <= 0) {
                throw new ProductOutOfStockException("Товар закінчився.");
            }
            
            double price = slot.getProduct().getPrice();
            System.out.println("Ціна товару: " + price + " грн.");

            // 2. Встановлюємо стратегію та обробляємо платіж
            paymentProcessor.setPaymentStrategy(paymentStrategy);
            if (paymentProcessor.processPayment(price)) {
                // 3. Якщо оплата успішна, вносимо гроші на баланс та видаємо товар
                System.out.println("Оплата пройшла успішно. Вносимо гроші на баланс автомата...");
                machine.insertMoney(price);
                machine.selectSlot(slotNumber);
            } else {
                System.out.println("Оплата не вдалася.");
            }

        } catch (VendingMachineException e) {
            System.err.println("[ПОМИЛКА]: " + e.getMessage());
        } finally {
            // 4. Повертаємо решту, щоб автомат був готовий до наступної операції
            machine.requestChange();
            System.out.println("--- Процес покупки завершено ---");
        }
    }
}
