package qlnhanvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import qlnhanvien.entity.RegistrationAccountToken;


public interface IRegistrationAccountTokenRepository extends JpaRepository<RegistrationAccountToken, Integer> {

	public RegistrationAccountToken findByToken(String token);

	public boolean existsByToken(String token);
	
	@Query("	SELECT 	token	"
			+ "	FROM 	RegistrationAccountToken "
			+ " WHERE 	account.id = :accountId")
	public String findByAccountId(int accountId);

	@Transactional
	@Modifying
	@Query("	DELETE 							"
			+ "	FROM 	RegistrationAccountToken 	"
			+ " WHERE 	account.id = :accountId")
	public void deleteByAccountId(int accountId);

}
