package cuong.run.service.api.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import cuong.run.model.Product;
import cuong.run.repository.CategoryRepository;
import cuong.run.repository.OrderItemsRepository;
import cuong.run.repository.ProductRepository;
import cuong.run.repository.UpdateStatusRepository;
import cuong.run.repository.interfaces.UpdateStatusInterface;
import cuong.run.repository.projection.CategoryProjection;
import cuong.run.repository.projection.ProductProjection;
import cuong.run.service.InforPageService;
import cuong.run.service.Param;
import cuong.run.service.api.APIProductService;
import cuong.run.utils.BuilderCategory;
import cuong.run.utils.BuilderOrderStatus;
import cuong.run.utils.BuilderOrderToOrderResp;
import cuong.run.utils.BuilderProduct;
import cuong.run.utils.SortProduct;
import cuong.run.web.resp.RespUpdateStatus;
import cuong.run.web.response.CategoryResponse;
import cuong.run.web.response.OrderItemsDetailResponse;
import cuong.run.web.response.ProductResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class APIProductServiceImpl implements APIProductService {
	final Param param;
	final ProductRepository productRepository;
	final CategoryRepository categoryRepository;
	final OrderItemsRepository orderItemsRepository;
	final BuilderProduct builderProduct;
	final BuilderCategory builderCategory;
	final BuilderOrderToOrderResp builderOrderToOrderResp;
	final InforPageService inforPageService;
	final BuilderOrderStatus builderOrderStatus;
	final UpdateStatusRepository updateStatusRepository;
	
	@Cacheable(cacheNames = "product", key = "#id")
	@Override
	public ProductResponse toProductResponse(Integer id) {
		return getProductResponse(id);
	}
	
	
	@Cacheable(cacheNames = "productDate", key = "#page")
	@Override
	public Page<ProductResponse> getProductDate(Integer page) {
		return getProductPage(page, SortProduct.sortDate());
	}
	
	@Cacheable(cacheNames = "productPrice", key = "#page")
	@Override
	public Page<ProductResponse> getProductPrice(Integer page) {
		return getProductPage(page, SortProduct.sortPrice());
	}
	
	@Cacheable(cacheNames = "productSearch",  key = "#search + #page")
	@Override
	public Page<ProductResponse> getProductSearch(String search,Integer page) {
		return getProductPage(search,page, SortProduct.sortDate());
	}
	
	@Cacheable(cacheNames = "categories")
	@Override
	public List<CategoryResponse> getAllCategory() {
		return builderCategory
				.toCategoryResponses(categoryRepository.findBy(CategoryProjection.class));
	}
	
	
	public Page<ProductResponse> getProductPage(Integer page, Sort sort) {
		Pageable pageable = PageRequest.of(page, 8, sort);
		Page<ProductProjection> data = productRepository
				.findBy(ProductProjection.class, pageable);
		return builderProduct.toProductResponses(data);
	}

	public Page<ProductResponse> getProductPage(String search,Integer page, Sort sort){
		Pageable pageable = PageRequest.of(page, 8, sort);
		Page<ProductProjection> data = productRepository
				.findBy("%"+search+"%",pageable,ProductProjection.class);
		return builderProduct.toProductResponses(data);
	}
	
	
	ProductResponse getProductResponse(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			return builderProduct.toProductResponse(product.get());
		}
		return null;
	}


	@Cacheable(cacheNames = "orderDetail",  key = "#id")
	@Override
	public List<OrderItemsDetailResponse> getOrderDetail(int id) {
		return builderOrderToOrderResp.toRespOrderItems(orderItemsRepository.findBy(id));
	}


	@Cacheable(cacheNames = "orderStatus",  key = "#id")
	@Override
	public List<RespUpdateStatus> getOrderStatus(int id) {
		List<UpdateStatusInterface> data = 
				updateStatusRepository.findBy(id, UpdateStatusInterface.class);
		return builderOrderStatus.respUpdateStatus(data);
	}
}
