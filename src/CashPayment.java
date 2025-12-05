/**
 * Реалізація оплати готівкою. У нашому випадку це просто імітація.
 */
public class CashPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.printf("Оплата готівкою на суму %.2f грн прийнята.%n", amount);
        // У реальному житті тут була б інтеграція з купюроприймачем
        return true;
    }
}
