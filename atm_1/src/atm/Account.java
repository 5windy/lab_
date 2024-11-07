package atm;

public class Account {
	
	private int code;
	private int userCode;
	private int password;
	private int balance;
	
	public Account(int code) {
		this.code = code;
	}
	
	public Account(int code, int userCode, int password) {
		this.code = code;
		this.userCode = userCode;
		this.password = password;
	}
	
	public Account(int code, int userCode, int password, int balance) {
		this.code = code;
		this.userCode = userCode;
		this.password = password;
		this.balance = balance;
	}
	
	public boolean isValidPassword(int password) {
		return password == this.password;
	}
	
	public void increaseBalance(int money) {
		this.balance += money;
	}
	
	public void decreaseBalance(int money) {
		this.balance -= money;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public int getUserCode() {
		return this.userCode;
	}
	
	public int getBalance() {
		return this.balance;
	}
}
