import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerSocketTask11 {
    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    private static Bus[] busses;

    public void start(int port) {
        try {

            server = new ServerSocket(port);

            while (true) {
                System.out.println("Waiting for a client ...");
                socket = server.accept();
                System.out.println("Client connected");

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (processQuery()) ;
            }
        } catch (IOException e) {
            System.out.println("Error >>     " + e.getMessage());
        }
    }

    private boolean processQuery() {
        int compCode = 0;
        try {
            String query = in.readLine();

            if (query == null)
                return false;

            String[] requestData = query.split(",");

            String response = compCode + "#";

            switch (requestData[0])
            {
                case "routeNum":
                    for (Bus buss : busses) {
                        if (buss.route == Integer.parseInt(requestData[1])) {
                            response += query + "#" + buss.toString() + "\n";
                        }
                    }
                    break;
                case "olderThan":
                    for (Bus buss : busses) {
                        if (buss.age >= Integer.parseInt(requestData[1])) {
                            response += query + "#" + buss.toString() + "\n";
                        }
                    }
                    break;
                case "mileageBiggerThan":
                    for (Bus buss : busses) {
                        if (buss.mileage >= Integer.parseInt(requestData[1])) {
                            response += query + "#" + buss.toString() + "\n";
                        }
                    }
                    break;
            }


            out.println(response);
            return true;
        } catch (IOException e) {
            System.out.println("Error >>     " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        busses = new Bus[6];

        busses[0] = new Bus("Nikita", "Toyota", 13, 10, 300000);
        busses[1] = new Bus("Vasya", "Porshe", 11, 2, 20000);
        busses[2] = new Bus("Stepan", "PAZ", 155, 55, 400000);
        busses[3] = new Bus("Petya", "MAZ", 56, 60, 1000000);
        busses[4] = new Bus("Lera", "VAZ", 13, 40, 350000);
        busses[5] = new Bus("Vasylysa", "ZAZ", 33, 52, 200000);

        ServerSocketTask11 srv = new ServerSocketTask11();
        srv.start(7272);
    }
}
