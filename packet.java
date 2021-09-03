package lab4;

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.Serializable;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class packet implements Serializable {

	private static final long serivalVersionUID = 1L;
	private int port ;
	private String user = new String(), mess = new String();
	private String  date ;
	private String icon = new String();
	private File file;
	public packet(String user , String mess, String date, String icon,File file) {
		this.setUser(user);
		this.setMess(mess);
		this.setDate(date);
		this.setIcon(icon);
		this.setFile(file);
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getMess() {
		return mess;
	}
	public void setMess(String mess) {
		this.mess = mess;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
