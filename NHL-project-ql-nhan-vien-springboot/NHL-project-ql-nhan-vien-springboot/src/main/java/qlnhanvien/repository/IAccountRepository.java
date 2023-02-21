package qlnhanvien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import qlnhanvien.entity.Account;
import qlnhanvien.entity.Account.AccountStatus;


public interface IAccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
	public Account findByUsername(String name);

	public Account findByEmail(String email);
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE `Account` SET username =:usernameParam, department_id =:departmentIdParam WHERE id =:idParam")
	public void updateUsernameAndDepartmentId(@Param("usernameParam") String usernameParam, @Param("departmentIdParam") Integer departmentIdParam, @Param("idParam") Integer idParam);

	boolean existsByUsername(String username);

	public boolean existsByEmail(String email);

	@Query(nativeQuery = true, value = "SELECT COUNT(id) FROM `Account` WHERE department_id = :nameParameter")
	public int countAccountFromDepartmentId(@Param("nameParameter") int departmentId);

	@Query(nativeQuery = true, value = "SELECT * FROM `Account` WHERE department_id IS NULL")
	public List<Account> findByDepartmentIdIsNull();

	@Query(nativeQuery = true, value = "SELECT * FROM `Account` WHERE department_id IS NULL OR department_id =:nameParameter")
	public List<Account> findByDepartmentIdIsNullORDepartmentIdIsParam(@Param("nameParameter") int departmentId);

	@Query("SELECT 	accountStatus " + "FROM 	Account " + " WHERE email = :email")
	public AccountStatus findStatusByEmail(String email);
}
