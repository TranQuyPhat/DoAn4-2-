package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Class.Packet;
import constant.Constant;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JLabel;

public class GuiPacket extends JPanel {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame b = new JFrame();
					GuiPacket JPanel = new GuiPacket(new Packet("phan"));
					
					
					b.getContentPane().add(JPanel);
					b.setVisible(true);
					b.setSize(500,500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiPacket(Packet packet) {
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
	    setBounds(100, 100, 453, 53);
	    contentPane = new JPanel();
	    contentPane.setLayout(new BorderLayout(0, 0));
	    
	    JPanel panel = new JPanel();
	    contentPane.add(panel, BorderLayout.WEST);
	    		
	    	    JLabel lblName = new JLabel("Phan V\u0102n PH\u00F9ng");
	    	    
	    	    panel.add(lblName);
	    	    lblName.setBackground(Color.white);
	    
	    JPanel panel_2 = new JPanel();
	    contentPane.add(panel_2, BorderLayout.CENTER);
	    lblName.setBounds(0,0,200,50);
	    	    lblName.setText("     "+name+"     ");
	    	    panel_2.setLayout(new BorderLayout(0, 0));
	    
	    	    JLabel lblSubject = new JLabel("");
	    	    panel_2.add(lblSubject, BorderLayout.WEST);
	    	    lblSubject.setBackground(Color.white);
	    lblSubject.setText("    "+subject);

	    JLabel lblTitle = new JLabel("v\u1EAFn b\u1EA3n n\u00E0y d\u00F9ng \u0111\u1EC3 text");
	    panel_2.add(lblTitle);
	    lblTitle.setBackground(Color.white);
	    lblTitle.setAlignmentX(5000);
	    
	    JPanel panel_3 = new JPanel();
	    contentPane.add(panel_3, BorderLayout.EAST);
	    lblTitle.setText("    "+content);

	    JLabel lblDate = new JLabel("12/5/2001");
	    panel_3.add(lblDate);
	    lblDate.setBackground(Color.white);
	    lblDate.setText(date);
	    this.setLayout(new BorderLayout());
	    this.add(contentPane, BorderLayout.CENTER);
	    this.setVisible(true);
	}
}