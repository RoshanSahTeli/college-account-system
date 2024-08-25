package com.account.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.account.DTO.semesterStudentCount;
import com.account.entity.semester;

public interface semesterRepository extends JpaRepository<semester, String> {

	@Query("SELECT s.semesterId AS semId, s.semesterName AS cname, COUNT(st.uid) AS totalStudent, SUM(st.pendingFee) AS pendingFee "
			+ "FROM semester s LEFT JOIN s.user st WHERE s.course.id = :courseId "
			+ "GROUP BY s.semesterId, s.semesterName")

	List<semesterStudentCount> findSemesterByCourseId(@Param("courseId") int courseId);

}
