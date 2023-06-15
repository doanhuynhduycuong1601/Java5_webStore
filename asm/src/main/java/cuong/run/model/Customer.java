package cuong.run.model;

import java.util.List;

import cuong.run.pojo.LoginPojo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String fullname;
	private String email;
	private String passwords;
	private String phone;
	private Integer age;
	private Boolean gender = true;
	private String addresss;
	@Column(name = "active")
	private Boolean existss = true;
	private Boolean admins = false;
	
	public void set(LoginPojo loginPojo) {
		this.email = loginPojo.getEmail();
		this.passwords = loginPojo.getPasswords();
	}
	
	
	@OneToMany(mappedBy = "customerID", fetch = FetchType.LAZY)
    private List<Orders> customerID;
}
