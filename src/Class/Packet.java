package Class;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Packet implements Serializable {
	private int define_require; 
	private String name_send; 
	private String name_recerive;
	private String title ; 
	private String date ; 
	private String content; 
	private String File ; 
	private String FileImage;
	private String Other;
	private String messSent;
	private String messReceive;
	
	public Packet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Packet(int define_require,String name,String other) {
		super();
		this.define_require = define_require; 
		this.name_send=name;
		this.Other = other;
	}

	
	public Packet(int define_require, String name_send, String name_receive, String title,String content, String date,  String file, String fileImage) {
		super();
		this.define_require = define_require; 
		this.name_send = name_send;
		this.name_recerive = name_receive; 
		this.title = title;
		this.content = content;

		this.date = date;
		
		File = file;
		FileImage = fileImage;
		
	}

	
	public Packet(List<String> mess) {
		this.mess= mess;
	}
	public Packet(String messSent) {
		super();
		this.messSent = messSent;
	}
	public List<String> getMess() {
		return mess;
	}

	public void setMess(List<String> mess) {
		this.mess = mess;
	}
	private List<String> mess;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFile() {
		return File;
	}
	public void setFile(String file) {
		File = file;
	}
	public String getFileImage() {
		return FileImage;
	}
	public void setFileImage(String fileImage) {
		FileImage = fileImage;
	}
	public int getDefine_require() {
		return define_require;
	}
	public void setDefine_require(int define_require) {
		this.define_require = define_require;
	}
	public String getName_send() {
		return name_send;
	}
	public void setName_send(String name_send) {
		this.name_send = name_send;
	}
	public String getName_recerive() {
		return name_recerive;
	}
	public void setName_recerive(String name_recerive) {
		this.name_recerive = name_recerive;
	}
	public String getOther() {
		return Other;
	}
	public void setOther(String iP_client) {
		Other = iP_client;
	}
	public String getMessSent() {
		return messSent;
	}
	public void setMessSent(String messSent) {
		this.messSent = messSent;
	}
	public String getMessReceive() {
		return messReceive;
	}
	public void setMessReceive(String messReceive) {
		this.messReceive = messReceive;
	}
	
	
}
