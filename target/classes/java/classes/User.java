package classes;

public class User {
    private String login;
    private String password;
    private Integer isAdmin;
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

    public Integer isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Integer admin) {
        isAdmin = admin;
    }

    public User(String login, String password, Integer isAdmin) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}