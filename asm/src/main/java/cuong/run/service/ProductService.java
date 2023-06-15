package cuong.run.service;

import cuong.run.web.request.ReqCart;
import cuong.run.web.response.CartResponse;
import cuong.run.web.response.ProductResponse;

public interface ProductService {
	
	CartResponse addCart(ReqCart reqCart);
	
	CartResponse update(ReqCart reqCart);
	
	ProductResponse toProductResponse();
}
