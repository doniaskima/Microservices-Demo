package com.assignment.AssignmentService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.assignment.AssignmentService.entity.AssignmentEntity;
 

@Repository
public interface AssignmentRepository extends  MongoRepository<AssignmentEntity, String> {

}
