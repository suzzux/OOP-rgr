/**
 * Реалізація оплати карткою. Імітація зчитування картки.
 */
public class CardPayment implements PaymentStrategy {
    private String cardNumber;

    public CardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Здійснюється транзакція на суму %.2f грн з картки %s...%n", amount, cardNumber);
        // Імітація успішної транзакції
        System.out.println("Оплата пройшла успішно.");
        return true;
    }
}
