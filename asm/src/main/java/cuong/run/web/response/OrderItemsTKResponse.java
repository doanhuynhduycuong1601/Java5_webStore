package cuong.run.web.response;

import cuong.run.web.resp.RespOrderItems;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
@SuperBuilder
@Getter
public class OrderItemsTKResponse extends RespOrderItems {
	
	OrderResponse order;
}
