package model.sfproject1;

/**
 * the InHouse class takes in part data and machineID to distinguish it from outsourced
 * RUNTIME_ERROR:
 * 1. main () is not found in the class, but will be using JavaFX to extend in the future and error will be gone
 * @author Luis Munguia Salas
 */
public class InHouse extends Part{
    /*
     * properties
     */
    private int machineId;

    /*
     * constructor
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        // call the superclass "Part" constructor
        super(id, name, price, stock, min, max);
        // set the machineId value to machineId property
        this.machineId = machineId;
    }
    /**
     * @param machineId the id to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    /**
     * RUNTIME ERROR: forgot to add 'int' to getMachineId(), added the int before method
     * @return the machineID
     */
    public int getMachineId() {
        return this.machineId;
    }
}