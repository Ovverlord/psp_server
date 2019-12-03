package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main{
//    public static Stage mainStage;
//
//
//    private void setPrimaryStage(Stage stage) {
//        Main.mainStage = stage;
//    }
//
//    static public Stage getPrimaryStage() {
//        return Main.mainStage;
//    }
//
//    public static void main(String[] args)
//    {
//        launch(args);
//    }
//
//    public void start(Stage stage) throws Exception {
//        //Connection obj = Connection.getInstance();
//        mainStage = stage;
//        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../sample/forms/ServerWindow.fxml")));
//        mainStage.setTitle("Сервер");
//        mainStage.setScene(scene);
//        mainStage.getIcons().add(new Image("resources/images/Server.png"));
//        mainStage.setResizable(false);
//        mainStage.show();

//        mainStage.setOnCloseRequest(windowEvent ->
//        {
//            try
//            {
//                Connection serverSocket = Connection.getInstance().close();
//            }
//            catch (Exception ex)
//            {
//                System.out.println(ex);
//            }
//        });
   // }
    public static void main(String[] args) {
        Server server = new Server();
    }
}