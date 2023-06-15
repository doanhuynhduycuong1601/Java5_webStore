package cuong.run.model;
import java.time.LocalDateTime;
import java.util.List;

import cuong.run.pojo.ManagerProductPojo;
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
@Table(name = "Product")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = 0;
	
	
	//lưu kiểu dữ liệu ngày giờ
	@Column(name = "date_at")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dateAt = LocalDateTime.now();
	private String names;
	private Integer quantity;
	private String img;
	private String descript;
	private Double price;
	private LocalDateTime dateDelete;
	
	private Boolean existss = true;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_Id")
    private Category category;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrdersItems> orderItems;
	
	
	@Override
	public String toString() {
		return "id: "+getId()+ " / Name: " +names ;
	}
	
	public String getDateAtt() {
		return dateAt.getYear()+"/"+dateAt.getMonthValue()+"/"+dateAt.getDayOfMonth();
	}
	
	
	public void set(ManagerProductPojo managerProductPojo) {
		this.names = managerProductPojo.getNames();
		this.img =  managerProductPojo.getImg();
		this.existss = managerProductPojo.getExistss();
		this.price = Double.valueOf(managerProductPojo.getPrice());
		this.quantity = Integer.valueOf(managerProductPojo.getQuantity());
		this.descript = managerProductPojo.getDescript();
		this.category = managerProductPojo.getCategory();
	}
	
	
	
	public Product(Integer id, String names, Integer quantity, String img, String descript, Double price, Category category) {
		super();
		this.id = id;
		this.names = names;
		this.quantity = quantity;
		this.img = img;
		this.descript = descript;
		this.price = price;
		this.category = category;
	}

	public Product(String names, Integer quantity, String img, String descript, Double price, Category category) {
		super();
		this.names = names;
		this.quantity = quantity;
		this.img = img;
		this.descript = descript;
		this.price = price;
		this.category = category;
	}

	public Product(Integer id) {
		super();
		this.id = id;
	}

	
	
	//học them
	//@Transient không lưu vào database
//	@Transient
//	String ok;
	
	//Được gọi trước khi lưu xuống cơ sở dữ liệu
//	@PrePersist
//	private void PrePersist() {
//		System.out.println("PrePersist");
//	}
	
	//Được gọi sau khi tải cơ sở dữ liệu
//	@PostLoad
//	private void PostLoad() {
//		System.out.println("PostLoad");
//	}
	
}
