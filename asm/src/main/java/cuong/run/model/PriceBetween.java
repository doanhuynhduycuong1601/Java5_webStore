package cuong.run.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "PRICEBETWEEN")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceBetween {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = 0;
	
	@Column(name = "NAMES")
	private String name;
	
	@Column(name = "IDCATEGORY")
	private Integer category;
	
	@Column(name = "PRICE")
	private String price;
}
