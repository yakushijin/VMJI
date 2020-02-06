package mainPackage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.persistence.EntityManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class Main {

	//private static JdbcTemplate jdbcTemplate;
	public static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/application-config.xml");
	
	private static EntityManager manager;
		
	public static void main(String[] args) {
		
		LocalContainerEntityManagerFactoryBean factory =
				context.getBean(LocalContainerEntityManagerFactoryBean.class);
		manager = factory.getNativeEntityManagerFactory().createEntityManager();
		

	}

}
