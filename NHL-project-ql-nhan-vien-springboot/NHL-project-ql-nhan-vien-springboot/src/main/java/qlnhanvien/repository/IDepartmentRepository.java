package qlnhanvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import qlnhanvien.entity.Department;


public interface IDepartmentRepository
		extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {

	public Department findByName(String name);

	boolean existsByName(String name);
}
