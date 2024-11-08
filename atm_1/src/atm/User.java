package atm;

import java.util.ArrayList;

public class User {
	
	private int code;
	private String name;
	private String password;
	private ArrayList<Account> accounts = new ArrayList<>();
	
	public User(int code) {
		this.code = code;
	}
	
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
	
	public ArrayList<Account> getAccounts() {
		return this.accounts;
	}
	
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts.clear();
		for(int i=0; i<accounts.size(); i++) {
			Account account = accounts.get(i);
			this.accounts.add(account);
		}
	}
	
	public boolean isValidPassword(String password) {
		return this.password.equals(password);
	}
	
	public String getUserInfoAsString() {
		String info = "";
		info += code + "/";
		info += name + "/";
		info += password;
		return info;
	}
	
	public int getAccountSize() {
		return this.accounts.size();
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			User target = (User)obj;
			return this.code == target.getCode();
		}
		return false;
	}

}
