package cuong.run.utils;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import cuong.run.model.Product;
import cuong.run.pojo.Cart;
import cuong.run.repository.projection.ProductProjection;
import cuong.run.service.impl.ShoppingCartServiceImpl;
import cuong.run.web.response.ProductResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BuilderProduct {
	final ShoppingCartServiceImpl shoppingCartService;
	
	public Page<ProductResponse> toProductResponses(Page<ProductProjection> productProjections){
		return productProjections.map(this::toProductResponse);
	}
	
	public Page<ProductResponse> toProductResponsess(Page<Product> products){
		return products.map(this::toProductResponse);
	}
	
	
	public List<ProductResponse> toProductResponses(List<ProductProjection> productProjections){
		return productProjections
				.stream()
				.map(this::toProductResponse)
				.toList();
	}
	
	public ProductResponse toProductResponse(ProductProjection productProjection) {
		Cart cart = shoppingCartService.getCart().get(productProjection.getId());
		int quantityCart = 0;
		if(cart != null) {
			quantityCart = cart.getQuantityCart();
		}

		String status = productProjection.getExistss()? "Đang kinh doanh" : "Ngừng kinh doanh";
		return ProductResponse
				.builder()
				.id(productProjection.getId())
				.img(productProjection.getImg())
				.names(productProjection.getNames())
				.price(GetString.getVnd(productProjection.getPrice()))
				.quantity(productProjection.getQuantity())
				.descript(productProjection.getDescript())
				.nameCategory(productProjection.getCategory().getNames())
				.quantityCart(quantityCart)
				.status(status)
				.build();
	}
	
	
	public ProductResponse toProductResponse(Product product) {
		Cart cart = shoppingCartService.getCart().get(product.getId());
		int quantityCart = 0;
		if(cart != null) {
			quantityCart = cart.getQuantityCart();
		}
		
		String status = product.getExistss()? "Đang kinh doanh" : "Ngừng kinh doanh";

		return ProductResponse
				.builder()
				.id(product.getId())
				.img(product.getImg())
				.names(product.getNames())
				.price(GetString.getVnd(product.getPrice()))
				.quantity(product.getQuantity())
				.descript(product.getDescript())
				.nameCategory(product.getCategory().getNames())
				.quantityCart(quantityCart)
				.status(status)
				.build();
	}
	
}
