package cuong.run.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import cuong.run.repository.ProductRepository;
import cuong.run.repository.projection.ProductProjection;
import cuong.run.service.ProductSearchService;
import cuong.run.utils.BuilderProduct;
import cuong.run.web.response.ProductResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {
	final SerParam param;
	final ProductRepository productRepository;
	final BuilderProduct builderProduct;
	String search = "";
	int size = 8;
	
	//trangSearch
	public void search() {
		this.search = param.getString("search", "");
	}
	
	public Page<ProductResponse> search(int page, Sort sort){
		return builderProduct
				.toProductResponses(
						productRepository
						.findBy("%"+search+"%",
								PageRequest.of(page, size, sort),ProductProjection.class));
	}
	
	
	//trang home sort date
	public Page<ProductResponse> home(int page, Sort sort){
		return builderProduct
				.toProductResponses(
						productRepository
						.findBy(ProductProjection.class,
								PageRequest.of(page, size, sort)));
	}
	
}
