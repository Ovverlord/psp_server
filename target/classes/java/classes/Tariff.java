package classes;

public class Tariff {
    private Double energyCost;
    private Double gasCost;
    private Double rentCost;
    private Integer id;


    public Tariff(Double energyCost, Double gasCost, Double rentCost) {
        this.energyCost = energyCost;
        this.gasCost = gasCost;
        this.rentCost = rentCost;
    }

    public Tariff(double energyCost, double gasCost, double rentCost, Integer id) {
        this.energyCost = energyCost;
        this.gasCost = gasCost;
        this.rentCost = rentCost;
        this.id = id;
    }

    public double getEnergyCost() {
        return energyCost;
    }

    public void setEnergyCost(double energyCost) {
        this.energyCost = energyCost;
    }

    public double getGasCost() {
        return gasCost;
    }

    public void setGasCost(double gasCost) {
        this.gasCost = gasCost;
    }

    public double getRentCost() {
        return rentCost;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
