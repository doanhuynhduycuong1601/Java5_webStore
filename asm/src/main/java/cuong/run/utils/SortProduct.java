package cuong.run.utils;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.TypedSort;

import cuong.run.model.Product;

public class SortProduct {
	public static Sort sortPrice() {
		TypedSort<Product> product = Sort.sort(Product.class);
		return  product.by(Product::getPrice).descending();
	}
	
	
	public static Sort sortDate() {
		TypedSort<Product> product = Sort.sort(Product.class);
		return  product.by(Product::getDateAt).descending().and(product.by(Product::getPrice).descending());
	}
}
