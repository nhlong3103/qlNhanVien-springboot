package qlnhanvien.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import qlnhanvien.entity.Account;
import qlnhanvien.entity.Department;
import qlnhanvien.form.CreateDepartmentForm;
import qlnhanvien.form.DepartmentFilterForm;
import qlnhanvien.form.UpdateDepartmentForm;
import qlnhanvien.repository.IAccountRepository;
import qlnhanvien.repository.IDepartmentRepository;
import qlnhanvien.specification.department.DepartmentSpecification;


@Service
public class DepartmentService implements IDepartmentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IDepartmentRepository repository;

	@Autowired
	private IAccountRepository accountRepository;

	public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm) {

		Specification<Department> where = DepartmentSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	public Department getDepartmentByID(int id) {
		return repository.findById(id).get();
	}

	@Transactional
	public void createDepartment(CreateDepartmentForm form) {

		// convert form to entity
		Department departmentEntity = modelMapper.map(form, Department.class);

		// create department
		Department department = repository.save(departmentEntity);
		List<Account> listAccountForm = department.getAccounts();
		for (Account account : listAccountForm) {
			Account accountUpdate = accountRepository.findByUsername(account.getUsername());
			accountUpdate.setDepartment(department);
			accountRepository.save(accountUpdate);
		}
		department.setTotalMember(accountRepository.countAccountFromDepartmentId(department.getId()));
		repository.save(department);
	}

	public void updateDepartment(int id, UpdateDepartmentForm form) {
		form.setId(id);

		// convert form to entity
		Department department = modelMapper.map(form, Department.class);

		List<Account> listAccountForm = department.getAccounts();

		for (Account account : listAccountForm) {
			Account accountUpdate = accountRepository.findByUsername(account.getUsername());
			accountUpdate.setDepartment(department);
			accountRepository.save(accountUpdate);
		}
		department.setTotalMember(accountRepository.countAccountFromDepartmentId(id));
		repository.save(department);
	}

	public void deleteDepartment(int id) {
		repository.deleteById(id);
	}

	public boolean isDepartmentExistsByName(String name) {
		return repository.existsByName(name);
	}

	public boolean isDepartmentExistsByID(Integer id) {
		return repository.existsById(id);
	}

	public Department getDepartmentByName(String name) {
		return repository.findByName(name);
	}

}
