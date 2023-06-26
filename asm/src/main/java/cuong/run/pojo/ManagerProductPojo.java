package cuong.run.pojo;


import cuong.run.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerProductPojo {
	Integer id = 0;
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
