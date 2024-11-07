package atm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AtmSystem {
	
	private final int JOIN = 1;
	private final int LEAVE = 2;
	private final int LOGIN = 3;
	private final int LOGOUT = 4;
	private final int DEPOSIT = 5;
	private final int WITHRAWAL = 6;
	private final int BALANCE = 7;
	private final int TRANSFER = 8;
	private final int ACC_OPEN = 9;
	private final int ACC_CLOSE = 10;
	private final int ACC_LIMIT = 3;
	private final int EXIT = 0;
	
	private final int STRING = 1;
	private final int NUMBER = 2;
	
	public static Scanner scanner = new Scanner(System.in);
	public static Random random = new Random();
	
	private UserManager userManager = UserManager.getInstance();
	private AccountManager accountManager = AccountManager.getInstance();
	private FileManager fileManager = FileManager.getInstance();
	
//		+ 뱅킹기능(입금,출금,조회,이체)
//		+ 파일기능(저장,로드)
	
//		마지막 커밋 메세지 : 
//		Release v1.0.0 with core features
	
	private boolean isRun = true;
	
	private int log = -1;	// userCode
	
	public void run() {
		while(isRun) {
			printMenu();
			runMenu();
		}
	}
	
	private void printMenu() {
		System.out.println("1. 회원가입");
		System.out.println("2. 회원탈퇴");
		System.out.println("3. 로그인");
		System.out.println("4. 로그아웃");
		System.out.println("5. 입금");
		System.out.println("6. 출금");
		System.out.println("7. 조회");
		System.out.println("8. 이체");
		System.out.println("9. 계좌개설");
		System.out.println("10. 계좌철회");
		System.out.println("0. 종료");
	}
	
	private void runMenu() {
		int select = (int)input("메뉴 선택", NUMBER);
		
		if(isLogin()) {
			if(select == JOIN || select == LOGIN) {
				System.out.println("로그인 중입니다.");
				return;
			}
		} else {
			if(select == LEAVE || select == LOGOUT || select == DEPOSIT || select == WITHRAWAL || 
					select == BALANCE || select == TRANSFER || select == ACC_OPEN || select == ACC_CLOSE) {
				System.out.println("로그인 후 이용가능합니다.");
				return;
			}
		}
		
		switch(select) {
		case JOIN :
			join();
			return;
		case LEAVE :
			leave();
			return;
		case LOGIN :
			login();
			return;
		case LOGOUT :
			logout();
			return;
		case DEPOSIT :
			deposit();
			return;
		case WITHRAWAL :
			withrawal();
			return;
		case BALANCE :
			balance();
			return;
		case TRANSFER :
			transfer();
			return;
		case ACC_OPEN :
			openAccount();
			return;
		case ACC_CLOSE :
			closeAccount();
			return;
		case EXIT :
			exit();
		}
	}
	
	private void exit() {
		// TODO Auto-generated method stub
		
	}

	private void closeAccount() {
		// 1. 로그인한 회원의 계좌 목록을 출력 
		// 2. 철회할 계좌를 선택 받아 
		// 3. 계좌 철회 
		printUserAccounts();
		int accCode = (int)input("철회할 계좌번호", NUMBER);
		int password = (int)input("계좌 비밀번호", NUMBER);
		
		if(accountManager.removeAccountByCode(accCode, password))
			System.out.println("계좌철회 완료");
		else 
			System.out.println("계좌철회 실패, 계좌 정보를 다시 확인하세요.");
	}
	
	private void printUserAccounts() {
		User user = userManager.getUserByCode(log);
		ArrayList<Account> accounts = user.getAccounts();
		
		for(int i=0; i<accounts.size(); i++) {
			Account account = accounts.get(i);
			System.out.println("[" + (i+1) + "] " + account);
		}
	}

	private void openAccount() {
		User user = userManager.getUserByCode(log);
		int accSize = user.getAccountSize();
		
		if(accSize == ACC_LIMIT) {
			System.err.println("최대 계좌개설 가능 개수를 초과할 수 없습니다.");
			return;
		}
		
		int password = (int)input("계좌 비밀번호 (숫자)", NUMBER);
		
		Account account = accountManager.addAccount(log, password);
		
		// 이미 accountManager가 가지고 있는 계좌 객체의 
		// 주소에 바로 접근할 수 있는 목록 관리 
		user.addAcount(account);
		System.out.println(account.getCode() + "번 계좌가 개설되었습니다.");
	}

	private void transfer() {
		Account account = getAccountByNumberFromLoginUser();
		
		if(account == null)
			return;
		
		int accCode = (int)input("이체할 계좌 번호", NUMBER);
		Account target = accountManager.getAccountByCode(accCode);
		
		if(target == null) {
			System.out.println("계좌정보가 올바르지 않습니다.");
			return;
		}
		
		if(target.getUserCode() == log) {
			System.out.println("본인 계좌로의 이체는 불가합니다.");
			return;
		}
		
		int money = (int)input("이체할 금액", NUMBER);
		
		if(money > account.getBalance() || money < 1) {
			System.out.println("잔액이 부족합니다.");
			return;
		}
		
		account.decreaseBalance(money);
		target.increaseBalance(money);
		System.out.println("이체 완료");
	}

	private void balance() {
		Account account = getAccountByNumberFromLoginUser();
		
		if(account == null)
			return;
		
		System.out.println(account);
	}

	private void withrawal() {
		Account account = getAccountByNumberFromLoginUser();
		
		if(account == null)
			return;
		
		int money = (int)input("인출할 금액", NUMBER);
		
		if(money > account.getBalance() || money < 1) {
			System.out.println("잔액이 부족합니다.");
			return;
		}
		
		account.decreaseBalance(money);
		System.out.println("인출 완료");
	}

	private void deposit() {
		Account account = getAccountByNumberFromLoginUser();
		
		if(account == null)
			return;
		
		int money = (int)input("입금할 금액", NUMBER);
		
		if(money < 1) {
			System.out.println("유효하지 않은 금액입니다.");
			return;
		}
		
		account.increaseBalance(money);
		System.out.println("입금 완료");
	}
	
	private Account getAccountByNumberFromLoginUser() {
		Account account = null;
		
		User user = userManager.getUserByCode(log);
		int accSize = user.getAccountSize();
		
		
		printUserAccounts();
		int index = (int)input("번호 선택", NUMBER) - 1;
		
		if(index < 0 || index >= accSize) {
			System.out.println("유효하지 않은 번호입니다.");
			return account;
		}
		
		account = user.getAccountByIndex(index);
		return account;
	}

	private void logout() {
		log = -1;
		System.out.println("로그아웃 완료");
	}

	private void login() {
		String name = (String)input("아이디", STRING);
		String password = (String)input("패스워드", STRING);
		
		User user = userManager.getUserByName(name);
		
		if(user.isValidPassword(password)) {
			log = user.getCode();
			System.out.println("로그인 성공");
		} else
			System.out.println("비밀번호가 불일치합니다.");
	}

	private void leave() {
		String password = (String)input("패스워드", STRING);
		
		User user = userManager.getUserByCode(log);
		
		if(user == null) {
			System.out.println("회원정보를 다시 확인하세요.");
			return;
		}
		
		if(userManager.removeUserByCode(user.getCode(), password))
			System.out.println("회원 탈퇴 완료");
		else
			System.out.println("비밀번호를 다시 확인하세요.");
	}
	
	private boolean isLogin() {
		return log != -1;
	}

	private void join() {
		UserRequestDto user = createUserRequestDto();
		if(userManager.addUser(user))
			System.out.println("회원가입 성공");
		else 
			System.out.println("중복되는 아이디입니다.");
	}
	
	private UserRequestDto createUserRequestDto() {
		UserRequestDto userDto = null;
		
		String name = (String)input("아이디", STRING);
		String password = (String)input("비밀번호", STRING);
		
		userDto = new UserRequestDto(name, password);
		return userDto;
	}
	
	private Object input(String message, int type) {
		System.out.println(message + " : ");
		String input = "";
		
		if(type == STRING) {
			while(input.equals("")) {
				input = scanner.nextLine();
			}
			return input;
		} else if(type == NUMBER) {
			input = scanner.nextLine();
			int number = -1;
			try {
				number = Integer.parseInt(input);
				return number;
			} catch (Exception e) {
				System.err.println("숫자를 입력하세요.");
			} finally {
				return null;
			}
		}
		return null;
	}

}
