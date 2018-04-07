import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class ChatClient extends JFrame implements Runnable {

  Socket socket;
  JTextArea textArea;
  Thread thread;

  DataInputStream din;
  DataOutputStream dout;

  String LoginName;

  ChatClient(String login) throws IOException {
    super(login);
    LoginName = login;

    textArea = new JTextArea(18, 50);
    socket = new Socket("localhost", 4444);

    din = new DataInputStream(socket.getInputStream());
    dout = new DataOutputStream(socket.getOutputStream());

    dout.writeUTF(LoginName);
    dout.writeUTF(LoginName + " " + "LOGIN");

    thread = new Thread(this);
    thread.start();
    setup();
  }

  private void setup() {
    setSize(600, 400);
    JPanel panel = new JPanel();
    panel.add(new JScrollPane(textArea));
    add(panel);
    setVisible(true);
  }


  @Override
  public void run() {
    while(true) {
      try {
        textArea.append("\n" + din.readUTF());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws UnknownHostException, IOException {
    ChatClient client = new ChatClient("User1");
  }
}
