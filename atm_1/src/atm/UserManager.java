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
	
	private User getUserByCode(int code) {
		User target = new User(code);
		int index = group.indexOf(target);
		
		if(index == -1)
			return null;
		
		return group.get(index);
	}
	
	private User getUserByName(String name) {
		int index = indexOf(name);
		
		if(index == -1)
			return null;
		
		return group.get(index);
	}
	
	private int indexOf(String name) {
		for(int i=0; i<group.size(); i++) {
			User user = group.get(i);
			if(user.getName().equals(name))
				return i;
		}
		return -1;
	}
	
	private boolean removeUserByCode(int code) {
		User target = new User(code);
		int index = group.indexOf(target);
		
		if(index == -1)
			return false;
		
		group.remove(index);
		return true;
	}
	
	private boolean removeUserByName(String name) {
		int index = indexOf(name);
		
		if(index == -1)
			return false;
		
		group.remove(index);
		return true;
	}
	
}
