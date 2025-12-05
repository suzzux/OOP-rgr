/**
 * Конкретна команда (Command) для внесення грошей.
 * Інкапсулює запит до VendingMachine на внесення певної суми.
 */
public class InsertMoneyCommand implements Command {
    private final VendingMachine machine;
    private final double amount;

    public InsertMoneyCommand(VendingMachine machine, double amount) {
        this.machine = machine;
        this.amount = amount;
    }

    @Override
    public void execute() {
        machine.insertMoney(amount);
    }
}
