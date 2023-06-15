package cuong.run.pojo;


import cuong.run.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class ManagerProductPojo {
	Integer id;
	String dateAt;

	@NotBlank(message = "Name không được để trống")
	String names;
	@cuong.run.validator.Integer(min = 1, max = 10000, name = "Quantity")
	String quantity;
	String img;

	@cuong.run.validator.Double(max = 1000000000,min = 1, name = "Price")
	String price;
	@NotBlank(message = "Bạn chưa nhập Mô tả")
	String descript;
	Boolean existss = true;
	Category category;
}
