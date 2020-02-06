package mainPackage.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mainPackage.entity.GuestOsEntity;

@Repository
public interface GuestOsRepository extends JpaRepository<GuestOsEntity, Integer> {

}