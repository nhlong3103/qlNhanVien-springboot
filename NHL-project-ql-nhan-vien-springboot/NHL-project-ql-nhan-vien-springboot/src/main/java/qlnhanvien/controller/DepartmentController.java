package qlnhanvien.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
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

import qlnhanvien.dto.DepartmentDTO;
import qlnhanvien.entity.Department;
import qlnhanvien.form.CreateDepartmentForm;
import qlnhanvien.form.DepartmentFilterForm;
import qlnhanvien.form.UpdateDepartmentForm;
import qlnhanvien.service.IDepartmentService;


@RestController
@RequestMapping(value = "api/qlNhanVien/departments")
@CrossOrigin("*")
@Validated
public class DepartmentController {

	@Autowired
	private IDepartmentService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping()
	public Page<DepartmentDTO> getAllDepartments(Pageable pageable,
			@RequestParam(name = "search", required = false) String search, DepartmentFilterForm filterForm) {
		Page<Department> entityPages = service.getAllDepartments(pageable, search, filterForm);

		// convert entities --> dtos
		List<DepartmentDTO> dtos = modelMapper.map(entityPages.getContent(), new TypeToken<List<DepartmentDTO>>() {
		}.getType());

		Page<DepartmentDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;
	}

	@GetMapping(value = "/{id}")
	public DepartmentDTO getDepartmentByID(@PathVariable(name = "id") int id) {
		Department entity = service.getDepartmentByID(id);

		// convert entity to dto
		DepartmentDTO dto = modelMapper.map(entity, DepartmentDTO.class);

		return dto;
	}

	@GetMapping(value = "/name/{name}")
	public DepartmentDTO getDepartmentByName(@PathVariable(name = "name") String name) {
		Department entity = service.getDepartmentByName(name);

		// convert entity to dto
		DepartmentDTO dto = modelMapper.map(entity, DepartmentDTO.class);
		return dto;
	}

	@PostMapping()
	public void createDepartment(@RequestBody @Valid CreateDepartmentForm form) {
		service.createDepartment(form);
	}

	@PutMapping(value = "/{id}")
	public void updateDepartment(@PathVariable(name = "id") int id, @RequestBody @Valid UpdateDepartmentForm form) {
		service.updateDepartment(id, form);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteDepartment(@PathVariable(name = "id") int id) {
		service.deleteDepartment(id);
	}

}
