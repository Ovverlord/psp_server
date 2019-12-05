package classes;

public class Worker {
    private String surname;
    private String name;
    private String lastname;
    private String position;
    private Integer wage;
    private Integer hoursworked;
    private Integer id;

    public Worker(String surname, String name, String lastname, String position, Integer wage, Integer hoursWorked, Integer id) {
        this.surname = surname;
        this.name = name;
        this.lastname = lastname;
        this.position = position;
        this.wage = wage;
        this.hoursworked = hoursWorked;
        this.id = id;
    }



    public Worker(String surname, String name, String lastname, String position, Integer wage, Integer hoursWorked) {
        this.surname = surname;
        this.name = name;
        this.lastname = lastname;
        this.position = position;
        this.wage = wage;
        this.hoursworked = hoursWorked;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getWage() {
        return wage;
    }

    public void setWage(Integer wage) {
        this.wage = wage;
    }

    public Integer getHoursworked() {
        return hoursworked;
    }

    public void setHoursworked(Integer hoursworked) {
        this.hoursworked = hoursworked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
