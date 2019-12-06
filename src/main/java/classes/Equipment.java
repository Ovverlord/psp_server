package classes;

public class Equipment {
    private String name;
    private String model;
    private Integer hoursworked;
    private Double energy;
    private Double gas;
    private Integer id;

    public Equipment(String name, String model, Integer hoursWorked, Double energy, Double gas, Integer id) {
        this.name = name;
        this.model = model;
        this.hoursworked = hoursWorked;
        this.energy = energy;
        this.gas = gas;
        this.id = id;
    }

    public Equipment(String name, String model, Integer hoursWorked, Double energy, Double gas) {
        this.name = name;
        this.model = model;
        this.hoursworked = hoursWorked;
        this.energy = energy;
        this.gas = gas;
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

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getGas() {
        return gas;
    }

    public void setGas(Double gas) {
        this.gas = gas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
