/**
 * Виняток, що викидається при спробі купити товар, який закінчився у слоті.
 */
public class ProductOutOfStockException extends VendingMachineException {
    public ProductOutOfStockException(String message) {
        super(message);
    }
}
