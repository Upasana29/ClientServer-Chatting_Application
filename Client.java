import java.awt.Font;
import java.awt.BorderLayout;
import java.io.*;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.*;
import java.awt.Color;

public class Client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    //Declare Component to add GUI
     private JLabel heading = new JLabel("Client Area");
private JTextArea meassageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
     private Font font = new Font("ROBOTO",Font.BOLD,12);
     
     

    

    public Client(){
        try{
            System.out.println("sending request to server");
            socket = new Socket("127.0.0.1",7447);
            System.out.println("connection done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter (socket.getOutputStream());
            
            createGUI();
            handelEvents();
            startReading();
             
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
        private void handelEvents() {
            messageInput.addKeyListener(new KeyListener(){

                @Override
                public void keyTyped(KeyEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    // TODO Auto-generated method stub
                    //System.out.println("Key released"+e.getKeyCode());
                    if(e.getKeyCode()==10){
                    System.out.println("you have pressed enter button");
                    String ContenttoSend = messageInput.getText();
                    meassageArea.append("Client:"+ContenttoSend+"\n");
                    out.println(ContenttoSend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                    }
                    
                }

            });
    }
        public void createGUI()
        {
            
            //code to create GUI
            this.setTitle("ClientMessage[END]");
            this.setSize(300,450);
            this.setLocation(700,200);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            heading.setFont(font);
            meassageArea.setFont(font);
            messageInput.setFont(font);
            heading.setIcon(new ImageIcon("women_1.jpg"));
            heading.setForeground(Color.WHITE);
            heading.setHorizontalAlignment(SwingConstants.CENTER);
            heading.setHorizontalTextPosition(SwingConstants.CENTER);
            heading.setVerticalTextPosition(SwingConstants.BOTTOM);
            heading.setBackground( new Color(7,94,84));
            heading.setOpaque(true);
            heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20 ));
            meassageArea.setEditable(false);
            meassageArea.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 20 ));
           // meassageArea.setBounds(4, 30, 280, 400);

            meassageArea.setEditable(false);
            messageInput.setHorizontalAlignment(SwingConstants.LEFT);
             this.setLayout(new BorderLayout());
             this.add(heading,BorderLayout.NORTH);
            //adding the component into frame
            JScrollPane jScrollPane= new JScrollPane(meassageArea);
            this.add(jScrollPane,BorderLayout.CENTER);
            this.add(messageInput,BorderLayout.SOUTH);
            this.setVisible(true);
            
            

            
        }
        public void startReading(){
            // thread- use to read data 
            Runnable r1=()->{
                System.out.println("reading started");
    try{
                while(true){
                    
                    String msg = br.readLine();
                    if(msg.equals("over")){
                        System.out.println("server over/terminate the connection");
                        JOptionPane.showMessageDialog(this,"client terminated the chat");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    meassageArea.append("Server: "+msg+"\n");
                   // System.out.println("Server:"+msg);
                }
               
            }
            catch(Exception e){
                //e.printStackTrace();
                System.out.println("connection closed");
            }
            };
            new Thread(r1).start();
    
        }
    
        /*public void startWriting(){
            // thread- send data to the client
            //lambda expression
            System.out.println("writer started");
            Runnable r2=()->{
              try{
                while(!socket.isClosed()){
                    
                    BufferedReader br2= new BufferedReader(new InputStreamReader(System.in));
                    String content = br2.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("over")){
                        socket.close();
                        break;
                    }
                    //System.out.println("connection closed");
                }
                
                
            }
            catch(Exception e){
               // e.printStackTrace();
               System.out.println("connection closed");
            }
            };
            new Thread(r2).start();
        }*/
    
    public static void main(String args[]){
        System.out.println("hello-- client");
        new Client();
    }
    
    
}
