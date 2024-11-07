package atm;

import java.util.ArrayList;
import java.util.Random;

public class AccountManager {
	
	private AccountManager() {
		
	}
	private static AccountManager instance = new AccountManager();
	public static AccountManager getInstance() {
		return instance;
	}
	
	private ArrayList<Account> list = new ArrayList<>();
	
	// Account CRD
	
	public Account addAccount(int userCode, int password) {
		// 1. 유저 조회 후, (시스템)
		// 2. 잔여 계좌개설 가능 수 확인 (시스템) 
		// 3. 개좌 개설
		int code = generateAccountCode();
		Account account = new Account(code, userCode, password);
		
		list.add(account);
		return account;
	}

	private int generateAccountCode() {
		int code = 0;
		
		Random random = AtmSystem.random;
		while(true) {
			code = random.nextInt(9000) + 1000;
			
			Account target = new Account(code);
			if(!list.contains(target))
				break;
		}
		return code;
	}
	
	public Account getAccountByCode(int code) {
		Account target = new Account(code);
		int index = list.indexOf(target);
		
		if(index == -1)
			return null;
		
		return list.get(index);
	}
	
	public ArrayList<Account> getAccountByUserCode(int userCode) {
		ArrayList<Account> list = new ArrayList<>();
		
		for(int i=0; i<this.list.size(); i++) {
			Account account = this.list.get(i);
			if(account.getUserCode() == userCode)
				list.add(account);
		}
		return list;
	}
	
	public ArrayList<Account> getList() {
		ArrayList<Account> clone = (ArrayList<Account>) list.clone();
		return clone;
	}
	
	public void setList(ArrayList<Account> list) {
		this.list.clear();
		for(int i=0; i<list.size(); i++) {
			Account account = list.get(i);
			this.list.add(account);
		}
	}
	
	public boolean removeAccountByCode(int code, int password) {
		Account target = new Account(code);
		int index = list.indexOf(target);
		
		if(index == -1)
			return false;
		
		Account account = list.get(index);
		if(!account.isValidPassword(password))
			return false;
		
		list.remove(account);
		return true;
	}
	
}
