package cuong.run.pojo;

import cuong.run.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginPojo {
	@Pattern(regexp = "^\\w{3,}+@\\w{3,}+(\\.\\w{2,}+){1,2}$", message = "Bạn phải nhập đúng định dạng email")
	private String email;
	
	@Pattern(regexp = "^\\w{6,40}$", message = "Mật khẩu ít nhất phải 6 ký tự")
	private String passwords;
	
	public boolean equals(LoginPojo obj) {
		if(this.email.equals(obj.getEmail()) && this.passwords.equals(obj.getPasswords())) {
			return true;
		}
		return false;
	}
}
