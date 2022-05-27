package infra.network;

import domain.repository.AccountRepository;
import domain.repository.PetPlantRepository;
import domain.repository.PlantRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//각 클라이언트와 TCP통신을 연결하고, 쓰레드를 생성하는 객체
public class Listener {
    private final AccountRepository accRepo;
    private final PlantRepository plantRepo;
    private final PetPlantRepository petPlantRepo;

    public Listener(AccountRepository accRepo, PlantRepository plantRepo, PetPlantRepository petPlantRepo){
        this.accRepo = accRepo;
        this.plantRepo = plantRepo;
        this.petPlantRepo = petPlantRepo;

        try{
            serverSocket = new ServerSocket(3000);
            clients = new Server[50];
            clientCount = 0;
        }catch(Exception e){
            e.getStackTrace();
        }
    }

    private static ServerSocket serverSocket;
    private static Server clients[];    // MainController는 thread를 상속받음. 스레드 배열.
    private static int clientCount; // 연결된 클라이언트 개수

    // 구동
    public void run() {
        System.out.println("Server running ...");
        while (serverSocket != null) {
            try {
                // 소켓연결
                Socket socket = serverSocket.accept();
                System.out.println("Success socket connection");
                // 스레드 생성
                addThread(socket);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    // 스레드 생성
    public synchronized void addThread(Socket socket) throws IOException {
        // 최대 스레드 개수를 넘지 않을 때만
        if (clientCount < clients.length) {
            clientCount++;
            Server thread = new Server(socket, clientCount, accRepo, plantRepo, petPlantRepo);
            clients[clientCount] = thread;     // 스레드 배열에 생성한 스레드 추가
            System.out.println("Create thread : clientCount = " + clientCount);
            System.out.println("client Port : " + thread.getClientID());
            thread.start(); // 스레드 run

        } else {
            System.out.println("Client refused: maximum " + clients.length + " reached.");
            socket.close();
        }
    }

    // clients 배열에서 해당 ID(port)를 가진 client pos 리턴
    public static int findClient(int ID) {
        for (int i = 0; i < clientCount; i++)
            if (clients[i].getClientID() == ID)
                return i;
        return -1;
    }

    // 쓰레드 지우기
    public synchronized static void removeThread(int ID) {
        int pos = findClient(ID);
        if (pos >= 0) {
            if (pos < clientCount - 1){
                for (int i = pos + 1; i < clientCount; i++){
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
        }
    }

}