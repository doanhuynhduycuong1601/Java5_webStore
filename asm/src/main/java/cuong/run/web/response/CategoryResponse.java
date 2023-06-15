package cuong.run.web.response;

import cuong.run.web.resp.RespCategory;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CategoryResponse extends RespCategory {
	
	Integer quantity;
}
