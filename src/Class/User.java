package Class;

import java.io.Serializable;

public class User  implements Serializable  {
	private int ID;
	private int require;
	private String name,pass,fullname;

	public User() {
		
	}
	public User(int define_REQUIRE_REGISTER, String username, String pass, String fullname2) {
		// TODO Auto-generated constructor stub
		this.require=define_REQUIRE_REGISTER;
		this.name = username;
		this.pass = pass;
		this.fullname = fullname2;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
}
