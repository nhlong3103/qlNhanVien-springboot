package qlnhanvien.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

	private int id;

	private String username;
	
	private String email;

	private String departmentName;

	private Integer departmentId;
}
