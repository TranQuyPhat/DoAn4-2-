package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ScrollPaneUI;

import Class.Client;
import Class.CustomCell;
import Class.HandleFile;
import Class.Packet;
import constant.Constant;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GuiClient extends JFrame implements ActionListener, FocusListener {

	DatagramSocket clientSocket;
	byte[] byte_recerive = new byte[1024];
	byte[] byte_send = new byte[1024];
	DatagramPacket datagramPacket_receive;
	Constant constant = new Constant();
	DefaultListModel<Packet> listModel = new DefaultListModel<>();
	JList<Packet> listnhan = new JList<>(listModel);
	JList<Packet> listgui = new JList<>(listModel);
	private JPanel contentPane;
	private Vector<Packet> vectorNhan = new Vector<Packet>();
	private Vector<Packet> vectorGui = new Vector<Packet>();
	JScrollPane scroll1;
	JScrollPane scroll2;
	private Vector<Packet> listData_Send = new Vector<Packet>();
	private JTextArea to;
	private JTextArea txt_Subject;
	private JButton btnSendMail, btnRefesh, btnMessSend;
//	Client client = null;
	String nameSend = "";
	String mesent = "";
	JTextArea text_Content;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiClient frame = new GuiClient("p");
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
	public GuiClient(String nameSend) {
		this.nameSend = nameSend;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 954, 567);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		try {// set icon giao dien---------------------------
			Image iconmes = ImageIO.read(new File(new Constant().LINK_PATH_IMAGE + "logoMail.jpg"));
			this.setIconImage(iconmes);
		} catch (IOException e1) {
			// TODO Auto-generated catch block

		}

		listgui = new JList(vectorGui);

		listgui.updateUI();
		listgui.setCellRenderer(new CustomCell());

		listnhan = new JList(vectorNhan);

		listnhan.updateUI();
		listnhan.setCellRenderer(new CustomCell());

		scroll1 = new JScrollPane(listnhan);
		scroll1.setBounds(145, 25, 525, 500);

		scroll1.setPreferredSize(getPreferredSize());
		scroll1.createVerticalScrollBar();
		contentPane.setLayout(null);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scroll1);
		scroll2 = new JScrollPane(listgui);
		scroll2.setBounds(145, 25, 525, 500);

		scroll2.setPreferredSize(getPreferredSize());
		scroll2.createVerticalScrollBar();
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scroll2);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 25, 140, 500);
		contentPane.add(panel_1);

		btnRefesh = new JButton("Mail Receive");
		btnRefesh.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnRefesh.setBounds(0, 0, 140, 46);
		btnRefesh.addActionListener(this);
		panel_1.setLayout(null);
		panel_1.add(btnRefesh);

		btnMessSend = new JButton("Maill Send");
		btnMessSend.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnMessSend.setBounds(0, 47, 140, 46);
		btnMessSend.addActionListener(this);
		panel_1.add(btnMessSend);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 0, 939, 25);
		contentPane.add(panel_2);
		try {
			BufferedImage bufferImage_hidden = ImageIO.read(new File(new Constant().LINK_PATH_IMAGE + "logoUser.png"));
			ImageIcon imageIcon_hidden = new ImageIcon(
					bufferImage_hidden.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panel_2.setLayout(null);

		JLabel lblUser = new JLabel("Phan van");
		lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblUser.setBounds(141, 0, 523, 25);
		lblUser.setText(nameSend);
		lblUser.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa ngang
		lblUser.setVerticalAlignment(SwingConstants.CENTER); // Căn giữa dọc
		panel_2.add(lblUser);

		JPanel panel = new JPanel();
		panel.setBounds(669, 33, 275, 492);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("New Mail");
		lblNewLabel_2.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel_2.setVerticalAlignment(JLabel.CENTER);
		lblNewLabel_2.setBounds(0, 0, 250, 21);
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panel.add(lblNewLabel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 20, 260, 414);
		panel.add(panel_3);
		panel_3.setLayout(null);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(0, 33, 240, 45);
		panel_3.add(panel_4);
		panel_4.setLayout(null);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 0, 240, 45);
		panel_4.add(scrollPane_2);

		to = new JTextArea("");
		scrollPane_2.setViewportView(to);
		to.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		to.addFocusListener(this);
		to.setForeground(Color.gray);
		to.setColumns(10);
		to.setLineWrap(true);
		to.setWrapStyleWord(true);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 182, 240, 91);
		panel_3.add(scrollPane);
		text_Content = new JTextArea("");
		text_Content.setColumns(10);
		text_Content.setForeground(Color.GRAY);
		text_Content.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		text_Content.setLineWrap(true);
		text_Content.setWrapStyleWord(true);
		scrollPane.setViewportView(text_Content);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 111, 240, 45);
		panel_3.add(scrollPane_1);

		txt_Subject = new JTextArea("");
		scrollPane_1.setViewportView(txt_Subject);
		txt_Subject.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txt_Subject.addFocusListener(this);
		txt_Subject.setForeground(Color.gray);
		txt_Subject.setColumns(10);
		txt_Subject.setLineWrap(true);
		txt_Subject.setWrapStyleWord(true);

		lblNewLabel_1 = new JLabel("Subject");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_1.setBounds(0, 88, 240, 20);
		panel_3.add(lblNewLabel_1);

		lblNewLabel = new JLabel("Content");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel.setBounds(0, 162, 240, 20);
		panel_3.add(lblNewLabel);

		lblNewLabel_3 = new JLabel("To");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_3.setBounds(0, 10, 240, 20);
		panel_3.add(lblNewLabel_3);

		btnSendMail = new JButton("Send");
		btnSendMail.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnSendMail.setBounds(10, 456, 260, 36);
		panel.add(btnSendMail);
		btnSendMail.addActionListener(this);
//		client = new Client() ;
//		InitGui();
//		new Thread(new Runnable() {
//		    @Override
//		    public void run() {
//		        while (true) {
//		            Packet packet = client.receiveMess(); 
//		            System.out.println("Client dang xu li nhan data tu server");
//		           
//		            if (packet == null) {
//		                System.out.println("kh nhan ");
//		                // break; // Dừng vòng lặp khi packet là null
//		            }
//
//		           
//		            	  vectorGui.addElement(packet);
//		            	  System.out.println("da nhan");
//		           
//		           // Thêm dữ liệu vào listModel
//		            
//		            // Cập nhật giao diện hiển thị
//		            SwingUtilities.invokeLater(new Runnable() {
//		                @Override
//		                public void run() {
//		                    listgui.updateUI();
//		                }
//		            });
//		        }
//		    }
//		}).start();
//		new Thread(new Runnable() {
//			   
//			@Override
//			public void run() {
//			    while (true) {
//			        Packet packet = client.receiveMess(); 
//			        if (packet == null) {
//			        	System.out.println("kh nhan ");
////			            break; // Dừng vòng lặp khi packet là null
//			        }
//			        
//			        listData.add(packet);
//			        list.updateUI(); 
//
//			    }
//			}
//		}).start();

//		list2.addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//				JList theList = (JList) e.getSource(); 
//
//				int index = theList.locationToIndex(e.getPoint()); 
//				Packet packetSelect = (Packet)theList.getModel().getElementAt(index);
//				GuiContent content  = new  GuiContent(packetSelect);
//				content.setVisible(true);
//			}
//		});
//		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnSendMail)) {
			Packet packet_send = new Packet(new Constant().DEFINE_REQUIRE_SENDMAIL, this.nameSend,
					to.getText(), txt_Subject.getText(), text_Content.getText(), getDateTime(), null, null);
			sendRequest(packet_send);
		

		}
		if (e.getSource().equals(btnRefesh)) {
			contentPane.remove(scroll2);
			contentPane.add(scroll1, BorderLayout.CENTER);
			contentPane.revalidate();
			contentPane.repaint();
			InitGui();
		}
		if (e.getSource().equals(btnMessSend)) {
			contentPane.remove(scroll1);
			contentPane.add(scroll2, BorderLayout.CENTER);
			contentPane.revalidate();
			contentPane.repaint();
			getMessSend();
		}
	}

	public void InitGui() {
		vectorNhan.removeAllElements();
		Packet packet_receive= getMess(new Constant().DEFINE_REQUIRE_GETMESSRe);
		if(packet_receive!=null) {
//			
			for(String s: packet_receive.getMess()) {
				Packet pk= new Packet(s);
				System.out.println(pk.getMessSent());
				vectorNhan.addElement(pk);
			}
//			vectorNhan.addElement(packet_receive);
			listnhan.updateUI();
		}else {
			System.out.println("null");
		}
		

	}

	public void getMessSend() {
		vectorGui.removeAllElements();
		Packet packet_receive= getMess(new Constant().DEFINE_REQUIRE_GETMESSSe);
		if(packet_receive!=null) {
			for(String s: packet_receive.getMess()) {
				Packet pk= new Packet(s);
				System.out.println(pk.getMessSent());
			vectorGui.addElement(pk);
			}
			  listgui.updateUI();
		}else {
			System.out.println("null");
		}

	}

	// get thá»�i gian hiá»‡n táº¡i
	public String getDateTime() {
		String d = String.valueOf(java.time.LocalDate.now());
		String h = String.valueOf(java.time.LocalTime.now());
		String[] timeArr = h.split("\\.");
		System.out.print(h);
		System.out.println(timeArr.length);
		return d + " " + timeArr[0];
	}

	public Packet getMess(int request) {

		try {
			byte[] receiveData = new byte[1024];
			
			String username = this.nameSend;
			// Sử dụng InetAddress để lấy địa chỉ IP của máy hiện tại

			Packet packet = new Packet(request, username, "");
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

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(to)) {
			if (to.getText().equals("To")) {
				to.setText("");
				to.setForeground(Color.black);
			}

		}
		if (e.getSource().equals(txt_Subject)) {
			if (txt_Subject.getText().equals("Subject")) {
				txt_Subject.setText("");
				txt_Subject.setForeground(Color.black);
			}

		}
		if (e.getSource().equals(text_Content)) {
			if (text_Content.getText().equals("Content")) {
				text_Content.setText("");
				text_Content.setForeground(Color.black);
			}

		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(to)) {
			if (to.getText().equals("")) {
				to.setText("To");
				to.setForeground(Color.gray);
			}
		}
		if (e.getSource().equals(txt_Subject)) {
			if (txt_Subject.getText().equals("")) {
				txt_Subject.setText("Subject");
				txt_Subject.setForeground(Color.gray);
			}

		}
		if (e.getSource().equals(text_Content)) {
			if (text_Content.getText().equals("")) {
				text_Content.setText("Content");
				text_Content.setForeground(Color.gray);
			}

		}
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
	public void response(Packet packet) {
		
	}
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}
}

class ContentCell implements ListCellRenderer {
	boolean ok = true;
	int index1 = -1;

	@Override
	public JPanel getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		// TODO Auto-generated method stub
		Packet packet = (Packet) value;
		JPanel panel = new JPanel();
		JTextArea text = new JTextArea("xin Ã ljasdlkf");
		panel.add(text);
		return panel;

	}

}
