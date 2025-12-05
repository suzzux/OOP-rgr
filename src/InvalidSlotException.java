/**
 * Виняток, що викидається, коли користувач обирає неіснуючий або невірний номер слота.
 */
public class InvalidSlotException extends VendingMachineException {
    public InvalidSlotException(String message) {
        super(message);
    }
}
