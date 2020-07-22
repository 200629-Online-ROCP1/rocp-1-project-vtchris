package com.changeBank.models.users;

public class UserDTO {

	public int userId;
	public String username;
	public String newPassword;
	public String firstName;
	public String lastName;
	public String email;
	public int roleId;
	public Role role;
	public int authUserId; //Who is logged in

}