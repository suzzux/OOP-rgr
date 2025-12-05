/**
 * Клас для запуску "юніт-тестів" без використання фреймворку JUnit.
 * Кожен тестовий метод перевіряє певний сценарій і викидає AssertionError у разі невдачі.
 * Це дозволяє перевірити коректність логіки VendingMachine.
 */
public class VendingMachineTestRunner {

    private static VendingMachine machine;

    public static void main(String[] args) {
        System.out.println("Запуск тестів...");
        runTest("testSuccessfulPurchase");
        runTest("testPurchaseWithExactAmount");
        runTest("testInsufficientFundsException");
        runTest("testProductOutOfStockException");
        runTest("testInvalidSlotException");
        runTest("testReturnChange");
        System.out.println("Всі тести успішно пройдені!");
    }

    private static void runTest(String testName) {
        try {
            System.out.print("Виконується тест: " + testName + "... ");
            setUp(); // Ініціалізація перед кожним тестом
            switch (testName) {
                case "testSuccessfulPurchase": testSuccessfulPurchase(); break;
                case "testPurchaseWithExactAmount": testPurchaseWithExactAmount(); break;
                case "testInsufficientFundsException": testInsufficientFundsException(); break;
                case "testProductOutOfStockException": testProductOutOfStockException(); break;
                case "testInvalidSlotException": testInvalidSlotException(); break;
                case "testReturnChange": testReturnChange(); break;
            }
            System.out.println("OK");
        } catch (AssertionError e) {
            System.out.println("FAIL");
            System.err.println("Помилка в тесті " + testName + ": " + e.getMessage());
            System.exit(1); // Завершити з помилкою
        } catch (Exception e) {
            System.out.println("FAIL");
            System.err.println("Неочікуваний виняток у тесті " + testName + ": " + e);
            System.exit(1);
        }
    }

    private static void setUp() {
        machine = VendingMachine.getInstance();
        machine.reset();
        machine.addSlot(new Slot(ProductFactory.createDrink("Coke", 25.0, 0.5), 5));
        machine.addSlot(new Slot(ProductFactory.createFood("Snickers", 18.0, 50), 0));
    }

    private static void testSuccessfulPurchase() throws VendingMachineException {
        machine.insertMoney(30.0);
        machine.selectSlot(1);
        assertEquals(5.0, machine.getCurrentBalance());
        assertEquals(4, machine.getSlots().get(0).getQuantity());
    }

    private static void testPurchaseWithExactAmount() throws VendingMachineException {
        machine.insertMoney(25.0);
        machine.selectSlot(1);
        assertEquals(0.0, machine.getCurrentBalance());
    }

    private static void testInsufficientFundsException() {
        machine.insertMoney(10.0);
        try {
            machine.selectSlot(1);
            throw new AssertionError("Очікувався InsufficientFundsException");
        } catch (InsufficientFundsException e) {
            // Очікуваний виняток
        } catch (VendingMachineException e) {
            throw new AssertionError("Очікувався інший тип винятку");
        }
        assertEquals(10.0, machine.getCurrentBalance());
    }

    private static void testProductOutOfStockException() {
        machine.insertMoney(50.0);
        try {
            machine.selectSlot(2);
            throw new AssertionError("Очікувався ProductOutOfStockException");
        } catch (ProductOutOfStockException e) {
            // Очікуваний виняток
        } catch (VendingMachineException e) {
            throw new AssertionError("Очікувався інший тип винятку");
        }
    }

    private static void testInvalidSlotException() throws VendingMachineException {
        machine.insertMoney(50.0);
        assertThrows(InvalidSlotException.class, () -> machine.selectSlot(99));
        assertThrows(InvalidSlotException.class, () -> machine.selectSlot(0));
    }

    private static void testReturnChange() {
        machine.insertMoney(50.0);
        machine.requestChange();
        assertEquals(0.0, machine.getCurrentBalance());
    }

    // --- Допоміжні методи для асертів ---

    @FunctionalInterface
    interface ThrowableRunnable {
        void run() throws Exception;
    }

    private static void assertEquals(double expected, double actual) {
        if (Math.abs(expected - actual) > 0.001) {
            throw new AssertionError("Очікувалося " + expected + ", але було " + actual);
        }
    }

    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Очікувалося " + expected + ", але було " + actual);
        }
    }

    private static void assertThrows(Class<? extends Throwable> expected, ThrowableRunnable code) {
        try {
            code.run();
            throw new AssertionError("Очікувався виняток " + expected.getSimpleName());
        } catch (Exception e) {
            if (!expected.isInstance(e)) {
                throw new AssertionError("Очікувався виняток " + expected.getSimpleName() + ", але отримано " + e.getClass().getSimpleName());
            }
        }
    }
}
