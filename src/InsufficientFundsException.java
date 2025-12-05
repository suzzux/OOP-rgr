/**
 * Виняток, що викидається при спробі купити товар без достатньої кількості грошей на балансі.
 */
public class InsufficientFundsException extends VendingMachineException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
