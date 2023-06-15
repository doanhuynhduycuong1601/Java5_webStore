package cuong.run.service;

import java.util.List;
import org.springframework.data.domain.Page;
import cuong.run.repository.interfaces.PriceBetweenInterface;
import cuong.run.web.request.ProductSearchRequest;
import cuong.run.web.response.PriceBetweenResponse;
import cuong.run.web.response.ProductResponse;

public interface SearchByCategoryService {
	Page<ProductResponse> getProductResponses(int page);
	
	void setSpec(ProductSearchRequest productSearchRequest);
	
	void setCategory(int category);
	
	List<PriceBetweenResponse> getPriceBetweenResponses();
	
	
	PriceBetweenResponse toPriceBetweenResponse(PriceBetweenInterface priceBetweenInterface);
}
