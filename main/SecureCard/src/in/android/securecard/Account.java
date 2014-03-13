package in.android.securecard;

import java.io.Serializable;

public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String username;
	String passowrd;

	public Account(String username, String passowrd, String website) {
		super();
		this.username = username;
		this.passowrd = passowrd;
		this.website = website;
	}

	String website;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassowrd() {
		return passowrd;
	}

	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
