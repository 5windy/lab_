package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	
	private File file;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	
	private UserManager userManager = UserManager.getInstance();
	private AccountManager accountManager = AccountManager.getInstance();
	
	private String userFileName = "user.txt";
	private String accountFileName = "account.txt";
	
	// Singleton Pattern 
	// 1) 생성자를 외부에서 사용하지 못하게 함 (생성자의 접근 제어자를 public -> private)
	private FileManager() {
		
	}
	// 2) 단일 인스턴스 만들기 (생성자 호출 <- 클래스 내부에서만 가능)
	private static FileManager instance = new FileManager();
	// 3) 단일 인스턴스에 접근할 수 있도록 public getter를 제공 
	public static FileManager getInstance() {
		return instance;
	}
	
	// 저장과 로드
	public void save() {
		// 1. 유저정보 
		saveUserData();
		// 2. 계좌정보
		saveAccountData();
	}
	
	private void save(String fileName, String data) {
		file = new File(fileName);
		
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(data);
			System.out.println(fileName + " 저장 완료");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(fileName + " 저장 실패");
		} finally {
			try {
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveAccountData() {
		String data = "";
		ArrayList<Account> list = accountManager.getList();
		for(int i=0; i<list.size(); i++) {
			Account account = list.get(i);
			data += account.getAccountInfoAsString();
			if(i < list.size() - 1)
				data += "\n";
		}
		save(accountFileName, data);
	}

	private void saveUserData() {
		String data = "";
		ArrayList<User> group = userManager.getGroup();
		for(int i=0; i<group.size(); i++) {
			User user = group.get(i);
			data += user.getUserInfoAsString();
			if(i < group.size() - 1)
				data+= "\n";
		}
		save(userFileName, data);
	}
	

	public void load() {
		ArrayList<User> group = loadUserFile();
		userManager.setGroup(group);
		
		ArrayList<Account> list = loadAccountFile();
		accountManager.setList(list);
	}

	private ArrayList<User> loadUserFile() {
		ArrayList<User> group = new ArrayList<>();
		
		file = new File(userFileName);
		
		if(!file.exists()) {
			System.out.println("유저 파일을 찾지 못했습니다.");
			return group;
		}
		
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			while(bufferedReader.ready()) {
				String line = bufferedReader.readLine();
				String[] info = line.split("/");
				
				int code = Integer.parseInt(info[0]);
				String name = info[1];
				String password = info[2];
				
				User user = new User(code, name, password);
				group.add(user);
			}
			System.out.println("유저 로드 완료");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("유저 로드 실패");
		} finally {
			try {
				bufferedReader.close();
				fileReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return group;
	}

	private ArrayList<Account> loadAccountFile() {
		ArrayList<Account> list = new ArrayList<>();
		
		file = new File(accountFileName);
		
		if(!file.exists()) {
			System.out.println("계좌 파일을 찾지 못했습니다.");
			return list;
		}
		
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			while(bufferedReader.ready()) {
				String line = bufferedReader.readLine();
				String[] info = line.split("/");
				
				int code = Integer.parseInt(info[0]);
				int userCode = Integer.parseInt(info[1]);
				int password = Integer.parseInt(info[2]);
				int balance = Integer.parseInt(info[3]);
				
				Account account = new Account(code, userCode, password, balance);
				list.add(account);
			}
			System.out.println("계좌 로드 완료");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("계좌 로드 실패");
		} finally {
			try {
				bufferedReader.close();
				fileReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
}
