package infra.network;

import controller.MainController;
import domain.repository.AccountRepository;
import domain.repository.PetPlantRepository;
import domain.repository.PlantRepository;
import domain.repository.WateringRepository;

import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    private Socket soc;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private MainController mainController;
    private int threadID;
    private boolean running;

    public Server(Socket socket, int id, AccountRepository accRepo, PlantRepository plantRepo, PetPlantRepository petPlantRepo, WateringRepository wateringRepo) {
        soc = socket;
        threadID = id;
        mainController = new MainController(accRepo, plantRepo, petPlantRepo, wateringRepo);
        try{
            is = new ObjectInputStream(
                    soc.getInputStream()
            );
            os = new ObjectOutputStream(
                    soc.getOutputStream()
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
                System.out.println("entry");
                Response res = mainController.handle((Request) is.readObject());
                os.writeObject(res);
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
