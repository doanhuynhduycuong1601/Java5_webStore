package cuong.run.utils;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Component;
import cuong.run.model.Orders;
import cuong.run.model.OrdersItems;
import cuong.run.pojo.Cart;
import cuong.run.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailOrder {
	final ShoppingCartService cartService;

	private String table(Orders orders) {
		String table = "";
		
		for(int i = 0; i < orders.getOrderItems().size(); i++) {
			OrdersItems item = orders.getOrderItems().get(i);
			table += "<tr style=\"width: 100%\">\r\n"
					+ "			<th width=\"10%\">"+(i+1)+"</th>\r\n"
					+ "			<th width=\"40%\">"+item.getProduct().getNames()+"</th>\r\n"
					+ "			<th width=\"25%\">"+GetString.getVnd(item.getPrice())+"</th>\r\n"
					+ "			<th width=\"25%\">"+item.getQuantity()+"</th>\r\n"
					+ "		</tr>";
		}
		return table;
	}
	
	private String table(Map<Integer, Cart> items) {
		String table = "";
		int i = 1;
		for(Map.Entry<Integer, Cart> entry : items.entrySet()) {
			Cart item = entry.getValue();
			table += "<tr style=\"width: 100%\">\r\n"
					+ "			<th width=\"10%\">"+(i+1)+"</th>\r\n"
					+ "			<th width=\"40%\">"+item.getNames()+"</th>\r\n"
					+ "			<th width=\"25%\">"+GetString.getVnd(item.getPrice())+"</th>\r\n"
					+ "			<th width=\"25%\">"+item.getQuantityCart()+"</th>\r\n"
					+ "		</tr>";
			i++;
		}
		return table;
	}
	
	
	public String confirmOrder(Orders orders) {
		String html = "<div style=\"text-align: center;\"><h1>Xác nhận mua hàng</h1></div>\r\n"
				+ "	<h2>Chào bạn : "+orders.getCustomerID().getFullname()+"</h2>\r\n"
				+ "	<h2>Số điện thoại : "+orders.getPhone()+"</h2>\r\n"
				+ "	<h2>Địa chỉ : "+orders.getAddresss()+"</h2>\r\n"
				+ "	<h2>Ngày đặt : "+GetString.getDate(orders.getOrderDate())+"</h2>\r\n"
				+ "	<h2>Ngày xác nhận : "+GetString.getDate(LocalDateTime.now())+"</h2>\r\n"
				+ "	<h2>Tổng tiền : "+GetString.getVnd(orders.getTotalAmount())+"</h2>\r\n"
				+ "	<div>Bạn có đặt một đơn hàng : với tổng số tiền : "+GetString.getVnd(orders.getTotalAmount())+"</div>\r\n"
				+ "	<table style=\"width: 100%\">\r\n"
				+ "		<tr style=\"width: 100%\">\r\n"
				+ "			<th width=\"10%\">STT</th>\r\n"
				+ "			<th width=\"40%\">Tên sp</th>\r\n"
				+ "			<th width=\"25%\">Giá tiền</th>\r\n"
				+ "			<th width=\"25%\">Số lượng</th>\r\n"
				+ "		</tr>\r\n"
				+ "		\r\n"
				+ table(orders)
				+ "	</table>\r\n"
				+ "	\r\n"
				+ "	<div>Đơn hàng sẽ gửi đến bạn sớm nhất có thể. Cám ơn bạn đã mua hàng.</div>";
		return html;
	}
	
	
	public String order(Orders orders) {
		String html = "<div style=\"text-align: center;\"><h1>Hóa đơn</h1></div>\r\n"
				+ "	<h2>Chào bạn : "+orders.getCustomerID().getFullname()+"</h2>\r\n"
				+ "	<h2>Ngày : "+GetString.getDate(orders.getOrderDate())+"</h2>\r\n"
				+ "	<h2>Số điện thoại : "+orders.getPhone()+"</h2>\r\n"
				+ "	<h2>Địa chỉ : "+orders.getAddresss()+"</h2>\r\n"
				+ "	<h2>Tổng tiền : "+GetString.getVnd(orders.getTotalAmount())+"</h2>\r\n"
				+ "	<table style=\"width: 100%\">\r\n"
				+ "		<tr style=\"width: 100%\">\r\n"
				+ "			<th width=\"10%\">STT</th>\r\n"
				+ "			<th width=\"40%\">Tên sp</th>\r\n"
				+ "			<th width=\"25%\">Giá tiền</th>\r\n"
				+ "			<th width=\"25%\">Số lượng</th>\r\n"
				+ "		</tr>\r\n"
				+ "		\r\n"
				+ table(cartService.getCart())
				+ "	</table>\r\n"
				+ "	\r\n"
				+ "	<div>Cảm ơn bạn đã đặt hàng chúng t sẽ phản hồi đến bạn sớm nhất.</div>";
		return html;
	}
	
	public String orderAdmin(Orders orders) {
		String html = "<div style=\"text-align: center;\"><h1>Hóa đơn</h1></div>\r\n"
				+ "	<h2>Khách hàng : "+orders.getCustomerID().getFullname()+"</h2>\r\n"
				+ "	<h2>Ngày : "+GetString.getDate(orders.getOrderDate())+"</h2>\r\n"
				+ "	<h2>Số điện thoại : "+orders.getPhone()+"</h2>\r\n"
				+ "	<h2>Địa chỉ : "+orders.getAddresss()+"</h2>\r\n"
				+ "	<h2>Tổng tiền : "+GetString.getVnd(orders.getTotalAmount())+"</h2>\r\n"
				+ "	<table style=\"width: 100%\">\r\n"
				+ "		<tr style=\"width: 100%\">\r\n"
				+ "			<th width=\"10%\">STT</th>\r\n"
				+ "			<th width=\"40%\">Tên sp</th>\r\n"
				+ "			<th width=\"25%\">Giá tiền</th>\r\n"
				+ "			<th width=\"25%\">Số lượng</th>\r\n"
				+ "		</tr>\r\n"
				+ "		\r\n"
				+ table(cartService.getCart())
				+ "	</table>\r\n"
				+ "	\r\n"
				+ "	<div>Bạn hãy kiểm tra xem khách hàng đủ tiêu chuẩn để mua hàng hay không và xác nhận.</div>";
		return html;
	}

}
