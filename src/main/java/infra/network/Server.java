package infra.network;

import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    private Socket soc;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private int threadID;
    private boolean running;

    public Server(Socket socket, int id) {
        soc = socket;
        threadID = id;
        try{
            is = new ObjectInputStream(
                    soc.getInputStream()
//                    new BufferedInputStream(soc.getInputStream())
            );
            os = new ObjectOutputStream(
                    soc.getOutputStream()
//                    new BufferedOutputStream(soc.getOutputStream())
            );
        } catch (IOException e) {
            e.printStackTrace();
            exit();
        }
    }

    @Override
    public void run() {
        super.run();
        running = true;

        while(running){
            try{
                System.out.println("hello thread");
                Request req = (Request)is.readObject();
                System.out.println(req.url);
            } catch (IOException e) {
                e.printStackTrace();
                exit();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public int getClientID() { //TODO : ??
        return threadID;
    }

    private void exit() {
        running = false;
        try {
            soc.close();
            Listener.removeThread(threadID);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
