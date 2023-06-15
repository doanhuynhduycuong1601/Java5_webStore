package cuong.run.service.impl;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import cuong.run.exception.StringException;
import cuong.run.model.Product;
import cuong.run.pojo.Cart;
import cuong.run.repository.ProductRepository;
import cuong.run.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
	final ProductRepository productRepository;
	DecimalFormat decimalFormat = new DecimalFormat("###,###");
	Map<Integer, Cart> items = new HashMap<>();

	@Override
	public Cart add(Integer id) {
		return null;
	}

	@Override
	public void remove(Integer id) {
		items.remove(id);
	}

	@Override
	public Cart update(Integer id, int qty) {
		Product product = productRepository.findById(id).get();
		if(product == null || !product.getExistss()) {
			throw new StringException(String.format("Sản phẩm %s hiện tại đang không còn kinh doanh.", product.getNames()));
		}
		Cart cartResponse = toCart(product);
		cartResponse.setQuantityCart(items.get(id)==null? 1 : qty);
		return items.put(id, cartResponse);
	}
	@Override
	public int getQuantity(int key) {
		return items.get(key)==null? 0 : items.get(key).getQuantityCart();
	}

	@Override
	public void clear() {
		items.clear();

	}

	@Override
	public Collection<Cart> getItems() {
		return items.values();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	
	@Override
	public double getAmount() {
		double result = 0;
		for (Map.Entry<Integer, Cart> entry : items.entrySet()) {
			result += entry.getValue().getQuantityCart() * entry.getValue().getPrice(); 
		}
		return result;
	}

	@Override
	public int getTotalCount() {
		int result = 0;
		for (Map.Entry<Integer, Cart> entry : items.entrySet()) {
			result += entry.getValue().getQuantityCart();
		}
		return result;
	}
	
	@Override
	public Map<Integer, Cart> getCart() {
		return items;
	}
	
	public Cart toCart(Product product) {
		return Cart
				.builder()
				.id(product.getId())
				.names(product.getNames())
				.img(product.getImg())
				.price(product.getPrice())
				.quantity(product.getQuantity())
				.build();
	}

	@Override
	public String getAmountFormat() {
		return decimalFormat.format(this.getAmount()) + " vnd";
	}

}
