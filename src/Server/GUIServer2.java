package Server;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Class.Packet;
import constant.Constant;

public class GUIServer2 extends JFrame {
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	private Connection connection;
	String path = "D:\\new_email";
	DatagramSocket serverSocket;
	int port = new Constant().PORT_SERVER;
	byte[] byte_recerive = new byte[1024];
	byte[] byte_send = new byte[1024];
	DatagramPacket datagramPacket_receive;
	Constant constant = new Constant();
    private JPanel contentPane;
    private JPanel panel_log;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JTextField txtIP;
    private JTextField txtport;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUIServer2 frame = new GUIServer2();
                    frame.setVisible(true);
                    
                    // Khởi chạy luồng log
                   
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GUIServer2() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\eclipse\\LTM\\MailServer\\image\\database-storage.png"));
        init();
   
    }


	public void receiveMail() {
		try {
			serverSocket.receive(datagramPacket_receive);
			Packet packet_receive = (Packet) deserialize(datagramPacket_receive.getData());
			int require = packet_receive.getDefine_require();
			InetAddress ip = datagramPacket_receive.getAddress();
			int port = datagramPacket_receive.getPort();
			

			if (require == constant.DEFINE_REQUIRE_LOGIN) {
				addLogMessage("Đã nhận yêu cầu đăng nhập từ client : " + datagramPacket_receive.getAddress());
				if (loginServer(packet_receive)) {
					sendResponse2("success", ip, port);
				} else {
					sendResponse2("failed", ip, port);
				}
			}
			if (require == constant.DEFINE_REQUIRE_REGISTER) {
				addLogMessage("Đã nhận yêu cầu đăng kí từ client : " + datagramPacket_receive.getAddress());
				if (registerServer(packet_receive)) {
					sendResponse2("success", ip, port);
				} else {
					sendResponse2("failed", ip, port);
				}

			}

			if( require == constant.DEFINE_REQUIRE_SENDMAIL) {
				addLogMessage("Đã nhận yêu cầu gửi mail từ client : " + datagramPacket_receive.getAddress());
				SendMail(packet_receive);
			}
			if (require == constant.DEFINE_REQUIRE_GETMESSSe) {
				addLogMessage("Yêu cầu xem Mail gửi từ client : " + datagramPacket_receive.getAddress());
				sendResponse(getMail(packet_receive.getName_send(), false), ip, port);
				for(String list: getMail(packet_receive.getName_send(), false)) {
					addLogMessage(list);
				}
			}
			if (require == constant.DEFINE_REQUIRE_GETMESSRe) {
				addLogMessage("Yêu cầu xem Mail nhận từ client : " + datagramPacket_receive.getAddress());
				sendResponse(getMail(packet_receive.getName_send(), true), ip, port);
//					
				for(String list: getMail(packet_receive.getName_send(), true)) {
					addLogMessage(list);
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void connectToDb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/servermail", "root", "Tranquyphat.1");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    public void init() {
    	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setBounds(100, 100, 534, 590);
         contentPane = new JPanel();
         contentPane.setBackground(Color.WHITE);
         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

         setContentPane(contentPane);
         contentPane.setLayout(new BorderLayout(0, 0));

         JPanel panel = new JPanel();
         panel.setBackground(Color.LIGHT_GRAY);
         contentPane.add(panel, BorderLayout.NORTH);
         panel.setLayout(new GridLayout(3, 1, 0, 0));

         JPanel pnlIP = new JPanel();
         panel.add(pnlIP);
         pnlIP.setLayout(new GridLayout(0, 2, 0, 0));

         JPanel panel_4 = new JPanel();
         panel_4.setBackground(UIManager.getColor("CheckBox.background"));
         pnlIP.add(panel_4);

         JLabel lblIP = new JLabel("IP Server");
         lblIP.setFont(new Font("Tahoma", Font.BOLD, 14));
         panel_4.add(lblIP);

         JPanel panel_5 = new JPanel();
         pnlIP.add(panel_5);

         txtIP = new JTextField();
         txtIP.setFont(new Font("Tahoma", Font.BOLD, 14));
         panel_5.add(txtIP);
         txtIP.setText(new Constant().IPAdrress);
         txtIP.setColumns(10);

         JPanel PnlPortParent = new JPanel();
         panel.add(PnlPortParent);
         PnlPortParent.setLayout(new GridLayout(0, 2, 0, 0));

         JPanel lbPort = new JPanel();
         lbPort.setBackground(UIManager.getColor("CheckBox.background"));
         PnlPortParent.add(lbPort);

         JLabel lblNewLabel_1 = new JLabel("Port");
         lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
         lbPort.add(lblNewLabel_1);

         JPanel pnlPort = new JPanel();
         PnlPortParent.add(pnlPort);

         txtport = new JTextField();
         txtport.setFont(new Font("Tahoma", Font.BOLD, 14));
         pnlPort.add(txtport);
         txtport.setColumns(10);
         txtport.setText(String.valueOf((new Constant().PORT_SERVER)));
         
         JPanel panel_1 = new JPanel();
         panel.add(panel_1);
         panel_1.setLayout(new GridLayout(0, 2, 0, 0));
         
         JButton btnNewButton = new JButton("Run server");
         btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
         panel_1.add(btnNewButton);
         
         JButton btnNewButton_1 = new JButton("Stop server");
         btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
         panel_1.add(btnNewButton_1);
         btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
         btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			     try {
						serverSocket = new DatagramSocket(port);
						datagramPacket_receive = new DatagramPacket(byte_recerive, byte_recerive.length);
						connectToDb();
						addLogMessage("Server đã chạy");

					} catch (SocketException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}

			        
					new Thread(new Runnable() {
						@Override
				        public void run() {
				            while (true) {
				                // Log một câu bất kỳ vào panel log
//				                guiServer.addLogMessage("hahaa");
				            	receiveMail();
				               
				            }
				        }
					}).start();
			}
		});
         txtport.enable(false);
        panel_log = new JPanel();
        panel_log.setBackground(Color.WHITE);
        panel_log.setLayout(new BoxLayout(panel_log, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panel_log);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
    public void addLogMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JLabel label = new JLabel(getCurrentDateTime()+" : "+message);
                label.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
                panel_log.add(label);
                panel_log.revalidate();
                panel_log.repaint();
            }
        });
    }


   

	public boolean loginServer(Packet packet) {
		connectToDb();
		String username = packet.getName_send();
		String password = packet.getOther();
		String sql = "SELECT * FROM user WHERE mailname = ? AND password = ?";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				addLogMessage("Client "+username+" đăng nhập thành công");
				// Đăng nhập thành công
				return true;
			}else {
				addLogMessage("Client "+username+" đăng nhập thất bại");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Đăng nhập thất bại
		return false;
	}
	public boolean registerServer(Packet packet) {
		connectToDb();


	    String[] arr = packet.getName_send().split(constant.SPLIT_S);
	    String fullname = arr[0];
	    String username = arr[1];
	    String pass = arr[2];

	    String query = "INSERT INTO user (mailname, password, fullname) VALUES (?, ?, ?)";
	    try {
	        PreparedStatement statement = connection.prepareStatement(query);

	        statement.setString(1, username);
	        statement.setString(2, pass);
	        statement.setString(3, fullname);

	        int rowsAffected = statement.executeUpdate();
	        statement.close();
	        connection.close();
	        if(rowsAffected>0) {
	        	 addLogMessage("User "+username+" đăng kí thành công!");
	        }else {
	        	addLogMessage("User "+username+" đăng kí thất bại!");
	        }
	       
	        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng, ngược lại trả về false
		} catch (SQLException e) {
			e.printStackTrace();
			 
			return false; // Lỗi xảy ra, trả về false
		}
	}

	public List<String> getMail(String userName, boolean isSearchByRecipient) {
	    try {
	        // Tạo kết nối tới cơ sở dữ liệu
	        connectToDb();

	        // Chuẩn bị truy vấn SQL
	        String query;
	        if (isSearchByRecipient) {
	            query = "SELECT * FROM mail WHERE user_receive = ?";
	        } else {
	            query = "SELECT * FROM mail WHERE user_send = ?";
	        }

	        statement = connection.prepareStatement(query);
	        statement.setString(1, userName);

	        resultSet = statement.executeQuery();

	        List<String> results = new ArrayList<>();

	        while (resultSet.next()) {
	        	String mailID = resultSet.getString("mail_ID");
	            String senderName = resultSet.getString("user_send");
	            String recipientName = resultSet.getString("user_receive");
	            String subject = resultSet.getString("subject");
	            String content = resultSet.getString("content");
	            String sentDate = resultSet.getString("date_send");

	            String result =mailID+new Constant().SPLIT_S + senderName + new Constant().SPLIT_S + recipientName + new Constant().SPLIT_S + subject
	                    + new Constant().SPLIT_S + content + new Constant().SPLIT_S + sentDate.toString();
	            if (isSearchByRecipient) {
	                results.add("recipient&&&" + result);
	            } else {
	                results.add("sender&&&" + result);
	            }
	        }

	        return results;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Đóng kết nối và giải phóng tài nguyên
	        try {
	            if (resultSet != null)
	                resultSet.close();
	            if (statement != null)
	                statement.close();
	            if (connection != null)
	                connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return null;
	}

	public boolean SendMail(Packet packet) {
	    connectToDb();

	    String namesend = packet.getName_send();
	    String nameReceive = packet.getName_recerive();
	    String subject = packet.getTitle();
	    String content = packet.getContent();
	    String dateSend = packet.getDate();

	    String query = "INSERT INTO mail (user_send, user_receive, subject, content, date_send) VALUES (?, ?, ?, ?, ?)";
	    try {
	        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

	        statement.setString(1,namesend);
	        statement.setString(2, nameReceive);
	        statement.setString(3, subject);
	        statement.setString(4, content);
	        statement.setString(5, dateSend);

	        int rowsAffected = statement.executeUpdate();

	        if (rowsAffected > 0) {
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int mailId = generatedKeys.getInt(1);
	                // Sử dụng giá trị mailId theo nhu cầu của bạn
	            }
	            generatedKeys.close();
	            addLogMessage("User "+namesend+" đã gửi mail đến "+nameReceive);
	        }
	        statement.close();
	        connection.close();
	        
	        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng, ngược lại trả về false
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Lỗi xảy ra, trả về false
	    }
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
	public void sendResponse2(String mess, InetAddress ip, int port) {

		byte[] sendData = new byte[1024];
		Packet packet = new Packet(mess);

		try {
			sendData = serialize((Object) packet);

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void sendResponse(List<String> mess, InetAddress ip, int port) {

		byte[] sendData = new byte[1024];
		Packet packet = new Packet(mess);
//		addLogMessage(mess);

		try {
			sendData = serialize((Object) packet);

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	  public static String getCurrentDateTime() {
	        // Lấy ngày giờ hiện tại
	        Date currentDate = new Date();
	        
	        // Định dạng ngày giờ
	        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
	        String formattedDateTime = dateFormat.format(currentDate);

	        return formattedDateTime;
	    }
}