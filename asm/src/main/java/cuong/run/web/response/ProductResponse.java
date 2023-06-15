package cuong.run.web.response;

import cuong.run.web.resp.RespProduct;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ProductResponse extends RespProduct {

	private String nameCategory;
	
	private Integer quantityCart;
}
