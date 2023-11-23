package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Class.Packet;
import constant.Constant;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class GuiContent extends JFrame {
	private DatagramSocket clientSocket;
	private JPanel contentPane;
	String id= "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiContent frame = new GuiContent(new Packet("phan"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiContent(Packet packet) {
	    String packetData = packet.getMessSent();
	    // Phân tách các giá trị bằng dấu "&&&"
	    String[] values = packetData.split("&&&");

	    // Lấy các giá trị từ mảng
	    String type = values[0];
	    String mailID = values[1];
	    String namesend = values[2];
	    String namere = values[3];
	    String subject = values[4];
	    String content = values[5];
	    String date = values[6];
	    System.out.println(mailID);
	    String name= "";
	    if(type.equals("recipient")) {
	    	name=namesend;
	    }else {
	    	name=namere;
	    }
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new GridLayout(4,1));
		panel.add(panel_3, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_3.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUserName = new JLabel("From : ");
		panel_2.add(lblUserName, BorderLayout.WEST);
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1 = new JLabel(namesend);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2.add(lblNewLabel_1);
		
		JPanel panel_2_1 = new JPanel();
		panel_3.add(panel_2_1);
		panel_2_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1_1 = new JLabel(namere);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2_1.add(lblNewLabel_1_1, BorderLayout.CENTER);
		
		JLabel lblUserName_1 = new JLabel("To : ");
		lblUserName_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2_1.add(lblUserName_1, BorderLayout.WEST);
		
		JPanel panel_2_2 = new JPanel();
		panel_3.add(panel_2_2);
		panel_2_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUserName_1_1 = new JLabel("Date : ");
		lblUserName_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2_2.add(lblUserName_1_1, BorderLayout.WEST);
		
		JLabel lblNewLabel_1_1_1 = new JLabel(date);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2_2.add(lblNewLabel_1_1_1, BorderLayout.CENTER);
		
		JPanel panel_2_3 = new JPanel();
		panel_3.add(panel_2_3);
		panel_2_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUserName_1_2 = new JLabel("Subject : ");
		lblUserName_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2_3.add(lblUserName_1_2, BorderLayout.WEST);
		
		JLabel lblNewLabel_1_1_2 = new JLabel(subject);
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2_3.add(lblNewLabel_1_1_2, BorderLayout.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6, BorderLayout.EAST);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_6.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel deletelbl = new JLabel("");
		deletelbl.setIcon(new ImageIcon("D:\\eclipse\\LTM\\MailServer\\image\\delete (2).png"));
		deletelbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		panel_4.add(deletelbl);
		deletelbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int result = JOptionPane.showConfirmDialog(null, "Bạn muốn xoá mail?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                	Packet packet_receive= getMess(new Constant().DEFINE_REQUIRE_DELETEMAILbyID,mailID,type);
                	System.out.println(packet_receive.getDefine_require()+" "+packet_receive.getName_send());
                } else if (result == JOptionPane.NO_OPTION) {
                	JOptionPane.getRootFrame().dispose();
                }
            }
        });
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel(content);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		lblNewLabel_2.setBackground(Color.WHITE);
		panel_1.add(lblNewLabel_2);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				System.exit(0);
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void sendRequest(Packet packet) {
		byte[] receiveData = new byte[1024];
		try {
			clientSocket = new DatagramSocket();
			byte[] sendData = new byte[1024];
			// Sử dụng InetAddress để lấy địa chỉ IP của máy hiện tại

			sendData = serialize((Object) packet);
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
					InetAddress.getByName(new Constant().IPAdrress), new Constant().PORT_SERVER);
			clientSocket.send(sendPacket);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public Packet getMess(int request,String id,String type) {

		try {
			byte[] receiveData = new byte[1024];
			
			// Sử dụng InetAddress để lấy địa chỉ IP của máy hiện tại
			System.out.println(request + " "+id+" "+type);
			Packet packet = new Packet(request, id, type);
			sendRequest(packet);
			// Nhận phản hồi từ server
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);

			try {
				Packet packet_receive = (Packet) deserialize(receivePacket.getData());
				
			
				return packet_receive;
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			clientSocket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    
}
