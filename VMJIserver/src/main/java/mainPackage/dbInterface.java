package mainPackage;

import java.util.List;
import java.util.Map;

public interface dbInterface<T> {

	public List<T> SelectEntity();
	public void addEntity(T entity);
	public void updateEntity(T entity);
	public void removeEntity(T data);
	public void removeEntity(Long id);
	
}