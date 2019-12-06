package classes;

public class Result {
    private Double finalEnergyCost;
    private Double finalGasCost;
    private Double finalRentCost;
    private Integer finalWageCost;
    private Double finalMaterialCost;
    private Double finalCost;
    private Integer id;

    public Result(Double finalEnergyCost, Double finalGasCost, Double finalRentCost, Integer finalWageCost,
                  Double finalMaterialCost, Double finalCost, Integer id) {
        this.finalEnergyCost = finalEnergyCost;
        this.finalGasCost = finalGasCost;
        this.finalRentCost = finalRentCost;
        this.finalWageCost = finalWageCost;
        this.finalMaterialCost = finalMaterialCost;
        this.finalCost = finalCost;
        this.id = id;
    }

    public Result(Double finalEnergyCost, Double finalGasCost, Double finalRentCost,
                  Integer finalWageCost, Double finalMaterialCost, Double finalCost) {
        this.finalEnergyCost = finalEnergyCost;
        this.finalGasCost = finalGasCost;
        this.finalRentCost = finalRentCost;
        this.finalWageCost = finalWageCost;
        this.finalMaterialCost = finalMaterialCost;
        this.finalCost = finalCost;
    }

    public Double getFinalEnergyCost() {
        return finalEnergyCost;
    }

    public void setFinalEnergyCost(Double finalEnergyCost) {
        this.finalEnergyCost = finalEnergyCost;
    }

    public Double getFinalGasCost() {
        return finalGasCost;
    }

    public void setFinalGasCost(Double finalGasCost) {
        this.finalGasCost = finalGasCost;
    }

    public Double getFinalRentCost() {
        return finalRentCost;
    }

    public void setFinalRentCost(Double finalRentCost) {
        this.finalRentCost = finalRentCost;
    }

    public Integer getFinalWageCost() {
        return finalWageCost;
    }

    public void setFinalWageCost(Integer finalWageCost) {
        this.finalWageCost = finalWageCost;
    }

    public Double getFinalMaterialCost() {
        return finalMaterialCost;
    }

    public void setFinalMaterialCost(Double finalMaterialCost) {
        this.finalMaterialCost = finalMaterialCost;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Double finalCost) {
        this.finalCost = finalCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
