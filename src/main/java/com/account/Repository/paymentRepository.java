package com.account.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.account.entity.payment;

public interface paymentRepository extends JpaRepository<payment, String> {
	
	@Query("select p from payment p where p.user.uid= :uid order by paymentDate DESC")
	public List<payment> findPaymentByUid(@Param("uid")String uid);

}
