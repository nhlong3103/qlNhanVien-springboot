package qlnhanvien.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAccountForm {

	private int id;

	private String username;

	private int departmentId;
}
