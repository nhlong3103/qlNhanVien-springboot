package qlnhanvien.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import qlnhanvien.dto.AccountDTO;
import qlnhanvien.entity.Account;
import qlnhanvien.form.AccountFilterForm;
import qlnhanvien.form.CreateAccountForm;
import qlnhanvien.form.UpdateAccountForm;
import qlnhanvien.service.IAccountService;


@CrossOrigin("*")
@RestController
@RequestMapping(value = "api/qlNhanVien/accounts")
public class AccountController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IAccountService service;

	@GetMapping()
	public Page<AccountDTO> getAllAccounts(Pageable pageable,
			@RequestParam(value = "search", required = false) String search, AccountFilterForm filterForm) {

		Page<Account> entityPages = service.getAllAccounts(pageable, search, filterForm);

		// convert entities --> dtos
		List<AccountDTO> dtos = modelMapper.map(entityPages.getContent(), new TypeToken<List<AccountDTO>>() {
		}.getType());

		Page<AccountDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;
	}
	
	@GetMapping(value = {"/accountByDepartmentIdIsNull"})
	public List<AccountDTO> getAccountByDepartmentIdIsNull() {

		List<Account> account = service.getAccountByDepartmentIdIsNull();

		// convert entities --> dtos
		List<AccountDTO> accountDtos = Arrays.asList(modelMapper.map(account, AccountDTO[].class));

		return accountDtos;
	}
	
	@GetMapping(value = {"/accountByDepartmentIdIsNullOrDepartmentIdIsParam/{departmentId}"})
	public List<AccountDTO> getAccountByDepartmentIdIsNullAndDepartmentIdIsParam(@PathVariable(name = "departmentId") int departmentId) {

		List<Account> account = service.getAccountByDepartmentIdIsNullOrDepartmentIdIsParam(departmentId);

		// convert entities --> dtos
		List<AccountDTO> accountDtos = Arrays.asList(modelMapper.map(account, AccountDTO[].class));

		return accountDtos;
	}

	@GetMapping(value = "/{id}")
	public AccountDTO getAccountByID(@PathVariable(name = "id") int id) {
		Account entity = service.getAccountById(id);

		// convert entity to dto
		AccountDTO dto = modelMapper.map(entity, AccountDTO.class);
		return dto;
	}
	
	@GetMapping("/activeAccount")
	public ResponseEntity<?> activeAccountrViaEmail(@RequestParam String token) {

		// active user
//		userService.activeUser(token);
		service.activeAccount(token);

		return new ResponseEntity<>("Active success!", HttpStatus.OK);
	}

//	@PostMapping()
//	public void createAccount(@RequestBody CreateAccountForm form) {
//		service.createAccount(form);
//	}

	@PostMapping()
	public ResponseEntity<?> createAccount(@RequestBody CreateAccountForm form) {
		service.createAccount(form);
		return new ResponseEntity<>("We have sent 1 email. Please check email to active account!", HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/{id}")
	public void updateAccount(@PathVariable(name = "id") int id, @RequestBody UpdateAccountForm form) {
		service.updateAccount(id, form);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteAccount(@PathVariable(name = "id") int id) {
		service.deleteAccount(id);
	}
}
