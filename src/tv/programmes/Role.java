package tv.programmes;

public class Role {
	private String name, role;

	public Role(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	/**
	 * For easy checking if this role is an actor.
	 * @return true if the role is an actor, false otherwise
	 */
	public boolean isActor(){
		return role.equals("actor");
	}
}
