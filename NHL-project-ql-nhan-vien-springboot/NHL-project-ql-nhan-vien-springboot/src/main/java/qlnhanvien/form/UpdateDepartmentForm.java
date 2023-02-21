package qlnhanvien.form;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDepartmentForm {

	private int id;
	
	@NotBlank(message = "Bạn phải điền tên department!")
	@Length(max = 50, message = "Tên department không được dài quá 50 ký tự!")
//	@DepartmentNameNotExists
	private String name;

	@Pattern(regexp = "DEV|TEST|PM", message = "Bạn phải điền dạng DEV, TEST hoặc PM!")
	private String type;
	
	@NotEmpty(message = "Không được để trống phần accounts")
	private List<@Valid Account> accounts;

	@Data
	@NoArgsConstructor
	public static class Account {
		@NotBlank(message = "Bạn phải điền tên username!")
		@Length(max = 50, message = "Tên username không được dài quá 50 ký tự!")
//		@AccountUsernameNotExists
		private String username;
	}
}
