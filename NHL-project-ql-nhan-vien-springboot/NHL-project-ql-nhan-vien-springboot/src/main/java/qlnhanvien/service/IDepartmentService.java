package qlnhanvien.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import qlnhanvien.entity.Department;
import qlnhanvien.form.CreateDepartmentForm;
import qlnhanvien.form.DepartmentFilterForm;
import qlnhanvien.form.UpdateDepartmentForm;


public interface IDepartmentService {

	public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm);

	public Department getDepartmentByID(int id);
	
	public Department getDepartmentByName(String name);

	public void createDepartment(CreateDepartmentForm form);

	public void updateDepartment(int id, UpdateDepartmentForm form);
	
	public void deleteDepartment(int id);
	
	public boolean isDepartmentExistsByName(String name);

	public boolean isDepartmentExistsByID(Integer id);
}
