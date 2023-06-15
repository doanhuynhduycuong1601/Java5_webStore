package cuong.run.pojo;

import java.text.DecimalFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Cart {
	private Integer id;
	private String names;
	private Integer quantity;
	private String img;
	private Double price;
	
	private Integer quantityCart;
	
	public String priceFormat() {
		DecimalFormat decimalFormat = new DecimalFormat("###,###");
		return decimalFormat.format(price) + " vnd";
	}
	
	
	@Override
	public String toString() {
		return "Name SP : " + names + " / Giá : " +price + " / Số lượng mua : " + quantityCart + "\n" ;
	}
}
