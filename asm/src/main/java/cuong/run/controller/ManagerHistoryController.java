package cuong.run.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cuong.run.service.ManagerHistoryService;
import cuong.run.web.response.OrderItemsDetailResponse;
import cuong.run.web.response.OrderItemsTKResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager/history")
public class ManagerHistoryController {

	final ManagerHistoryService managerHistoryService;

	@GetMapping("/order")
	public String orderView(Model model) {
		managerHistoryService.setPage();
		return managerHistoryService.orderView(model);
	}

	// xác nhận đơn hàng
	@GetMapping("/confirm/{id}")
	public String orderConfirm(@PathVariable("id") Integer id) throws MessagingException {
		return managerHistoryService.confirm(id);
	}

	// hủy đơn hàng
	@GetMapping("/cancel/{id}")
	public String orderCancel(@PathVariable("id") Integer id) {
		return managerHistoryService.orderCancel(id);
	}

	// hủy đơn hàng
	@GetMapping("/ship/cancel/{id}")
	public String orderShipCancel(@PathVariable("id") Integer id) {
		return managerHistoryService.orderShipCancel(id);
	}

	// người nhận trách nhiệm giao hàng
	@GetMapping("/ship/{id}")
	public String orderShip(@PathVariable("id") Integer id) {
		return managerHistoryService.ship(id);
	}

	// người nhận trách nhiệm giao hàng
	@GetMapping("/success/{id}")
	public String orderSuccess(@PathVariable("id") Integer id) {
		return managerHistoryService.success(id);
	}

	// trang view đơn hàng đã xác nhận
	@GetMapping("/order/confirm")
	public String orderConfirmView(Model model) {
		managerHistoryService.setPage();
		return managerHistoryService.orderConfirmOrderView(model);
	}

	// trang view đơn hàng đang giao
	@GetMapping("/order/ship")
	public String orderShipView(Model model) {
		managerHistoryService.setPage();
		return managerHistoryService.orderShipOrderView(model);
	}

	// trang view order bị hủy
	@GetMapping("/order/cancel")
	public String orderCancel(Model model) {
		managerHistoryService.setPage();
		managerHistoryService.setMonthYear();
		return "forward:/manager/history/order/cancel/view";
	}

	// phân trang view order bị hủy
	@GetMapping("/order/cancel/page")
	public String orderCancel() {
		managerHistoryService.setPage();
		return "forward:/manager/history/order/cancel/view";
	}

	// trang view order bị hủy
	@GetMapping("/order/cancel/view")
	public String orderCancelView(Model model) {
		return managerHistoryService.orderCancelOrderView(model);
	}

	// trang view order giao thành công
	@GetMapping("/order/success")
	public String orderSuccess(Model model) {
		managerHistoryService.setPage();
		managerHistoryService.setMonthYear();
		return "forward:/manager/history/order/success/view";
	}

	// phân trang view order giao thành công
	@GetMapping("/order/success/page")
	public String orderSuccess() {
		managerHistoryService.setPage();
		return "forward:/manager/history/order/success/view";
	}

	// trang view order giao thành công
	@GetMapping("/order/success/view")
	public String orderSuccessView(Model model) {
		return managerHistoryService.orderSuccessOrderView(model);
	}

	// trang view order tk
	@GetMapping("/order/tk")
	public String tk() {
		managerHistoryService.setPage();
		managerHistoryService.setMonthYear();
		managerHistoryService.setSearch();
		return "forward:/manager/history/order/tk/view";
	}

	// phân trang view order tk
	@GetMapping("/order/tk/page")
	public String tkPage() {
		managerHistoryService.setPage();
		return "forward:/manager/history/order/tk/view";
	}

	@GetMapping("/order/tk/view")
	public String viewOrderTk(Model model) {
		return managerHistoryService.orderTKOrderView(model);
	}

	@ResponseBody
	@PostMapping("/order/tk/detail")
	public List<OrderItemsTKResponse> tkOrderItemsDetail() {
		return managerHistoryService.getTKOrderItemsDetail();
	}

	// trang view order giao thành công
	@GetMapping("/order/hoan")
	public String orderHoan(Model model) {
		return managerHistoryService.orderHoanOrderView(model);
	}

	@ResponseBody
	@PostMapping("/remove/one/item")
	public String removeOneItem(@RequestParam("idOrder") int idOrder) {
		return managerHistoryService.removeOneItem(idOrder);
	}

	@ResponseBody
	@PostMapping("/giam/one/item")
	public String giamOneItem(@RequestParam("idOrder") int idOrder) {
		return managerHistoryService.giamOneItem(idOrder);
	}
	
	@ResponseBody
	@PostMapping("/hoan/order/detail")
	public List<OrderItemsDetailResponse>  viewOrderRequestHoan() {
		return managerHistoryService.requestHoan();
	}
	
	
	@GetMapping("/hoan/no/{id}/{idOrder}")
	public String noHoan(@PathVariable("id") Integer id,@PathVariable("idOrder") Integer idOrder) {
		return managerHistoryService.noHoan(id, idOrder);
	}
	
	@GetMapping("/hoan/yes/{id}/{idOrder}")
	public String yesHoan(@PathVariable("id") Integer id,@PathVariable("idOrder") Integer idOrder) {
		return managerHistoryService.yesHoan(id, idOrder);
	}

}
