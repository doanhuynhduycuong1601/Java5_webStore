package cuong.run.pojo;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ChangePass {
	@Pattern(regexp = "^\\w{6,40}$", message = "Mật khẩu hiện tại ít nhất phải 6 ký tự")
	private String passCurrent;
	
	@Pattern(regexp = "^\\w{6,40}$", message = "Mật khẩu mới ít nhất phải 6 ký tự")
	private String passNew;
	
	@Pattern(regexp = "^\\w{6,40}$", message = "Xác nhận mật khẩu ít nhất phải 6 ký tự")
	private String passConfirm;
	
	
}
