package mainPackage;

import org.springframework.beans.factory.annotation.Autowired;
import mainPackage.JpaRepository.GuestOsRepository;
import mainPackage.JpaRepository.HostOsRepository;
import mainPackage.JpaRepository.OperationContentsRepository;
import mainPackage.JpaRepository.OperationLogRepository;
import mainPackage.JpaRepository.UserRepository;

public abstract class AbstractDbInterface<T> implements dbInterface<T> {
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected GuestOsRepository guestOsRepository;
	
	@Autowired
	protected HostOsRepository hostOsRepository;
	
	@Autowired
	protected OperationLogRepository operationLogRepository;

	@Autowired
	protected OperationContentsRepository operationContentsRepository;
	
}
