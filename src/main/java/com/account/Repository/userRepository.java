package com.account.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.account.entity.User;

public interface userRepository extends JpaRepository<User, String> {
	
	public User findByEmail(String email);
	
	@Query("select u from User u where u.batch= :batch order by u.addedDate DESC LIMIT 1")
	public User findLastAddedByBatch(@Param("batch")String batch);
	
	public List<User> findByBatch(String batch);
	
	@Modifying
	@Query("update User u set u.pendingFee =:pendingFee where u.uid= :uid")
	public void UpdatePendingFee(@Param("pendingFee") double pendingFee,@Param("uid")String uid);
	
	@Query("select u from User u where u.semester.semesterId= :semesterId")
	public List<User> findUserBySemesterId(@Param("semesterId")String semesterId);
	
	@Modifying
	@Query("update User u set u.semester.semesterId= :semesterId, u.previousFee=u.pendingFee+u.previousFee,u.pendingFee= :semesterFee where u.semester.semesterId= :presentId")
	public void incrementSemester(@Param("semesterId")String semesterId,@Param("presentId")String presentId
			,@Param("semesterFee")double semesterFee);
	
	@Modifying
	@Query("Update User u set u.name= :name, u.email= :email, u.contact= :contact, u.pendingFee= :pendingFee, u.previousFee= :previousFee, u.image= :image where u.uid= :uid")
	public void UpdateUser(@Param("name")String name,@Param("email")String email,@Param("contact")String contact,
			@Param("pendingFee")double pendingFee,@Param("previousFee")double previousFee,@Param("image")String image, @Param("uid")String uid);
	
	@Query("select u from User u where u.pendingFee>0")
	public List<User>UserWithPendingFee();
}
