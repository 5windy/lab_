package atm;

// Data Transfer Object
// ㄴ 데이터 전달 용도의 객체

public class UserRequestDto {
	
	private String name;
	private String password;
	
	public UserRequestDto() {
		
	}
	
	public UserRequestDto(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
