import javax.swing.*;

/**
 * Created by srv_twry on 16/3/17.
 */
public class Main {
    public static void main(String args[]){
        Server serverObject = new Server();
        serverObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverObject.startRunning();
    }
}
