package cuong.run.web.resp;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class RespOrderItems {
	Integer id;
	Integer quantity;
	String price;
	Integer itemReturn;
	
}
