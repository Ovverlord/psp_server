package classes;

public class Material {
    private String name;
    private Double unitcost;
    private Double usedamount;
    private Integer id;

    public Material(String name, Double unitcost, Double usedamount) {
        this.name = name;
        this.unitcost = unitcost;
        this.usedamount = usedamount;
    }

    public Material(String name, Double unitcost, Double usedamount, Integer id) {
        this.name = name;
        this.unitcost = unitcost;
        this.usedamount = usedamount;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getUnitcost() {
        return unitcost;
    }

    public void setUnitcost(Double unitcost) {
        this.unitcost = unitcost;
    }

    public Double getUsedamount() {
        return usedamount;
    }

    public void setUsedamount(Double usedamount) {
        this.usedamount = usedamount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
