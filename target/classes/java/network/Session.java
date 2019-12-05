package network;

public class Session {
    String currentLogin;
    String currentIsAdmin;
    Integer currentID;
    static Session session;


    Session(String currentLogin, String currentIsAdmin, Integer currentID) {
        this.currentLogin = currentLogin;
        this.currentIsAdmin = currentIsAdmin;
        this.currentID = currentID;
    }

    public static Session getSession(String currentLogin,String currentIsAdmin,Integer currentID) {
        if (session == null) {
            session = new Session(currentLogin,currentIsAdmin,currentID);
        }
        if(currentLogin==null)
        {
            session = null;
        }
        return session;
    }

    public String getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(String currentLogin) {
        this.currentLogin = currentLogin;
    }

    public String getCurrentIsAdmin() {
        return currentIsAdmin;
    }

    public void setCurrentIsAdmin(String currentIsAdmin) {
        this.currentIsAdmin = currentIsAdmin;
    }

    public Integer getCurrentID() {
        return currentID;
    }

    public void setCurrentID(Integer currentID) {
        this.currentID = currentID;
    }
}
