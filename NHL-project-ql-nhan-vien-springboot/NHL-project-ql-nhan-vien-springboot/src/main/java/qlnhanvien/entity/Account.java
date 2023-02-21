package qlnhanvien.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`Account`")
@Data
@NoArgsConstructor
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username", length = 50, nullable = false, unique = true, updatable = false)
	private String username;
	
	@Column(name = "`email`", length = 50, nullable = false, unique = true, updatable = false)
	private String email;

	@Column(name = "password", length = 800, nullable = false)
	private String password;

	@Column(name = "`Role`")
	@Enumerated(EnumType.STRING)
	private Role role= Role.EMPLOYEE;
	
	@Column(name = "`status`", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private AccountStatus accountStatus = AccountStatus.NOT_ACTIVE;

	@ManyToOne
	@JoinColumn(name = "department_id", nullable = true)
	private Department department;

	public enum Role {
		ADMIN, MANAGER, EMPLOYEE
	}
	
	public enum AccountStatus {
		NOT_ACTIVE, ACTIVE
	}
}
