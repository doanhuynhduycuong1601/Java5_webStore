package cuong.run.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cuong.run.model.UpdateStatus;
import cuong.run.model.Customer;
import cuong.run.model.Orders;
import cuong.run.model.OrdersItems;
import cuong.run.repository.OrderItemsRepository;
import cuong.run.repository.OrderRepository;
import cuong.run.repository.UpdateStatusRepository;
import cuong.run.repository.interfaces.UpdateStatusInterface;
import cuong.run.service.Param;
import cuong.run.service.Session;
import cuong.run.utils.BuilderOrderStatus;
import cuong.run.utils.BuilderOrderToOrderResp;
import cuong.run.web.resp.RespUpdateStatus;
import cuong.run.web.response.OrderItemsDetailResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("history")
public class HistoryController {
	final BuilderOrderToOrderResp builderOrderToOrderResp;
	final BuilderOrderStatus builderOrderStatus;
	final OrderRepository orderRepository;
	final OrderItemsRepository orderItemsRepository;
	final UpdateStatusRepository updateStatusRepository;
	final Session session;
	final Param param;

	@GetMapping
	public String view(Model model) {
		model.addAttribute("page", "include/history/history.jsp");
		return "index";
	}

	@GetMapping("/order")
	public String orderView(Model model) {
		Customer customer = session.get("account");
		model.addAttribute("history",
				builderOrderToOrderResp.toRespOrders(orderRepository.findByStatus(1, customer.getId())));
		model.addAttribute("order", "");
		return "forward:/history";
	}

	// xem hóa đơn chi tiết
	@PostMapping("/order/detail")
	@ResponseBody
	public List<OrderItemsDetailResponse> one() {
		int id = param.getInt("id", 1);
		return builderOrderToOrderResp.toRespOrderItems(orderItemsRepository.findBy(id));
	}

	// hủy đơn hàng
	@GetMapping("/cancel/{id}")
	public String orderCancel(@PathVariable("id") Integer id) {
		Orders orders = orderRepository.findById(id).get();
		orders.setStatuss(0);
		orderRepository.save(orders);
		saveCancelOrder(id);
		return "redirect:/history/order";
	}

	// trang view order bị hủy
	@GetMapping("/order/cancel")
	public String orderCancelView(Model model) {
		Customer customer = session.get("account");
		model.addAttribute("history",
				builderOrderToOrderResp.toRespOrders(orderRepository.findByStatus(0, customer.getId())));
		model.addAttribute("orderCancel", "");
		return "forward:/history";
	}

	// trang view đơn hàng đã xác nhận
	@GetMapping("/order/confirm")
	public String orderConfirmView(Model model) {
		Customer customer = session.get("account");
		model.addAttribute("history",
				builderOrderToOrderResp.toRespOrders(orderRepository.findByStatus(2, customer.getId())));
		return "forward:/history";
	}
	
	// trang view đơn hàng đã xác nhận
	@GetMapping("/order/ship")
	public String orderShipView(Model model) {
		Customer customer = session.get("account");
		model.addAttribute("history",
				builderOrderToOrderResp.toRespOrders(orderRepository.findByStatus(3, customer.getId())));
		return "forward:/history";
	}
	
	// trang view đơn hàng đã xác nhận
	@GetMapping("/order/success")
	public String orderSuccessView(Model model) {
		Customer customer = session.get("account");
		model.addAttribute("history",
			builderOrderToOrderResp.toRespOrders(orderRepository.findByStatus(4, customer.getId())));
		model.addAttribute("page", "include/history/historySuccess.jsp");
		return "index";
	}

	 //xem tình trạng đơn hàng
	@ResponseBody
	@PostMapping("/order/status")
	public List<RespUpdateStatus> status() {
		int id = param.getInt("id", 1);
		return builderOrderStatus
				.respUpdateStatus(updateStatusRepository.findBy(id, UpdateStatusInterface.class));
	}

	public void saveCancelOrder(Integer id) {
		Customer customer = session.get("account");
		UpdateStatus cancelOrder = new UpdateStatus(id, customer.getEmail());
		updateStatusRepository.save(cancelOrder);
	}
	
	
	@GetMapping("/hoan/{id}")
	public String hoan(@PathVariable("id") Integer id) {
		String reason = param.getString("reason", "");
		OrdersItems item = orderItemsRepository.findById(id).get();
		item.setITemReturn(2);
		orderItemsRepository.save(item);
		Orders orders = orderRepository.findById(item.getOrder().getOrderID()).get();
		String msg = String
				.format("Khách hàng yêu cầu hoàn trả sản phẩm %s. Lý do : %s",item.getProduct().getNames(),reason);
		orders.getUpdateStatus().add(updateStatus(orders, msg));
		orderRepository.save(orders);
		return "redirect:/history/order/success";
	}
	
	private UpdateStatus updateStatus(Orders orders,String msg) {
		Customer customer = session.get("account");
		UpdateStatus updateStatus = new UpdateStatus();
		updateStatus.setOrders(orders);
		updateStatus.setDescriptions(msg);
		updateStatus.setGmail(customer.getEmail());
		return updateStatus;
	}
}
