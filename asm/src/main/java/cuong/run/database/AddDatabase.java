package cuong.run.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Component;
import cuong.run.model.Category;
import cuong.run.model.Customer;
import cuong.run.model.Orders;
import cuong.run.model.OrdersItems;
import cuong.run.model.Product;
import cuong.run.model.UpdateStatus;
import cuong.run.repository.CategoryRepository;
import cuong.run.repository.CustomerRepository;
import cuong.run.repository.OrderRepository;
import cuong.run.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

//@Component
@RequiredArgsConstructor
public class AddDatabase {
	final CategoryRepository categoryRepository;
	final ProductRepository productRepository;
	final CustomerRepository customerRepository;
	final OrderRepository orderRepository;

	@PostConstruct
	public void addAll() {
		addCategory();
		addProducts();
		addCustomer();
		addOrder();
		updateOrder(1, 2500);
		
	}
	
	
	public void updateOrder(int start, int end) {
		for(int i=start;i<=end;i++) {
			Orders orders = orderRepository.findById(i).get();
			orders.setUpdateStatus(status(orders));
			orderRepository.save(orders);
		}
	}
	
	public List<UpdateStatus> status(Orders orders) {
		Random random = new Random();
		List<UpdateStatus> list = new ArrayList<>();
		int status = random.nextInt(5);
		orders.setStatuss(status);
		UpdateStatus updateStatus = new UpdateStatus();
		switch (status) {
		case 0:
			list.add(cancel(orders));
			break;
		case 1:
			return null;

		case 2:
			list.add(confirm(orders));
			break;
		
		case 3:
			list.add(confirm(orders));
			list.add(ship(orders));
			break;
		case 4:
			list.add(confirm(orders));
			list.add(ship(orders));
			list.add(success(orders));
			break;
		}
		updateStatus.setOrders(orders);
		return list;
	}
	
	public UpdateStatus cancel(Orders orders) {
		UpdateStatus updateStatus = new UpdateStatus();
		updateStatus.setDescriptions("Người đặt không nhận");
		updateStatus.setGmail("admin@gmail.com");
		updateStatus.setOrders(orders);
		return updateStatus;
	}
	
	public UpdateStatus confirm(Orders orders) {
		UpdateStatus updateStatus = new UpdateStatus();
		updateStatus.setOrders(orders);
		updateStatus.setDescriptions("Đã xác nhận sẽ giao đến bạn sớm nhất");
		updateStatus.setGmail("admin@gmail.com");
		return updateStatus;
	}
	
	public UpdateStatus ship(Orders orders) {
		UpdateStatus updateStatus = new UpdateStatus();
		updateStatus.setOrders(orders);
		updateStatus.setDescriptions("Đang giao");
		updateStatus.setGmail("admin@gmail.com");
		return updateStatus;
	}
	
	public UpdateStatus success(Orders orders) {
		UpdateStatus updateStatus = new UpdateStatus();
		updateStatus.setOrders(orders);
		updateStatus.setDescriptions("Giao thành công");
		updateStatus.setGmail("admin@gmail.com");
		return updateStatus;
	}
	
	public void addOrder() {
		Random random = new Random();
		LocalDate date = LocalDate.of(2018, 1, 1);
		LocalDateTime datetime =  LocalDateTime.of(date, LocalTime.now());
		for (int i = 1; i <= 2500; i++) {
			int idCustomer = random.nextInt(32)+1;
			Customer customer = customerRepository.findById(idCustomer).get();
			Orders orders = new Orders();
			orders.setCustomerID(customer);
			orders.setAddresss(customer.getAddresss());
			orders.setPhone(customer.getPhone());
			orders.setStatuss(1);
			orders.setOrderID(i);
			Map<Integer,OrdersItems> orderItems = addOrderItems(orders);
			orders.setOrderItems(new ArrayList<>(orderItems.values()));
			orders.setTotalAmount(totalAmount(orderItems));
			
			datetime = datetime.plusHours(random.nextInt(14)+1);
			orders.setOrderDate(datetime);
			orderRepository.save(orders);
			
		}
	}
	
	
	public Double totalAmount(Map<Integer,OrdersItems> orderItems) {
		return orderItems.values().stream()
				.mapToDouble(item -> item.getPrice()*item.getQuantity()).sum();
	}
	
	public Map<Integer,OrdersItems> addOrderItems(Orders orders) {
		Random random = new Random();
		Map<Integer,OrdersItems> map = new HashMap<>();
		int slBuy = random.nextInt(10)+1;
		for(int i=1; i<= slBuy;i++) {
			int quantity = random.nextInt(5)+1;
			int idProduct = random.nextInt(1000)+1;
			Product p = productRepository.findById(idProduct).get();
			OrdersItems items = new OrdersItems();
			items.setOrder(orders);
			items.setProduct(p);
			items.setQuantity(quantity);
			items.setPrice(p.getPrice());
			map.put(p.getId(), items);
		}
		return map;
	}
	
	
	
	public void addCustomer() {
		Random random = new Random();
		
		//customer 
		Customer guest = customer();
		guest.setFullname("customer");
		guest.setEmail("customer@gmail.com");
		guest.setAdmins(false);
		customerRepository.save(guest);
		
		//admin 
		Customer admin = customer();
		admin.setFullname("admin");
		admin.setEmail("admin@gmail.com");
		admin.setAdmins(true);
		customerRepository.save(admin);
		
		for(int i=3;i<=32;i++) {
			//1,2,3 là khách hàng
			int admins = random.nextInt(4)+1;
			Object []array = this.customer(i, admins);
			Customer customer = customer();
			customer.setFullname((String)array[0]);
			customer.setEmail((String)array[1]);
			customer.setAdmins((Boolean)array[2]);
			customerRepository.save(customer);
		}
	}
	
	public Customer customer() {
		Random random = new Random();
		Customer customer = new Customer();
		customer.setPasswords("123456");
		customer.setAddresss(address());
		customer.setAge(random.nextInt(60)+1);
		customer.setPhone(randomNumber());
		return customer;
	}
	
	public String randomNumber() {
		String number = "0";
		Random random = new Random();
		for(int i =0; i<9;i++) {
			number += random.nextInt(10);
		}
		return number;
	}
	
	public String address() {
		Random random = new Random();
		int address = random.nextInt(9)+1;
		switch (address) {
		case 1:
			return "Hà Nội";
		case 2:
			return "Hồ Chí Minh";
		case 3:
			return "Bình Dương";
		case 4:
			return "Đà Lạt";
		case 5:
			return "Vũng tàu";
		case 6:
			return "Quảng Nam";
		case 7:
			return "Quảng Ninh";
		case 8:
			return "Hà Nội";

		default:
			return "Cần giờ";
		}
	}
	
	public Object[] customer(int i,int random) {
		Object []array = new Object[3];
		switch (random) {
		case 1,2,3:
			array[0] = "Tên customer : " + i;
			array[1] = "customer"+i+"@gmail.com";
			array[2] = false;
			break;

		default:
			array[0] = "Tên admin : " + i;
			array[1] = "admin"+i+"@gmail.com";
			array[2] = true;
			break;
		}
		return array;
	}
	
	

	public void addCategory() {
		String[] arrays = new String[] { "Laptop", "Phone", "Tablets", "Phụ kiện" };
		for (String string : arrays) {
			categoryRepository.save(new Category(string));
		}
	}

	public void addProducts() {
		Random random = new Random();
		for (int i = 1; i <= 1000; i++) {
			int category = random.nextInt(4) + 1;
			int quantity = random.nextInt(100)+10000;
			String[] name = nameImg(i, category);
			Product product = new Product(i,name[0], quantity, name[1], name[2], (double)(int)Math.round(money(category)), categoryRepository.findById(category).get());
			productRepository.save(product);
		}
	}
	
	
	public double money(int category) {
		Random random = new Random();
		double money = random.nextDouble(40);
		if (category == 1) {
			return (money * 500000 + 500000) * 1.8;
		}else if (category == 2){
			return (money * 400000 + 200000) * 1.5;
		}else if (category == 3){
			return (money * 300000 + 100000) * 1.2;
		}
		return money * 30000 + 50000;
		
	}

	public String[] nameImg(int i, int category) {
		if (category == 1) {
			return laptop(i);
		}else if (category == 2){
			return phone(i);
		}else if (category == 3){
			return tablets(i);
		}
		
		return pk(i);
	}
	
	public String[] pk(int i) {
		Random random = new Random();
		int num = random.nextInt(5) + 1;
		String[] pk = new String[3];
		switch (num) {
		case 1:
			pk[0] = "Tai nghe " + i;
			pk[1] = "24.webp";
			pk[2] = "Tai nghe Bluetooth SoundPEATS Life Lite cung cấp kết nối Bluetooth 5.3 tiên tiến, micro ENC kép cho cuộc gọi rõ ràng, thời gian chơi nhạc lên đến 23 tiếng, âm thanh sống động với trình điều khiển 10nm và chế độ trò chơi với độ trễ cực thấp.";
			break;

		case 2:
			pk[0] = "ốp lưng " + i;
			pk[1] = "25.webp";
			pk[2] = "Tai nghe Bluetooth SoundPEATS Life Lite cung cấp kết nối Bluetooth 5.3 tiên tiến, micro ENC kép cho cuộc gọi rõ ràng, thời gian chơi nhạc lên đến 23 tiếng, âm thanh sống động với trình điều khiển 10nm và chế độ trò chơi với độ trễ cực thấp";
			break;
		case 3:
			pk[0] = "Ổ cứng " + i;
			pk[1] = "26.webp";
			pk[2] = "Không có thông tin";
			break;
		case 4:
			pk[0] = "Chuột " + i;
			pk[1] = "27.webp";
			pk[2] = "Không chỉ ghi điểm bởi thiết kế gọn gàng, thanh lịch, chuột không dây Targus W600 còn đem tới sự hỗ trợ đắc lực trong công việc và học tập của bạn nhờ độ phân giải quang học lên tới 1.600 DPI. Sản phẩm sử dụng cơ chế tương tác không dây tiện lợi thông qua kết nối bằng đầu thu USB nhanh nhạy.";
			break;
		case 5:
			pk[0] = "Củ sạc " + i;
			pk[1] = "28.webp";
			pk[2] = "Có thiết kế độc đáo, thông minh sao cho tiết kiệm không gian trên bàn làm việc nhất mà vẫn có thể sạc hiệu quả cùng lúc cho iPhone 13 và tai nghe AirPods, bộ sạc Hyperjuice 2 trong 1 chính là lựa chọn tối ưu nhất cho các iFan hiện nay khi tìm kiếm phương án sạc không dây cho các thiết bị của mình.\r\n"
					+ "\r\n"
					+ "";
			break;
		}
		
		return pk;
	}
	
	public String[] tablets(int i) {
		Random random = new Random();
		int num = random.nextInt(6) + 1;
		String[] tablet = new String[3];
		switch (num) {
		case 1:
			tablet[0] = "Apple (iPad) " + i;
			tablet[1] = "18.webp";
			tablet[2] = "iPad Gen 9 10.2 2021 mang trên mình bộ vi xử lý Apple A13 Bionic cực mạnh, giúp tất cả đều diễn ra một cách mượt mà và nhanh chóng. Từ duyệt web, xem phim cho đến chơi game hay thậm chí là chạy nhiều ứng dụng cùng lúc, iPad đều hoàn thành xuất sắc. GPU mạnh hơn tới 20%, cho hiệu suất đồ họa hoàn hảo, mang tới khả năng chỉnh sửa ảnh, video hay chơi game đỉnh cao. Hãy làm mọi thứ bạn muốn, iPad Gen 9 10.2 2021 đủ hiệu năng để chạy mượt trong nhiều năm nữa.";
			break;

		case 2:
			tablet[0] = "Samsung " + i;
			tablet[1] = "19.webp";
			tablet[2] = "Gặp gỡ Samsung Galaxy Tab S6 Lite 2022 - phiên bản mới với sự nâng cấp mạnh về hiệu năng Snapdragon 720G, cho mọi tác vụ mượt mà, đa nhiệm hoàn hảo từ học tập, làm việc đến giải trí. Sự hỗ trợ của bút S-Pen còn là người bạn tuyệt vời để thỏa thích sáng tạo, tăng hiệu suất công việc.";
			break;
		case 3:
			tablet[0] = "Lenovo " + i;
			tablet[1] = "20.webp";
			tablet[2] = "Sự phong cách và vẻ đẹp trong thiết kế của máy tính bảng Tab M8-Gen 2 đến từ cách Lenovo sử dụng chất liệu kim loại để hoàn thiện mặt lưng thiết bị. Nhờ công nghệ hoàn thiện tốt, sản phẩm đạt độ mỏng tối ưu 8.15m. Trải nghiệm cầm nắm mà Lenovo Tab M8-Gen 2 đem lại rất thật tay, bạn sẽ cảm nhận được nét sang trọng của thiết bị ngay từ cái nhìn đầu tiên.";
			break;
		case 4:
			tablet[0] = "Coolpad " + i;
			tablet[1] = "21.webp";
			tablet[2] = "Coolpad Tab Tasker 10 hội tụ đủ mọi yếu tố trong mơ ở một chiếc máy tính bảng giá phải chăng. Với màn hình rộng 10 inch đẹp trung thực, dung lượng pin 6.000 mAh, hỗ trợ camera phục vụ tốt cho cả nhu cầu chụp ảnh thường ngày và gọi điện video call, sản phẩm thực sự là lựa chọn tốt cho các bạn học sinh, sinh viên và người dùng lớn tuổi.";
			break;
		case 5:
			tablet[0] = "Xiaomi " + i;
			tablet[1] = "22.webp";
			tablet[2] = "Với trọng lượng chỉ 445g và độ mỏng 7.05mm, Redmi Pad là một trong những chiếc tablet mỏng, nhẹ hàng đầu. Thiết kế dạng khung viền phẳng nguyên khối hoàn toàn bằng kim loại, cùng viền đối xứng với các đường nét thanh mảnh, tạo cho Redmi Pad dáng vẻ thanh lịch và mang đến những cảm nhận êm ái khi cầm nắm sử dụng. ";
			break;
		case 6:
			tablet[0] = "OPPO " + i;
			tablet[1] = "23.webp";
			tablet[2] = "Với thân máy siêu mỏng chỉ 6.84mm và có trọng lượng nhẹ nhàng 440 gram, OPPO Pad Air không chỉ cơ động mà còn khoe dáng vẻ sắc sảo đầy tính thời trang. Mỗi đường nét, mỗi khía cạnh trên sản phẩm đều được trau chuốt tỉ mỉ nhằm tạo nên kết cấu tổng thể đặc biệt hút mắt. Sự gọn gàng tinh tế biến OPPO Pad Air thành một trong những mẫu tablet cơ động nhất thị trường. Bạn sẽ phải ngạc nhiên về độ hoàn thiện của sản phẩm khi cầm thiết bị trên tay.";
			break;
		}
		
		return tablet;
	}
	
	public String[] phone(int i) {
		Random random = new Random();
		int num = random.nextInt(7) +1;
		String[] phone = new String[3];
		switch (num) {
		case 1:
			phone[0] = "Samsung " + i;
			phone[1] = "11.webp";
			phone[2] = "Samsung Galaxy S22 là bước nhảy vọt trong công nghệ video trên thế hệ di động. Đồng thời, điện thoại cũng mở ra loạt cải tiến đột phá hàng đầu hiện nay từ màn hình vát phẳng “nịnh” mắt đến bộ xử lý 4nm tiên tiến đầu tiên trên thế hệ smartphone Samsung.\r\n"
					+ "\r\n"
					+ "Kiệt tác của tinh thần sáng tạo\r\n"
					+ "Kiệt tác Samsung Galaxy S22 sở hữu thiết kế Contour-Cut tràn đầy tính sáng tạo, khung máy hoàn thiện liền mạch với cụm camera, giúp tổng thể cực kì hài hòa. Sự kết hợp giữa chuẩn chống nước/bụi IP68 với chất liệu Armor Aluminium cho khung máy – hợp kim nhôm cứng cáp nhất trên thị trường, Galaxy S22 có độ bền đáng kinh ngạc.";
			break;

		case 2:
			phone[0] = "Xiaomi " + i;
			phone[1] = "12.webp";
			phone[2] = "Đi kèm với màn hình lớn sẽ là viên pin dung lượng cao và tất nhiên Xiaomi Redmi 10A cũng không ngoại lệ. Với dung lượng 5000mAh, điện thoại sẽ cung cấp điện năng sử dụng lên đến hai ngày và luôn đồng hành cùng bạn trong mọi chuyến đi. Với một lần sạc đầy, Redmi 10A có thể đạt 745 giờ ở chế độ chờ hoặc 148 giờ nghe nhạc hoặc 20 giờ xem phim - tất cả được gói gọn trong chiếc điện thoại giá rẻ này và mang lại giải trí vô tận cho người dùng. ";
			break;
		case 3:
			phone[0] = "Vivo " + i;
			phone[1] = "13.webp";
			phone[2] = "Chiếc flagship vivo V25 Pro 5G đã sẵn sàng để cùng bạn khám phá thế giới, mọi thứ tạo nên một chiếc điện thoại hoàn hảo, từ thiết kế đổi màu cao cấp, camera dẫn đầu xu hướng đến hiệu năng 5G mạnh mẽ. Những trải nghiệm tuyệt vời nhất sẽ luôn bên bạn nếu có chiếc điện thoại này trên tay. ";
			break;
		case 4:
			phone[0] = "Iphone " + i;
			phone[1] = "14.webp";
			phone[2] = "iPhone 12 đã có sự đột phá về thiết kế với kiểu dáng mới vuông vắn, mạnh mẽ và sang trọng hơn. Không chỉ vậy, iPhone 12 mỏng hơn 11%, nhỏ gọn hơn 15% và nhẹ hơn 16% so với thế hệ trước iPhone 11. Bất ngờ hơn nữa là dù gọn hơn đáng kể nhưng iPhone 12 vẫn có màn hình 6,1 inch như iPhone 11 mà không hề bị cắt giảm. Phần viền màn hình thu hẹp tối đa cùng sự nỗ lực trong thiết kế đã tạo nên điều kỳ diệu ở iPhone 12.";
			break;
		case 5:
			phone[0] = "Oppo " + i;
			phone[1] = "15.webp";
			phone[2] = "OPPO Find N2 Flip thời thượng, hiện đại khi nhìn bên ngoài và nổi bật khi gập lại nhỏ gọn. Bạn có thể đặt OPPO Find N2 Flip vừa vặn trong túi quần hay túi xách mà không cảm thấy vướng vịu, khó chịu. Khi cần thiết, một màn hình lớn tiện lợi sẽ xuất hiện, tạo ra chuẩn không gian smartphone thường, để bạn thực hiện mọi hoạt động. Màu sắc còn tôn lên Find N2 Flip với sắc màu tím cuốn hút, bên cạnh sắc đen tối giản, tinh tế, sang trọng.";
			break;
		case 6:
			phone[0] = "Realme " + i;
			phone[1] = "16.webp";
			phone[2] = "Realme C55 tự hào là sản phẩm mỏng nhất trong dòng C, độ mỏng chỉ 7.89mm. Kết hợp đó là bo góc chữ C uyển chuyển, cho tổng thể tinh tế và hiện đại. Màu sắc còn là điểm nhấn với quy trình phủ hạt, kết hợp quy trình phủ kép 2 lớp tiên tiến, tạo ra hiệu ứng ba chiều cho phiên bản Vàng Nắng Mai. Đây là phiên bản dựa trên nguồn cảm hứng từ thiên nhiên, tái hiện tia nắng nhẹ nhàng, cho hiệu ứng mờ ảo, chuyển màu theo ánh sáng tuyệt đẹp trong từng môi trường. Ngoài ra, người dùng còn có lựa chọn giản đơn hơn là Đen Trời Đêm sang trọng và huyền bí.";
			break;
		case 7:
			phone[0] = "Asus " + i;
			phone[1] = "17.webp";
			phone[2] = "Là thế hệ mới nhất của dòng smartphone gaming lừng danh toàn cầu, Asus ROG Phone 6 đem lại nguồn sức mạnh ấn tượng nhờ chip Qualcomm Snapdragon 8+ Gen 1 kết hợp cùng tản nhiệt GameCool 6 đẳng cấp. Màn hình 165Hz và viên pin 6.000 mAh ấn tượng sẽ phá vỡ mọi rào cản để mang lại trải nghiệm game đẳng cấp nhất.";
			break;
		}
		
		return phone;
	}

	public String[] laptop(int i) {
		Random random = new Random();
		int num = random.nextInt(10) + 1;
		String[] laptop = new String[3];
		switch (num) {
		case 1:
			laptop[0] = "Asus " + i;
			laptop[1] = "1.webp";
			laptop[2] = "Asus TUF Gaming F15 FX506LHB-HN188W mang trên mình sức mạnh của bộ vi xử lý Intel thế hệ thứ 10. Con chip Intel Core i5 10300H với 4 lõi 8 luồng và tốc độ tối đa lên tới 4.50GHz cho hiệu năng đáng tin cậy, dù là bạn chơi game, phát trực tiếp hay làm việc. Đi kèm với đó là 512GB SSD NVMe PCIe, 8GB RAM DDR4 và khả năng nâng cấp dễ dàng, để bạn có được hiệu năng đúng như ý muốn.";
			break;

		case 2:
			laptop[0] = "MSI " + i;
			laptop[1] = "2.webp";
			laptop[2] = "Laptop MSI GF63 11SC có thiết kế hiện đại và phong cách đặc trưng của dòng laptop chơi game MSI. Phần mặt trên và kê tay bàn phím được làm bằng kim loại cứng cáp, sang trọng. MSI đã cải tiến để MSI GF63 mỏng nhẹ nhất có thể, nhưng vẫn đảm bảo được quá trình tản nhiệt hiệu quả. Máy có độ mỏng chưa đến 2,2cm và trọng lượng vỏn vẹn 1,86kg, những thông số rất tuyệt vời đối với một chiếc laptop chơi game.";
			break;
		case 3:
			laptop[0] = "Acer " + i;
			laptop[1] = "3.webp";
			laptop[2] = "Acer Nitro 5 Tiger AN515-58-52SP là một trong những chiếc laptop đầu tiên trên thị trường trang bị bộ vi xử lý Intel thế hệ thứ 12 Alder Lake mới nhất. Con chip Intel Core i5 12500H có sức mạnh khiến cả những chip Core i7 trước đây phải choáng ngợp với 12 lõi 16 luồng, tốc độ xung nhịp 3.3 – 4.5 GHz, 18 MB Intel Smart Cache. Với nhiều nhân hơn, tốc độ xung nhịp cao hơn, i5 12500H mang đến hiệu suất cực khủng để đáp ứng tốt những tựa game hiện đại hay các ứng dụng đòi hỏi cấu hình cao. Sức mạnh của chiếc laptop gaming này sẽ giúp bạn tự tin làm bất cứ công việc gì.\r\n"
					+ "\r\n"
					+ "";
			break;
		case 4:
			laptop[0] = "Gigabyte " + i;
			laptop[1] = "4.webp";
			laptop[2] = "Toàn bộ khung máy Gigabyte G5 GD được hoàn thiện bằng màu đen mờ thời thượng, mang đến vẻ đẹp hiện đại, sang trọng và mạnh mẽ. Phần viền màn hình mỏng giúp Gigabyte Gaming G5 GD gọn gàng, dễ dàng cho vào balo mang đi bất cứ đâu. Trọng lượng chỉ 2,2 kg càng khiến máy thêm phần di động. Ngoài ra Gigabyte Gaming G5 GD còn cho phép bạn thay đổi màu đèn nền bàn phím theo 15 màu cài sẵn, thể hiện phong cách độc đáo.";
			break;
		case 5:
			laptop[0] = "Microsoft (Surface) " + i;
			laptop[1] = "5.webp";
			laptop[2] = "Là một thành viên trong gia đình laptop Surface, Surface Laptop Go vẫn mang trong mình sự cao cấp, cầu kỳ và trau chuốt trong từng đường nét thiết kế và chất liệu hoàn thiện. Ấn tượng đầu tiên là ngôn ngữ thiết kế của máy, vuông vức nhưng vẫn bo tròn một cách tinh tế từ phần khung viền màn hình cho đến khung đế máy và cả các phím bấm. Khung máy và khung màn hình làm từ hợp kim nhôm siêu nhẹ, độ hoàn thiện cực tốt cho cảm giác vô cùng cao cấp cả khi nhìn lẫn khi chạm vào. Phần đế máy làm từ nhựa tổng hợp polycarbonate cũng góp phần làm cho tổng khối lượng của máy nhẹ hơn, nhưng vẫn không kém phần cao cấp về độ hoàn thiện. Với khối lượng máy chỉ 2.45 pounds (1.1kg), Microsoft Surface Laptop Go là chiếc laptop mỏng nhẹ nhất trong dòng sản phẩm Surface Laptop của Microsoft. Độ dày của máy cũng cực kỳ ấn tượng ở mức chỉ 15.69mm, kết hợp với màn hình nhỏ gọn 12.4 inch và cân nặng 1.1kg đem lại khả năng di động hoàn hảo trong mọi trường hợp.";
			break;
		case 6:
			laptop[0] = "Apple (MacBook) " + i;
			laptop[1] = "6.webp";
			laptop[2] = "So với bản MacBook Pro chạy chip Intel Core i7, mẫu MacBook Pro 14 inch M2 Max đạt hiệu suất chỉnh sửa video Final Cut Pro gấp 15,7 lần, hiệu suất chỉnh sửa ảnh qua Photoshop gấp 3 lần, render đồ họa chuyển động với Motion nhanh gấp 1,9 lần. Đặt MacBook Pro 14 M2 Max trên bàn làm việc, bạn sẽ an tâm rằng mình đang có cỗ máy đắc lực nhất cho công việc.";
			break;
		case 7:
			laptop[0] = "HP " + i;
			laptop[1] = "7.webp";
			laptop[2] = "Dù thuộc phân khúc giá rẻ, HP 15s fq2711TU vẫn được hãng chăm chút cho phần thiết kế bên ngoài bằng cách phủ một lớp nhựa có tông màu vàng nhạt lên nắp lưng cũng như thân máy. Màn hình được bo cong nhẹ ở 4 góc để tạo nên cảm giác mềm mại, nhẹ nhàng khi quan sát tổng thể. HP 15s fq2711TU mỏng 1.79cm và nặng 1.7kg, phù hợp cho cả những người thường xuyên sử dụng tại nhà cũng như liên tục di chuyển.";
			break;
		case 8:
			laptop[0] = "Lenovo " + i;
			laptop[1] = "8.webp";
			laptop[2] = "Lenovo Legion 5 15IAH7 sẽ định nghĩa lại về tiêu chuẩn laptop gaming thế hệ mới khi bên cạnh cấu hình mạnh mẽ, chiếc laptop chơi game hiện đại còn phải sở hữu màn hình độ phân giải cao và tần số quét vượt trội. Bạn sẽ được thưởng thức hình ảnh gaming siêu đẹp, siêu mượt trên tấm nền 2K 165Hz đầy khác biệt.";
			break;
		case 9:
			laptop[0] = "Dell " + i;
			laptop[1] = "9.webp";
			laptop[2] = "Dell Vostro 3510 là phiên bản laptop doanh nhân 15,6 inch mới nhất từ Dell, với thiết kế gọn gàng thanh thoát và hiện đại, đồng thời hiệu suất được nâng cấp đáng kể nhờ bộ vi xử lý Intel Core i5 thế hệ thứ 11 cùng card đồ họa rời NVIDIA MX350, giúp cho công việc của bạn luôn hoạt động hiệu quả và an toàn.";
			break;
		case 10:
			laptop[0] = "Masstel " + i;
			laptop[1] = "10.webp";
			laptop[2] = "Giải pháp học online đắc lực và tiện dụng nhất đã xuất hiện, Laptop Masstel E116 được xây dựng nhằm hỗ trợ cho quá trình giáo dục từ xa một cách tiện lợi nhất, vừa bảo vệ trẻ khỏi những thông tin độc hại trong quá trình sử dụng internet. Thông qua chiếc laptop này, phụ huynh dễ dàng kiểm soát và quản lý những thông tin tiếp cận đến trẻ để yên tâm giao máy cho con học online.";
			break;
		}
		
		return laptop;
	}

}
