package cuong.run.web.resp;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class RespUpdateStatus {
	private Integer id;
	private String gmail;
	private String date;
	private String description;
}
