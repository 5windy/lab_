package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileManager {
	
	private File file;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	
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
}
