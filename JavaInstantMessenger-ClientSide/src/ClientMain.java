/**
 * Created by srv_twry on 17/3/17.
 */
import javax.swing.JFrame;

public class ClientMain {
    public static void main(String args[]){
        Client clientObject = new Client("127.0.0.1");
        clientObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientObject.startRunning();
    }
}
