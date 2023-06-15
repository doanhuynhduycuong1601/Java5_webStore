package cuong.run.web.resp;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class RespOrder {
	public Integer orderId;
	public String dateAt;
	public String totalAmount;
	public String address;
	public Integer status;
	public String phone;
}
