package qlnhanvien.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Registration_Account_Token")
@Data

public class RegistrationAccountToken implements Serializable {
	private static final long serialVersionUID = 1L;

	// tồn tài trong 24h
	private static final long EXPIRATION_TIME = 86400000l;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "token", nullable = false, length = 50, unique = true)
	private String token;

	@OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "account_id")
	private Account account;

	@Column(name = "expiry_date", nullable = false)
	private Date expiryDate;

	public RegistrationAccountToken() {
		expiryDate = getDefaultExpiryDate();
	}
	
	public RegistrationAccountToken(String token, Account account) {
		this.token = token;
		this.account = account;

		// 1h
		expiryDate = new Date(System.currentTimeMillis() + 360000);
	}

	private Date getDefaultExpiryDate() {
		return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
	}

	public boolean isExpiryDate() {
		return expiryDate.getTime() < System.currentTimeMillis();
	}
}
