package models.users;

public class Role {
	  private int roleId; // primary key
	  private String role; // not null, unique
	  
	  public Role() {
		  super();
	  }

	  public Role(int roleId, String role) {
		  super();
		  this.roleId = roleId;
		  this.role = role;
	  }

	  public int getRoleId() {
		  return roleId;
	  }

	  public void setRoleId(int roleId) {
		  this.roleId = roleId;
	  }

	  public String getRole() {
		  return role;
	  }

	  public void setRole(String role) {
		  this.role = role;
	  }
	  
}
