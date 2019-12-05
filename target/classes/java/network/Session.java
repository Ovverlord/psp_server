package network;

public class Session {

    static Integer currentID;
    static String currentLogin;

    public Session(String currentLogin,Integer currentID) {
        if(this.currentID==null || currentID==null)
        {
            this.currentID = currentID;
        }
        if(this.currentLogin==null || currentLogin==null)
        {
            this.currentLogin = currentLogin;
        }
    }

    public Integer getCurrentID() {
        return currentID;
    }

    public void setCurrentID(Integer currentID) {
        this.currentID = currentID;
    }

    public static String getCurrentLogin() {
        return currentLogin;
    }

    public static void setCurrentLogin(String currentLogin) {
        Session.currentLogin = currentLogin;
    }


}
