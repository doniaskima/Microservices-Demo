package com.assignment.AssignmentService.service;

import java.util.List;

import com.assignment.AssignmentService.entity.AssignmentEntity;
 

public interface AssignmentService {

	// create AssignmentEntity
		public AssignmentEntity createAssignment(AssignmentEntity AssignmentEntity);

		// fetch all AssignmentEntity
		public  List<AssignmentEntity> getAllAssignments();

		// fetch AssignmentEntity by id
		List<AssignmentEntity> findAssignmentByUserId(int Userid);
}
