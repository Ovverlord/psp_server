package classes;

public class User {
    private String login;
    private String password;
    private String isAdmin;
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String isAdmin() {
        return isAdmin;
    }

    public void setAdmin(String admin) {
        isAdmin = admin;
    }

    public User(String login, String password, String isAdmin) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(String login, String password, String isAdmin,Integer id) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
        this.id = id;
    }
}