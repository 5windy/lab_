package atm;

import java.util.ArrayList;

public class User {
	
	private int code;
	private String name;
	private String password;
	private ArrayList<Account> accounts;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public User(int code, String name, String password) {
		this.code = code;
		this.name = name;
		this.password = password;
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
	
	public boolean isValidPassword(String password) {
		return this.password.equals(password);
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