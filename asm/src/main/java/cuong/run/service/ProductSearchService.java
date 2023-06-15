package cuong.run.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import cuong.run.web.response.ProductResponse;

public interface ProductSearchService {

	public void search();

	
	public Page<ProductResponse> search(int page, Sort sort);
	
	
	//trang home sort date
	public Page<ProductResponse> home(int page, Sort sort);
	
}
