package atm;

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
	private final int EXIT = 0;
	
	private final int STRING = 1;
	private final int NUMBER = 2;
	
	public static Scanner scanner = new Scanner(System.in);
	public static Random random = new Random();
	
	private UserManager userManager = UserManager.getInstance();
	private AccountManager accountManager = AccountManager.getInstance();
	private FileManager fileManager = FileManager.getInstance();
	
//		- 회원관리(가입/탈퇴)
//		- 로그인
//		- 계좌관리(신청/철회) (1인 3계좌까지)
//		+ 뱅킹기능(입금,출금,조회,이체,계좌생성,계좌철회)
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
		// TODO Auto-generated method stub
		
	}

	private void openAccount() {
		// TODO Auto-generated method stub
		
	}

	private void transfer() {
		// TODO Auto-generated method stub
		
	}

	private void balance() {
		// TODO Auto-generated method stub
		
	}

	private void withrawal() {
		// TODO Auto-generated method stub
		
	}

	private void deposit() {
		// TODO Auto-generated method stub
		
	}

	private void logout() {
		if(log == -1) {
			System.out.println("이미 로그아웃 상태입니다.");
			return;
		}
		
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
		if(!isLogin()) {
			System.out.println("로그인 후 이용가능합니다.");
			return;
		}
		
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
