package cuong.run.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "ORDERS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Integer orderID;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
	private Customer customerID;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_DATE")
	private LocalDateTime orderDate = LocalDateTime.now();
	
	@Column(name = "TOTAL_AMOUNT")
	private Double totalAmount;
	
	private String phone;
	
	
	private String addresss;
	
	private Integer statuss;
	
	@OneToMany(mappedBy = "orders",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<UpdateStatus> updateStatus;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<OrdersItems> orderItems;
}
