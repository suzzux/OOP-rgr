/**
 * Клас, що відповідає за обробку платежів.
 * Використовує патерн Strategy для вибору конкретного способу оплати.
 */
public class PaymentProcessor {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    /**
     * Виконує платіж, використовуючи обрану стратегію.
     * @param amount Сума для оплати.
     * @return true, якщо оплата успішна, інакше false.
     */
    public boolean processPayment(double amount) {
        if (paymentStrategy == null) {
            System.err.println("Спосіб оплати не обрано.");
            return false;
        }
        return paymentStrategy.pay(amount);
    }
}
