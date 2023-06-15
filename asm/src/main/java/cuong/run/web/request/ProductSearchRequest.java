package cuong.run.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {
//	String []brands;
	
	//truyền theo kiểu dấu , vd từ 0 đến 1trieu : 0.0,1000000.0
	String []priceBetWeen = null;
}
