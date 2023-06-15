package cuong.run.repository.projection;

import java.util.List;

import cuong.run.repository.interfaces.CategoryInterface;
import cuong.run.repository.interfaces.ProductInterface;

public interface CategoryProjection extends CategoryInterface {	
	List<ProductInterface> getProducts();
}
