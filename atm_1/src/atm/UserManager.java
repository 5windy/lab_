package atm;

import java.util.ArrayList;
import java.util.Random;

public class UserManager {
	
	private UserManager() {
		
	}
	private static UserManager instance = new UserManager();
	public static UserManager getInstance() {
		return instance;
	}
	
	private ArrayList<User> group = new ArrayList<>();

	// 회원에 대한 CRD 기능
	
	public boolean addUser(UserRequestDto reqUser) {
		// 1) 요청단에서 넘어온 유저 객체(name, password)
		// 2) 랜덤 식별 코드를 발행하여 -> group에 추가
		
		int code = generateUserCode();
		String name = reqUser.getName();
		
		if(isDuplUserName(name)) 
			return false;
		
		String password = reqUser.getPassword();
		
		User user = new User(code, name, password);
		group.add(user);
		
		return true;
	}
	
	private int generateUserCode() {
		int code = 0;
		
		Random random = AtmSystem.random;
		while(true) {
			code = random.nextInt(9000) + 1000;
			User target = new User(code);
			
			if(!group.contains(target))
				break;
		}
		
		return code;
	}
	
	private boolean isDuplUserName(String name) {
		int index = indexOf(name);
		return index != -1;
	}
	
	public User getUserByCode(int code) {
		User target = new User(code);
		int index = group.indexOf(target);
		
		if(index == -1)
			return null;
		
		return group.get(index);
	}
	
	public User getUserByName(String name) {
		int index = indexOf(name);
		
		if(index == -1)
			return null;
		
		return group.get(index);
	}
	
	public ArrayList<User> getGroup() {
		ArrayList<User> clone = new ArrayList<>();
		for(int i=0; i<group.size(); i++) {
			User user = group.get(i);
			clone.add(user);
		}
		return clone;
	}
	
	public void setGroup(ArrayList<User> group) {
		this.group.clear();
		for(int i=0; i<group.size(); i++) {
			User user = group.get(i);
			this.group.add(user);
		}
	}
	
	private int indexOf(String name) {
		for(int i=0; i<group.size(); i++) {
			User user = group.get(i);
			if(user.getName().equals(name))
				return i;
		}
		return -1;
	}
	
	public boolean removeUserByCode(int code, String password) {
		User target = new User(code);
		int index = group.indexOf(target);
		
		if(index == -1)
			return false;
		
		User user = group.get(index);
		if(!user.isValidPassword(password))
			return false;
		
		group.remove(user);
		return true;
	}
	
	public boolean removeUserByName(String name, String password) {
		int index = indexOf(name);
		
		if(index == -1)
			return false;
		
		User user = group.get(index);
		if(!user.isValidPassword(password))
			return false;
		
		group.remove(user);
		return true;
	}
	
}
