package qlnhanvien.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qlnhanvien.dto.LoginDTO;
import qlnhanvien.entity.Account;
import qlnhanvien.service.IAccountService;


@RestController
@RequestMapping(value = "api/qlNhanVien/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IAccountService service;

	@GetMapping("/login")
	public LoginDTO login(Principal principal) {
		String username=principal.getName();
		Account account=service.getAccountByUsername(username);
		
		//entity --> dto
		LoginDTO dto = modelMapper.map(account, LoginDTO.class);
		
		return dto;
	}
}
