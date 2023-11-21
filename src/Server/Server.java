package Server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import Class.Packet;
import constant.Constant;

public class Server {
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

	public Server() {
		try {
			serverSocket = new DatagramSocket(port);
			datagramPacket_receive = new DatagramPacket(byte_recerive, byte_recerive.length);
			connectToDb();
			System.out.println("server is running");

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					receiveMail();

				}
			}
		}).start();

	}

	public static void main(String args[]) throws Exception {
		new Server();

	}

	public void connectToDb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/servermail", "root", "Tranquyphat.1");
			System.out.println("Connected");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void receiveMail() {
		try {
			serverSocket.receive(datagramPacket_receive);
			Packet packet_receive = (Packet) deserialize(datagramPacket_receive.getData());
			int require = packet_receive.getDefine_require();
			InetAddress ip = datagramPacket_receive.getAddress();
			int port = datagramPacket_receive.getPort();
			System.out.println("Da nhan yeu cau tu client " + datagramPacket_receive.getAddress());

			if (require == constant.DEFINE_REQUIRE_LOGIN) {
				if (loginServer(packet_receive)) {

					sendResponse2("success", ip, port);
				} else {
					sendResponse2("failed", ip, port);
				}
			}
			if (require == constant.DEFINE_REQUIRE_REGISTER) {
				if (registerServer(packet_receive)) {
					sendResponse2("success", ip, port);
				} else {
					sendResponse2("failed", ip, port);
				}

			}

			if( require == constant.DEFINE_REQUIRE_SENDMAIL) {
				
				
				SendMail(packet_receive);
			}
			if (require == constant.DEFINE_REQUIRE_GETMESSSe) {

				sendResponse(getMail(packet_receive.getName_send(), false), ip, port);
				for(String list: getMail(packet_receive.getName_send(), false)) {
					System.out.println(list);
				}
			}
			if (require == constant.DEFINE_REQUIRE_GETMESSRe) {
				sendResponse(getMail(packet_receive.getName_send(), true), ip, port);
//					
				for(String list: getMail(packet_receive.getName_send(), true)) {
					System.out.println(list);
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//    public void sendMail(Packet packet, InetAddress inetAddress, int port) {
//    	try {
//			byte_send = serialize((Object) packet) ;
//	       	DatagramPacket sendPacket =   new DatagramPacket(byte_send, byte_send.length, InetAddress.getByName(packet.getIP_client()), port);
//	       	
//	       	//Gui dl lai cho client
//	       	serverSocket.send(sendPacket);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//    	
//
//    }

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
	            String senderName = resultSet.getString("user_send");
	            String recipientName = resultSet.getString("user_receive");
	            String subject = resultSet.getString("subject");
	            String content = resultSet.getString("content");
	            String sentDate = resultSet.getString("date_send");

	            String result = senderName + new Constant().SPLIT_S + recipientName + new Constant().SPLIT_S + subject
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

	        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng, ngược lại trả về false
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Lỗi xảy ra, trả về false
		}
	}

	public boolean loginServer(Packet packet) {
		connectToDb();
		String username = packet.getName_send();
		String password = packet.getOther();
		System.out.println("Login : " + username + " " + password);
		String sql = "SELECT * FROM user WHERE mailname = ? AND password = ?";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				// Đăng nhập thành công
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Đăng nhập thất bại
		return false;
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

	        statement.setString(1, namesend);
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
		System.out.println(mess);

		try {
			sendData = serialize((Object) packet);

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
			serverSocket.send(sendPacket);
			System.out.println("gui thanh cong qua " + ip + "  " + port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void sendResponse(List<String> mess, InetAddress ip, int port) {

		byte[] sendData = new byte[1024];
		Packet packet = new Packet(mess);
		System.out.println(mess);

		try {
			sendData = serialize((Object) packet);

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
			serverSocket.send(sendPacket);
			System.out.println("gui thanh cong qua " + ip + "  " + port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}