package Server;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static int clients_count = 0;
    private Server server;
    private Socket clientSocket = null;

    public ClientHandler(Socket socket, Server server) {
        clients_count++;
        this.server = server;
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        System.out.println("aaa");
        try {
            System.out.println(clientSocket);
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            String query;
            System.out.println("Клиент подключился");
            System.out.println("Количество клиентов: " + clients_count);
            while (!clientSocket.isClosed()){
                System.out.println("Waiting for query");
                query = in.readUTF();
                System.out.println(query);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
