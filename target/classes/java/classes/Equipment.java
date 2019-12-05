package classes;

public class Equipment {
    private String name;
    private String model;
    private Integer hoursworked;
    private Integer energy;
    private Integer gas;
    private Integer id;

    public Equipment(String name, String model, Integer hoursWorked, Integer energy, Integer gas, Integer id) {
        this.name = name;
        this.model = model;
        this.hoursworked = hoursWorked;
        this.energy = energy;
        this.gas = gas;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getHoursWorked() {
        return hoursworked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursworked = hoursWorked;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Integer getGas() {
        return gas;
    }

    public void setGas(Integer gas) {
        this.gas = gas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
