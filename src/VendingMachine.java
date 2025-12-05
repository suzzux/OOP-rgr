import java.util.ArrayList;
import java.util.List;

/**
 * Основний клас торгового автомата, реалізований як Singleton.
 * Він керує слотами з продуктами, балансом та станами.
 * 
 * GRASP Patterns:
 * - Creator: Створює та керує об'єктами Slot.
 * - Information Expert: Має всю необхідну інформацію для розрахунку вартості,
 *   решти та перевірки наявності товару.
 * 
 * Creational Patterns:
 * - Singleton: Гарантує існування лише одного екземпляра автомата в системі.
 * 
 * Behavioral Patterns:
 * - State: Керує станами автомата (Idle, HasMoney, Sold). Делегує поведінку
 *   об'єктам стану.
 */
public class VendingMachine {
    private static final VendingMachine INSTANCE = new VendingMachine();
    private List<Slot> slots;
    private double currentBalance;
    
    // State Pattern: Поля для всіх можливих станів
    private final VendingMachineState idleState;
    private final VendingMachineState hasMoneyState;
    private final VendingMachineState soldState;
    
    private VendingMachineState currentState;
    private Product productToDispense;

    // Observer Pattern: Список спостерігачів
    private final List<VendingMachineObserver> observers = new ArrayList<>();

    private VendingMachine() {
        this.slots = new ArrayList<>();
        this.currentBalance = 0.0;
        
        this.idleState = new IdleState();
        this.hasMoneyState = new HasMoneyState();
        this.soldState = new SoldState();
        this.currentState = idleState;
    }

    public static VendingMachine getInstance() {
        return INSTANCE;
    }
    
    // --- Методи, що делегують виклики стану ---
    
    public void insertMoney(double amount) {
        currentState.insertMoney(this, amount);
    }

    public void selectSlot(int slotNumber) throws VendingMachineException {
        currentState.selectProduct(this, slotNumber);
    }
    
    public void dispenseProduct() {
        currentState.dispenseProduct(this);
    }
    
    public double requestChange() {
        return currentState.returnChange(this);
    }
    
    // --- Внутрішні методи, що використовуються станами ---

    void doInsertMoney(double amount) {
        if (amount > 0) {
            this.currentBalance += amount;
            notifyObservers(String.format("Баланс оновлено: %.2f грн", this.currentBalance));
        }
    }
    
    Product doSelectProduct(int slotNumber) throws VendingMachineException {
        if (slotNumber <= 0 || slotNumber > slots.size()) {
            throw new InvalidSlotException("Невірний номер слота: " + slotNumber);
        }
        Slot slot = slots.get(slotNumber - 1);
        if (slot.getQuantity() <= 0) {
            throw new ProductOutOfStockException("Товар '" + slot.getProduct().getName() + "' закінчився.");
        }
        Product product = slot.getProduct();
        if (currentBalance < product.getPrice()) {
            throw new InsufficientFundsException(String.format("Недостатньо коштів. Потрібно: %.2f грн, на балансі: %.2f грн.", product.getPrice(), currentBalance));
        }
        currentBalance -= product.getPrice();
        slot.decreaseQuantity();
        notifyObservers(String.format("Видано '%s'. Залишилось: %d. Баланс: %.2f грн", product.getName(), slot.getQuantity(), currentBalance));
        return product;
    }
    
    double doReturnChange() {
        double change = currentBalance;
        currentBalance = 0;
        if (change > 0) {
            notifyObservers(String.format("Повернено решту: %.2f грн. Баланс: 0.00 грн", change));
        }
        return change;
    }

    // --- Observer Pattern методи ---

    public void addObserver(VendingMachineObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(VendingMachineObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (VendingMachineObserver observer : observers) {
            observer.update(message);
        }
    }

    // --- Геттери та Сеттери для станів ---
    
    void setCurrentState(VendingMachineState newState) {
        this.currentState = newState;
    }

    VendingMachineState getIdleState() { return idleState; }
    VendingMachineState getHasMoneyState() { return hasMoneyState; }
    VendingMachineState getSoldState() { return soldState; }
    
    void setProductToDispense(Product product) { this.productToDispense = product; }
    Product getProductToDispense() { return productToDispense; }
    
    // --- Інші методи ---
    
    public void addSlot(Slot slot) { slots.add(slot); }
    public List<Slot> getSlots() { return slots; }
    public double getCurrentBalance() { return currentBalance; }
    
    public void reset() {
        slots.clear();
        currentBalance = 0;
        currentState = idleState;
    }

    public void displayProducts() {
        System.out.println("--- Асортимент ---");
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            if(slot.getQuantity() > 0) {
                System.out.printf("%d. %s | Залишилось: %d%n", (i + 1), slot.getProduct().getDescription(), slot.getQuantity());
            }
        }
        System.out.println("-----------------");
    }
}
