package network;

public class Session {

    private static Integer currentID;
    private static String currentLogin;
    static Session session;

    Session(String currentLogin,Integer currentID) {
            this.currentID = currentID;
            this.currentLogin = currentLogin;
    }

    public static Session getInstance(String currentLogin,Integer currentID) {
        if(currentLogin==null && currentID==null)
        {
            session=null;

        }
        else if(currentLogin != "notInitialize" && currentID !=-1)
        {
            if (session == null) {
                session = new Session(currentLogin,currentID);
            }
        }
        return session;
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
