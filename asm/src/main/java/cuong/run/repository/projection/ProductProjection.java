package cuong.run.repository.projection;

import cuong.run.repository.interfaces.CategoryInterface;
import cuong.run.repository.interfaces.ProductInterface;

public interface ProductProjection extends ProductInterface{
	CategoryInterface getCategory();
}
