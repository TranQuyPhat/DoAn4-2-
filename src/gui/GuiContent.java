package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Class.Packet;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class GuiContent extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					GuiContent frame = new GuiContent(new Packet("phan","",  "phjfl", "jlskadjf", "Mail", null, null));
//					frame.setVisible(true);
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
		panel.setLayout(new GridLayout(1,2));
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new GridLayout(4,1));
		panel.add(panel_3);
		
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
		panel.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_6.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel deletelbl = new JLabel("");
		deletelbl.setIcon(new ImageIcon("D:\\eclipse\\LTM\\MailServer\\image\\delete (2).png"));
		deletelbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		panel_4.add(deletelbl);
	
		
		
		
		
	
		
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

}
