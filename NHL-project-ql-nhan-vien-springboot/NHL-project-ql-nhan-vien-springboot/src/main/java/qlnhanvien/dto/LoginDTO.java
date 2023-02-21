package qlnhanvien.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {

	private int id;
	
	private String username;
	
	private String role;
	}
