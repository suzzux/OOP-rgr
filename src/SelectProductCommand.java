/**
 * Конкретна команда (Command) для вибору продукту.
 * Інкапсулює запит до VendingMachine на вибір продукту з певного слота.
 */
public class SelectProductCommand implements Command {
    private final VendingMachine machine;
    private final int slotNumber;

    public SelectProductCommand(VendingMachine machine, int slotNumber) {
        this.machine = machine;
        this.slotNumber = slotNumber;
    }

    @Override
    public void execute() throws VendingMachineException {
        machine.selectSlot(slotNumber);
    }
}
