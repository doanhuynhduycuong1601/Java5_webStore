package cuong.run.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CartResponse {
	Integer quantity;
	
	Integer getCount;
}
