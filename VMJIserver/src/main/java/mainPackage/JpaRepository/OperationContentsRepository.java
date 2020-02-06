package mainPackage.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mainPackage.entity.OperationContentsEntity;
import mainPackage.entity.OperationLogEntity;

@Repository
public interface OperationContentsRepository extends JpaRepository<OperationContentsEntity, Integer> {

}