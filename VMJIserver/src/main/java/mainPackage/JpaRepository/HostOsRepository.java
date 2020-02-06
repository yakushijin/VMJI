package mainPackage.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mainPackage.entity.HostOsEntity;

@Repository
public interface HostOsRepository extends JpaRepository<HostOsEntity, Integer> {

}