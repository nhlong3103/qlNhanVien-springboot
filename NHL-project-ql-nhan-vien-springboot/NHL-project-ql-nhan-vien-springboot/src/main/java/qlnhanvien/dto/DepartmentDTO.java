package qlnhanvien.dto;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO {

	private Integer id;

	private String name;

	private String type;
	
	private int totalMember;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;

	private List<AccountDTO> accounts;

	@Data
	@NoArgsConstructor
	public static class AccountDTO {
		// thay vì hiện id thì hiện là account Id
		@JsonProperty("accountId")
		private int id;
		private String username;
		private String email;
	}

}
