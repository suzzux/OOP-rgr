import java.util.Scanner;

/**
 * Головний клас для запуску та демонстрації роботи торгового автомата.
 * Імітує взаємодію користувача з автоматом через консоль.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Створюємо та ініціалізуємо контролер
        VendingMachineController controller = new VendingMachineController();
        controller.initializeVendingMachine();
        
        VendingMachine machine = controller.getMachine();
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Ласкаво просимо до торгового автомата! =====");

        // 2. Основний цикл програми
        boolean running = true;
        while (running) {
            machine.displayProducts();
            System.out.println("\nПоточний баланс: " + String.format("%.2f грн", machine.getCurrentBalance()));
            System.out.println("\nОберіть дію:");
            System.out.println("  1. Внести гроші");
            System.out.println("  2. Обрати товар");
            System.out.println("  3. Забрати решту");
            System.out.println("  4. Демонстрація оплати (Strategy Pattern)");
            System.out.println("  5. Вийти");

            System.out.print("Ваш вибір: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введіть суму для внесення: ");
                    try {
                        double amount = Double.parseDouble(scanner.nextLine());
                        if (amount <= 0) {
                            System.out.println("Сума має бути більшою за 0.");
                            break;
                        }

                        System.out.println("Оберіть спосіб оплати:");
                        System.out.println("  1. Готівка");
                        System.out.println("  2. Картка");
                        System.out.print("Ваш вибір: ");
                        String paymentChoice = scanner.nextLine();

                        PaymentStrategy strategy = null;

                        if (paymentChoice.equals("1")) {
                            strategy = new CashPayment();
                        } else if (paymentChoice.equals("2")) {
                            while (true) {
                                System.out.print("Введіть номер картки (формат XXXX-XXXX-XXXX-XXXX) або '0' для скасування: ");
                                String cardNum = scanner.nextLine();

                                if (cardNum.equals("0")) {
                                    System.out.println("Операцію скасовано.");
                                    break; // Вихід з циклу валідації
                                }

                                // Перевірка регулярним виразом: 4 групи по 4 цифри, розділені дефісом
                                if (cardNum.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
                                    strategy = new CardPayment(cardNum);
                                    break; // Номер вірний, виходимо з циклу
                                } else {
                                    System.err.println("Невірний формат картки! Спробуйте ще раз.");
                                }
                            }
                            if (strategy == null) break; // Якщо користувач скасував введення

                        } else {
                            System.out.println("Невірний вибір способу оплати. Операцію скасовано.");
                            break;
                        }

                        // Виконання оплати
                        boolean paymentSuccess = strategy.pay(amount);

                        if (paymentSuccess) {
                            controller.executeCommand(new InsertMoneyCommand(machine, amount));
                            System.out.println("Кошти успішно зараховано на баланс.");
                        } else {
                            System.out.println("Помилка оплати.");
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("Невірний формат суми.");
                    }
                    break;
                
                case "2":
                    System.out.print("Введіть номер товару: ");
                    try {
                        int slotNumber = Integer.parseInt(scanner.nextLine());
                        controller.executeCommand(new SelectProductCommand(machine, slotNumber));
                    } catch (NumberFormatException e) {
                        System.err.println("Невірний формат номера.");
                    }
                    break;
                    
                case "3":
                    machine.requestChange();
                    break;
                    
                case "4":
                    // Демонстрація Strategy Pattern
                    System.out.println("\n--- Демо-покупка товару №1 (Coke) готівкою ---");
                    controller.purchaseProductWithPayment(1, new CashPayment());
                    
                    System.out.println("\n--- Демо-покупка товару №4 (Lays) карткою ---");
                    controller.purchaseProductWithPayment(4, new CardPayment("1234-5678-xxxx-xxxx"));
                    
                    System.out.println("\n--- Спроба купити товар, що закінчився ---");
                    controller.purchaseProductWithPayment(3, new CashPayment()); // Snickers, quantity = 1
                    controller.purchaseProductWithPayment(3, new CashPayment()); // Друга спроба
                    break;

                case "5":
                    System.out.println("Дякуємо, що скористалися нашим автоматом!");
                    running = false;
                    break;
                    
                default:
                    System.err.println("Невідома команда. Спробуйте ще раз.");
                    break;
            }
            System.out.println("=================================================");
        }
        
        scanner.close();
    }
}
