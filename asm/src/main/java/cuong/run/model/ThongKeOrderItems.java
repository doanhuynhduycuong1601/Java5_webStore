package cuong.run.model;

import java.text.DecimalFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@ToString
public class ThongKeOrderItems {
	@Id
	Integer id;
	
	String names;
	
	String img;
	
	Double price;
	
	public String getTotalPrice() {
		DecimalFormat decimalFormat = new DecimalFormat("###,###");
		return decimalFormat.format(price)+" vnd";
	}
	
	Long quantity;

	public ThongKeOrderItems(Integer id, String names, String img, Double price, Long quantity) {
		super();
		this.id = id;
		this.names = names;
		this.img = img;
		this.price = price;
		this.quantity = quantity;
	}
	
	

	
	
}
