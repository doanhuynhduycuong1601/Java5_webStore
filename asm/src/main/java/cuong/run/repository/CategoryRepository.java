package cuong.run.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import cuong.run.model.Category;

@Repository
public interface CategoryRepository extends CommonRepository<Category, Integer> {
	<T> List<T> findBy(Class<T> classType);
	
	
	
}
