/**
 * Created by srv_twry on 17/3/17.
 */
import com.sun.xml.internal.ws.api.model.MEP;
import sun.security.x509.IPAddressName;

import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame {
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message="";
    private String serverIP;
    private Socket connection;

    //constructor
    public Client(String host){
        super("Client System");
        serverIP = host;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText,BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow),BorderLayout.CENTER);
        setSize(450,200);
        setVisible(true);
    }

    //setting up the server
    public void startRunning(){
        try{
            connectToServer();
            setUpStreams();
            whileChatting();
        }catch (EOFException eofException){
            showMessage("\n Client disconnected the connection");
        }catch (IOException ioException){
            ioException.printStackTrace();
        }finally {
            closeConnnection();
        }
    }

    //connect to server
    private void connectToServer() throws IOException{
        showMessage("\n Attempting to connect... \n ");
        connection = new Socket(InetAddress.getByName(serverIP),1234);
        showMessage("\n Connected to "+ connection.getInetAddress().getHostName());
    }

    //setting up the streams to send and receive messages
    private void setUpStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Your streams are now good to go! \n");
    }

    //while chatting with server
    private  void whileChatting() throws IOException{
        ableToType(true);
        do {
            try{
                message = (String) input.readObject();
                showMessage("\n"+ message);

            }catch (ClassNotFoundException classNotFoundException){
                showMessage("\n message isn't recognisable \n");
            }

        }while (!message.equals("SERVER - END"));
    }

    //close the streams and sockets
    private  void closeConnnection(){
        showMessage("\n Closing the chat \n");
        ableToType(false);
        try{
            input.close();
            output.close();
            connection.close();
        }catch (IOException ioExceptiom){
            ioExceptiom.printStackTrace();
        }
    }

    //sending messages
    private  void sendMessage(String message){
        try{
            output.writeObject("CLIENT - "+message);
            output.flush();
            showMessage("\n CLIENT - "+ message);
        }catch (IOException ioException){
            chatWindow.append("\n Something messed up\n");
        }
    }

    //change or update the chatwindow area
    private void showMessage(final String message){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(message);
                    }
                }
        );
    }

    //gives user permission to type
    private  void ableToType(boolean tof){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        userText.setEditable(tof);
                    }
                }
        );
    }


}
