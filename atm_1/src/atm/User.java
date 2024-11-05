package atm;

import java.util.ArrayList;

public class User {
	
	private int code;
	private String name;
	private ArrayList<Account> accounts;
	
	public User(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public void addAcount(Account account) {
		accounts.add(account);
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);
	}
	
	public Account getAccountByIndex(int index) {
		return this.accounts.get(index);
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.code + ") " + this.name;
	}

}
