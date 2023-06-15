package cuong.run.web.resp;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class RespProduct {
	private Integer id;
	private String names;
	private Integer quantity;
	private String img;
	private String descript;
	private String price;
	
	private String status;
}
