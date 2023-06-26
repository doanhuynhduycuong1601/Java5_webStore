package cuong.run.service.api;

import java.util.List;

import org.springframework.data.domain.Page;

import cuong.run.web.resp.RespUpdateStatus;
import cuong.run.web.response.CategoryResponse;
import cuong.run.web.response.OrderItemsDetailResponse;
import cuong.run.web.response.ProductResponse;

public interface APIProductService {
	ProductResponse toProductResponse(Integer id);
	
	Page<ProductResponse> getProductDate(Integer page);
	Page<ProductResponse> getProductPrice(Integer page);
	Page<ProductResponse> getProductSearch(String search,Integer page);
	
	List<CategoryResponse> getAllCategory();
	
	
	List<OrderItemsDetailResponse> getOrderDetail(int id);
	
	List<RespUpdateStatus> getOrderStatus(int id);
}
