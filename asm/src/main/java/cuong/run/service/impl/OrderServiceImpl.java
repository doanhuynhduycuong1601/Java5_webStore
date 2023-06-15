package cuong.run.service.impl;

import org.springframework.stereotype.Service;

import cuong.run.model.Customer;
import cuong.run.model.Orders;
import cuong.run.repository.OrderRepository;
import cuong.run.service.Mail;
import cuong.run.service.OrderService;
import cuong.run.service.Session;
import cuong.run.service.ShoppingCartService;
import cuong.run.utils.BuilderCartToOrder;
import cuong.run.utils.MailOrder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
	final ShoppingCartService cartService;
	final OrderRepository orderRepository;
	final BuilderCartToOrder builderOrder;
	final MailOrder mailOrder;
	final Mail mail;
	final Session session;

	String from = "doanhuynhduycuong16011601@gmail.com";
	@Override
	public String buy() {
		if (cartService.getCart().size() <= 0) {
			return "Bạn không có sản phẩm trong giỏ hàng để mua.";
		}

		Customer customer = session.get("account");

		try {
			Orders order = orderRepository.save(builderOrder.getOrders());
			mail.queue(customer.getEmail(), "Hóa đơn mua hàng tại ...",mailOrder.order(order));
			
			mail.queue(from, "Khách hàng đặt hàng ...",mailOrder.orderAdmin(order));
			cartService.clear();
			return "Buy thành công hóa đơn đã gửi đến gmail của bạn.";
		} catch (Exception e) {
		}
		return "Mua hàng thất bại vì có sản phẩm không đủ số lượng.";
	}

}
