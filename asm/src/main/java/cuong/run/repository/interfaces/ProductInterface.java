package cuong.run.repository.interfaces;

import java.time.LocalDateTime;
public interface ProductInterface {
	Integer getId();

	String getNames();

	String getDescript();

	String getImg();

	Integer getQuantity();

	Double getPrice();

	LocalDateTime getDateAt();
	
	Boolean getExistss();
}
