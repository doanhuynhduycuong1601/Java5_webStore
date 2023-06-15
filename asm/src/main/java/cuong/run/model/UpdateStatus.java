package cuong.run.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "UPDATESTATUS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_update")
	private LocalDateTime dateUpdate = LocalDateTime.now();
	
	private String descriptions;

	
	private String gmail;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
	private Orders orders;
	


	public UpdateStatus(Integer id, String gmail) {
		super();
		this.id = id;
		this.gmail = gmail;
	}
	
	
	
	public String getDate() {
		return dateUpdate.getYear() + "/"+dateUpdate.getMonthValue()+"/"+dateUpdate.getDayOfMonth();
	}



	public UpdateStatus(Integer id, String descriptions, String gmail) {
		super();
		this.id = id;
		this.descriptions = descriptions;
		this.gmail = gmail;
	}
	
	
	
}
