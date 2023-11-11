package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
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
import java.security.MessageDigest;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Class.Packet;
import gui.GuiClient;
import constant.Constant;

public class GuiLogin extends JFrame implements ActionListener, FocusListener {
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private ImageIcon imageIcon_show, imageIcon_hidden;
	private boolean iconPass = true; // hidden
	JLabel lblWarningUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiLogin frame = new GuiLogin();
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
	public GuiLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 450, 300);
		setTitle("RMI");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		try {// set icon giao dien---------------------------
			Image iconmes = ImageIO.read(new File(new Constant().LINK_PATH_IMAGE + "logoMail.jpg"));
			this.setIconImage(iconmes);
		} catch (IOException e1) {
			// TODO Auto-generated catch block

		}
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 5, 426, 258);
		contentPane.add(panel);

		txtUsername = new JTextField();
		txtUsername.setBounds(187, 70, 196, 32);
		txtUsername.setHorizontalAlignment(JTextField.CENTER);
		txtUsername.setText("Type your username");
		txtUsername.setForeground(Color.gray);
		txtUsername.addFocusListener(this);
		panel.setLayout(null);
		panel.add(txtUsername);
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(187, 144, 196, 32);
		txtPassword.setHorizontalAlignment(JTextField.CENTER);
		txtPassword.setText("Type your password");
		txtPassword.setForeground(Color.gray);
		txtPassword.setEchoChar('\0');
		txtPassword.addFocusListener(this);
		panel.add(txtPassword);
		txtPassword.setColumns(10);

		JLabel lbUsername = new JLabel("Username: ");
		lbUsername.setBounds(65, 67, 112, 32);
		lbUsername.setForeground(new Color(0, 128, 0));
		lbUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lbUsername);

		JLabel lbPassword = new JLabel("Password: ");
		lbPassword.setBounds(65, 141, 114, 32);
		lbPassword.setForeground(new Color(0, 128, 0));
		lbPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lbPassword);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(110, 10, 196, 47);
		lblNewLabel.setForeground(new Color(34, 139, 34));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(lblNewLabel);

		JLabel lbIconPassword = new JLabel("");
		lbIconPassword.setBounds(385, 156, 45, 13);
		panel.add(lbIconPassword);

		try {
			BufferedImage bufferImage = ImageIO.read(new File(new Constant().LINK_PATH_IMAGE + "eye-look.png"));
			imageIcon_show = new ImageIcon(bufferImage.getScaledInstance(21, 15, Image.SCALE_SMOOTH));

			BufferedImage bufferImage_hidden = ImageIO
					.read(new File(new Constant().LINK_PATH_IMAGE + "hide-private-hidden.png"));
			imageIcon_hidden = new ImageIcon(bufferImage_hidden.getScaledInstance(21, 15, Image.SCALE_SMOOTH));
			lbIconPassword.setIcon(imageIcon_hidden);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		lblWarningUsername = new JLabel("username already in use");
		lblWarningUsername.setBounds(187, 53, 208, 13);
		lblWarningUsername.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblWarningUsername.setForeground(Color.RED);
		lblWarningUsername.setVisible(false);
		panel.add(lblWarningUsername);

		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(new Color(0, 128, 0));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogin.setBounds(47, 206, 104, 21);
		panel.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					try {	
								DatagramSocket clientSocket = new DatagramSocket();
								byte[] sendData = new byte[1024];

					            byte[] receiveData = new byte[1024];
								Packet packet = new Packet(
										new Constant().DEFINE_REQUIRE_LOGIN, txtUsername.getText() ,new String(txtPassword.getPassword()));							
									sendData = serialize((Object) packet);
								
								DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
										InetAddress.getByName(new Constant().IPAdrress), new Constant().PORT_SERVER);
									
									System.out.println(sendPacket.getPort());
								clientSocket.send(sendPacket);
								
								 // Nhận phản hồi từ server
					            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					            clientSocket.receive(receivePacket);
					            
					            
					            try {
									Packet packet_receive = (Packet)deserialize(receivePacket.getData());
									String response= packet_receive.getMessSent();
								            // Xử lý phản hồi từ server
								            if (response.equals("success")) {
								                JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");

									            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(btnLogin);
									            currentFrame.dispose();
									            new GuiClient(txtUsername.getText()).setVisible(true);
								                // Xử lý khi đăng nhập thành công
								            } else if (response.equals("failed")) {
								                JOptionPane.showMessageDialog(null, "Đăng nhập thất bại!");
								                // Xử lý khi đăng nhập thất bại
								            }
					            } catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

					          

										clientSocket.close();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								


			}
		});

		JButton btnRegister = new JButton("Register");
		btnRegister.setForeground(new Color(0, 128, 0));
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRegister.setBounds(243, 206, 104, 21);
		panel.add(btnRegister);

		lbIconPassword.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (iconPass) {
					lbIconPassword.setIcon(imageIcon_show);
					iconPass = !iconPass;
					txtPassword.setEchoChar('\0');
				} else {
					lbIconPassword.setIcon(imageIcon_hidden);
					iconPass = !iconPass;
					if (txtPassword.getText().equals("Type your password")) {
						txtPassword.setEchoChar('\0');
					} else {
						txtPassword.setEchoChar('*');
					}
				}
			}
		});
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(txtUsername)) {
			if (txtUsername.getText().equals("Type your username")) {
				txtUsername.setText("");
				txtUsername.setForeground(Color.black);
			}
		}
		if (e.getSource().equals(txtPassword)) {
			if (txtPassword.getText().equals("Type your password")) {
				txtPassword.setText("");
				txtPassword.setForeground(Color.black);
				txtPassword.setEchoChar('*');
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(txtUsername)) {
			if (txtUsername.getText().equals("")) {
				txtUsername.setText("Type your username");
				txtUsername.setForeground(Color.gray);
			}
		}
		if (e.getSource().equals(txtPassword)) {
			if (txtPassword.getText().equals("")) {
				txtPassword.setText("Type your password");
				txtPassword.setForeground(Color.gray);
				txtPassword.setEchoChar('\0');
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public String scryptWithMD5(String pass) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] passBytes = pass.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; i++) {
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return null;
	}

	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}
	  public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	        ByteArrayInputStream in = new ByteArrayInputStream(data);
	        ObjectInputStream is = new ObjectInputStream(in);
	        return is.readObject();
	    }
}
