package cuong.run.web.response;

import cuong.run.web.resp.RespOrder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
@SuperBuilder
@Getter
public class OrderResponse extends RespOrder {
	public String email;
}
