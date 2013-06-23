package model;

public class ResDef {
	private String code;
	private String description;

	public ResDef(){}
	
	public ResDef(String code,String description) {
		this.code = code;
		this.description = description;
		
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
