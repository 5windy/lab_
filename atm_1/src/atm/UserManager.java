package atm;

import java.util.ArrayList;

public class UserManager {
	
	private UserManager() {
		
	}
	private static UserManager instance = new UserManager();
	public static UserManager getInstance() {
		return instance;
	}
	
	private ArrayList<User> group = new ArrayList<>();

}
