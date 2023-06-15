package cuong.run.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cuong.run.model.Product;
import cuong.run.repository.PriceBetweenRepository;
import cuong.run.repository.ProductRepository;
import cuong.run.repository.interfaces.PriceBetweenInterface;
import cuong.run.service.SearchByCategoryService;
import cuong.run.spec.SearchByCategorySpec;
import cuong.run.utils.BuilderProduct;
import cuong.run.web.request.ProductSearchRequest;
import cuong.run.web.response.PriceBetweenResponse;
import cuong.run.web.response.ProductResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchByCategoryServiceImpl implements SearchByCategoryService {
	final PriceBetweenRepository betweenRepository;
	final ProductRepository productRepository;
	final SearchByCategorySpec searchByCategoryFilter;
	final BuilderProduct builderProduct;
	
	private List<PriceBetweenInterface> priceBetween;
	private Specification<Product> spec;
	
	
	private int category = 1;
	
	
	public Page<ProductResponse> getProductResponses(int page){
		Page<Product> products = productRepository.findAll(this.spec, PageRequest.of(page, 9));
		return builderProduct.toProductResponsess(products);
	}
	
	
	public void setSpec(ProductSearchRequest productSearchRequest) {
		this.spec = searchByCategoryFilter.getSearchSpecification(productSearchRequest, category);
	}
	
	public void setCategory(int category) {
		this.category = category;
	}
	
	public List<PriceBetweenResponse> getPriceBetweenResponses(){
		return priceBetween.stream()
				.filter(price -> price.getCategory() == category)
				.map(this::toPriceBetweenResponse).toList();
	}
	
	@PostConstruct
	private void addPriceBetween() {
		priceBetween = betweenRepository.findBy(PriceBetweenInterface.class);
	}
	
	public PriceBetweenResponse toPriceBetweenResponse(PriceBetweenInterface priceBetweenInterface) {
		return PriceBetweenResponse
				.builder()
				.label(priceBetweenInterface.getName())
				.value(priceBetweenInterface.getPrice())
				.build();
	}
}
