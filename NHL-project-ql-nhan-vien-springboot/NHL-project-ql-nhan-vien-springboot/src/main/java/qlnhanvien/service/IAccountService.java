package qlnhanvien.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import qlnhanvien.entity.Account;
import qlnhanvien.form.AccountFilterForm;
import qlnhanvien.form.CreateAccountForm;
import qlnhanvien.form.UpdateAccountForm;


public interface IAccountService extends UserDetailsService {
	public Page<Account> getAllAccounts(Pageable pageable, String search, AccountFilterForm form);

	public Account getAccountById(int id);

	public Account getAccountByUsername(String username);

	public void createAccount(CreateAccountForm form);

	public void updateAccount(int id, UpdateAccountForm form);

	public void deleteAccount(int id);

	public boolean isAccountExistsByUsername(String username);

	public List<Account> getAccountByDepartmentIdIsNull();

	public List<Account> getAccountByDepartmentIdIsNullOrDepartmentIdIsParam(int departmentId);

	void sendRegistrationAccountConfirm(String email);

	void activeAccount(String token);
}
