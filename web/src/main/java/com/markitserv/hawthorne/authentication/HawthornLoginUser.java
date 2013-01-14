package com.markitserv.hawthorne.authentication;

import java.io.Serializable;
import java.sql.Timestamp;

public class HawthornLoginUser implements Serializable {

	private static final long serialVersionUID = 5406531014133706700L;
	private Long userId;
	private String userName;
	private String password;
	private String role;
	private long loggedInTime;
	private long loggedOutTime;
	private String userToken;

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public long getLoggedInTime() {
		return loggedInTime;
	}

	public void setLoggedInTime(long loggedInTime) {
		this.loggedInTime = loggedInTime;
	}

	public long getLoggedOutTime() {
		return loggedOutTime;
	}

	public void setLoggedOutTime(long loggedOutTime) {
		this.loggedOutTime = loggedOutTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HawthornLoginUser other = (HawthornLoginUser) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}

