/**
 * Базовий абстрактний клас для всіх винятків, пов'язаних з роботою торгового автомата.
 * Наслідування від Exception робить його перевіреним (checked), що змушує клієнтський код
 * обробляти ці виняткові ситуації.
 */
public abstract class VendingMachineException extends Exception {
    public VendingMachineException(String message) {
        super(message);
    }
}
