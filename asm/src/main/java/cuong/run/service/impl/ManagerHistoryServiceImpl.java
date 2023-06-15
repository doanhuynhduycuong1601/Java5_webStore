package cuong.run.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import cuong.run.exception.StringException;
import cuong.run.model.Customer;
import cuong.run.model.Orders;
import cuong.run.model.OrdersItems;
import cuong.run.model.ThongKeOrderItems;
import cuong.run.model.UpdateStatus;
import cuong.run.pojo.MailInfo;
import cuong.run.repository.OrderItemsRepository;
import cuong.run.repository.OrderRepository;
import cuong.run.repository.UpdateStatusRepository;
import cuong.run.service.Mail;
import cuong.run.service.ManagerHistoryService;
import cuong.run.service.Param;
import cuong.run.service.Session;
import cuong.run.utils.Bill_email;
import cuong.run.utils.BuilderOrderToOrderResp;
import cuong.run.utils.GetString;
import cuong.run.utils.MailOrder;
import cuong.run.web.response.OrderItemsDetailResponse;
import cuong.run.web.response.OrderItemsTKResponse;
import cuong.run.web.response.OrderResponse;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ManagerHistoryServiceImpl implements ManagerHistoryService {
	final OrderRepository orderRepository;
	final OrderItemsRepository orderItemsRepository;
	final UpdateStatusRepository updateStatusRepository;
	final BuilderOrderToOrderResp builderOrderToOrderResp;
	final Mail mail;
	final MailOrder mailOrder;
	final Bill_email bill_email;
	final Session session;
	final Param param;
	int page = 1;
	String monthYear = "All";
	String search = "";
	
	
	@Override
	public String orderView(Model model) {
		model.addAttribute("history", get(1));
		model.addAttribute("page", "include/managerHistory/order.jsp");
		return "index";
	}
	
	@Override
	public String orderConfirmOrderView(Model model) {
		model.addAttribute("page", "include/managerHistory/confirm.jsp");
		model.addAttribute("history", get(2));
		return "index";
	}
	
	@Override
	public String orderShipOrderView(Model model) {
		model.addAttribute("page", "include/managerHistory/ship.jsp");
		model.addAttribute("history", get(3));
		return "index";
	}
	
	@Override
	public String orderCancelOrderView(Model model) {
		model.addAttribute("MonthYear", listMonthYearOrderCancel);
		model.addAttribute("selected", this.monthYear);
		model.addAttribute("page", "include/managerHistory/cancel.jsp");
		model.addAttribute("history", getData(0));
		return "index";
	}
	
	@Override
	public String orderSuccessOrderView(Model model) {
		model.addAttribute("MonthYear", listMonthYearOrderSuccess);
		model.addAttribute("selected", this.monthYear);
		model.addAttribute("page", "include/managerHistory/success.jsp");
		model.addAttribute("history", getData(4));
		return "index";
	}
	
	@Override
	public String orderTKOrderView(Model model) {
		model.addAttribute("searchManager", this.search);
		model.addAttribute("MonthYear", listMonthYearOrderSuccess);
		model.addAttribute("selected", this.monthYear);
		model.addAttribute("page", "include/managerHistory/tk.jsp");
		model.addAttribute("history", getTK());
		model.addAttribute("money", GetString.getVnd(getTKTotal()));
		return "index";
	}
	
	@Override
	public String orderHoanOrderView(Model model) {
		model.addAttribute("page", "include/managerHistory/hoan.jsp");
		List<Orders> orders = orderItemsRepository.findByHoan();
		
		model.addAttribute("history", builderOrderToOrderResp
				.toRespOrders(orders));
		return "index";
	}
	
	
	@Override
	public String confirm(int id) throws MessagingException {
		Orders orders = orderRepository.findById(id).get();
		orders.setStatuss(2);
		String msg = "Đã xác nhận đơn hànlkg. Đơn hàng sẽ giao đến bạn sớm nhất";
		orders.getUpdateStatus().add(updateStatus(orders,msg));
		sendMail(orderRepository.save(orders));
		return "redirect:/manager/history/order";
	}
	
	@Override
	public String orderCancel(int id) {
		Orders orders = orderRepository.findById(id).get();
		orders.setStatuss(0);
		orders.getUpdateStatus().add(updateStatus(orders,param.getString("reason", "")));
		orderRepository.save(orders);
		return "redirect:/manager/history/order";
	}
	
	@Override
	public String orderShipCancel(int id) {
		Orders orders = orderRepository.findById(id).get();
		orders.setStatuss(0);
		orders.getUpdateStatus().add(updateStatus(orders,param.getString("reason", "")));
		orderRepository.save(orders);
		return "redirect:/manager/history/order/ship";
	}

	
	@Override
	public String ship(int id) {
		Orders orders = orderRepository.findById(id).get();
		orders.setStatuss(3);
		String msg = "Đơn hàng đang được giao.";
		orders.getUpdateStatus().add(updateStatus(orders,msg));
		orderRepository.save(orders);
		return "redirect:/manager/history/order/confirm";
	}
	
	@Override
	public String success(int id) {
		Orders orders = orderRepository.findById(id).get();
		orders.setStatuss(4);
		String msg = "Đơn hàng giao thành công.";
		orders.getUpdateStatus().add(updateStatus(orders,msg));
		orderRepository.save(orders);
		return "redirect:/manager/history/order/ship";
	}
	
	
	private void sendMail(Orders order) throws MessagingException {
		MailInfo mailInfor = new MailInfo(order.getCustomerID().getEmail(), "Xác nhận mua hàng thành công", mailOrder.confirmOrder(order));
		mailInfor.setAttachments(new File[] {bill_email.billEmail(order)});
		mail.queue(mailInfor);
	}
	
	
	private Page<OrderResponse> getData(int status){
		if(this.monthYear.equals("All")) {
			return get(status);
		}
		return getMonthYear(status);
	}
	
	
	private Page<OrderResponse> get(int status){
		Page<Orders> orders = orderRepository.findByStatus(status, PageRequest.of(page-1, 8));
		return builderOrderToOrderResp.toRespOrders(orders);
	}
	
	private Page<OrderResponse> getMonthYear(int status){
		String[] monthYear = this.monthYear.split("/");
		Page<Orders> orders = orderRepository.findByStatus(status,Integer.valueOf(monthYear[0]), Integer.valueOf(monthYear[1]), PageRequest.of(page-1, 8));
		return builderOrderToOrderResp.toRespOrders(orders);
	}
	
	private UpdateStatus updateStatus(Orders orders,String msg) {
		Customer customer = session.get("account");
		UpdateStatus updateStatus = new UpdateStatus();
		updateStatus.setOrders(orders);
		updateStatus.setDescriptions(msg);
		updateStatus.setGmail(customer.getEmail());
		return updateStatus;
	}
	

	
	@Override
	public void setPage() {
		this.page = param.getInt("page", 1);
	}

	@Override
	public void setSearch() {
		this.search = param.getString("searchManager", "");
	}
	
	
	@Override
	public void setMonthYear() {
		this.monthYear = param.getString("selectMonthYear", "All");
		
	}
	
	private List<String> listMonthYearOrderCancel = new ArrayList<>();
	private List<String> listMonthYearOrderSuccess = new ArrayList<>();
	
	@PostConstruct
	private void addListMonthYearsOrderCancel() {
		listMonthYearOrderCancel.add("All");
		listMonthYearOrderCancel.addAll(orderRepository.findAllMonthYear(0));
		
		listMonthYearOrderSuccess.add("All");
		listMonthYearOrderSuccess.addAll(orderRepository.findAllMonthYear(4));
	}

	@Override
	public List<String> getMonthYearsOrderCancel() {
		return listMonthYearOrderCancel;
	}

	@Override
	public String removeOneItem() {
		int id = param.getInt("id", 0);
		try {
			OrdersItems item = orderItemsRepository.findById(id).get();
			item.setITemReturn(0);
			orderItemsRepository.save(item);
			Orders orders = orderRepository.findById(item.getOrder().getOrderID()).get();
			String msg = String.format("Khách hàng yêu cầu hủy sản phẩm %s trong đơn hàng %d", 
					item.getProduct().getNames(),orders.getOrderID());
			orders.getUpdateStatus().add(updateStatus(orders, msg));
			orderRepository.save(orders);
			return "Xóa item thành công";
		}catch(Exception e) {
		}
		throw new StringException("Không tìm thấy mã item order : "+id);
	}

	@Override
	public String giamOneItem() {
		int id = param.getInt("id", 0);
		
		int quantityNew = param.getInt("quantity", 0);
		if(quantityNew <= 0 || quantityNew > 5) {
			throw new StringException("Bạn phải nhập số 1 số lớn hơn 0 và nhỏ hơn bằng 5");
		}
		
		
		try {
			OrdersItems item = orderItemsRepository.findById(id).get();
			int quantityOld = item.getQuantity();
			item.setQuantity(quantityNew);
			orderItemsRepository.save(item);
			Orders orders = orderRepository.findById(item.getOrder().getOrderID()).get();
			String msg = String
					.format("Khách hàng yêu cầu thay đổi số lượng sản phẩm %s từ %d %s %d trong đơn hàng %d",
							item.getProduct().getNames(),quantityOld,
							(quantityOld >= quantityNew ? "giảm xuống" : "lên"),
							quantityNew,orders.getOrderID());
			orders.getUpdateStatus().add(updateStatus(orders, msg));
			orderRepository.save(orders);
			return "Thay đổi số lượng sản phẩm trong item thành công";
		}catch(Exception e) {
		}
		throw new StringException("Không tìm thấy mã item order : "+id);
	}
	
	private Page<ThongKeOrderItems> getTK() {
		Pageable pageable = PageRequest.of(page-1, 8);
		Page<ThongKeOrderItems> pageTK;
		if(this.monthYear.equals("All")) {
			pageTK = orderItemsRepository.thongke("%"+this.search+"%",pageable);
		}else{
			String[] nam = this.monthYear.split("/");
			pageTK = orderItemsRepository
					.thongke("%"+this.search+"%", Integer.valueOf(nam[0]),Integer.valueOf(nam[1]), pageable);
		}
		return pageTK;
	}
	
	private double getTKTotal() {
		Double number;
		if(this.monthYear.equals("All")) {
			number =  (orderItemsRepository.thongkeTotal("%"+this.search+"%"));
		}else {
		String[] nam = this.monthYear.split("/");
		number = orderItemsRepository
					.thongkeTotal("%"+this.search+"%", Integer.valueOf(nam[0]),
							Integer.valueOf(nam[1]));
		}
		
		return number == null ? 0: number;
		
	}

	@Override
	public List<OrderItemsTKResponse> getTKOrderItemsDetail() {
		int idProduct = param.getInt("idProduct", 1);
		if(this.monthYear.equals("All")) {
			return builderOrderToOrderResp
					.toOrderItemsTKResponses(orderItemsRepository.tkOrderItemDetail(idProduct));
		}
		String[] nam = this.monthYear.split("/");
		return builderOrderToOrderResp
				.toOrderItemsTKResponses(
						orderItemsRepository.tkOrderItemDetail(idProduct,Integer.valueOf(nam[0]),
								Integer.valueOf(nam[1])));
	}

	@Override
	public List<OrderItemsDetailResponse> requestHoan() {
		int id = param.getInt("id", 1);
		return builderOrderToOrderResp.toRespOrderItems(orderItemsRepository.findByStatus(id));
	}

	@Override
	public String noHoan(int id) {
		OrdersItems item = orderItemsRepository.findById(id).get();
		item.setITemReturn(1);
		orderItemsRepository.save(item);
		Orders orders = orderRepository.findById(item.getOrder().getOrderID()).get();
		String msg = String.format("Quản lý không chấp nhận hoàn đơn hàng");
		orders.getUpdateStatus().add(updateStatus(orders, msg));
		orderRepository.save(orders);
		return "redirect:/manager/history/order/hoan";
	}
	
	@Override
	public String yesHoan(int id) {
		OrdersItems item = orderItemsRepository.findById(id).get();
		item.setITemReturn(3);
		orderItemsRepository.save(item);
		Orders orders = orderRepository.findById(item.getOrder().getOrderID()).get();
		String msg = String.format("Quản lý chấp nhận hoàn đơn hàng");
		orders.getUpdateStatus().add(updateStatus(orders, msg));
		orderRepository.save(orders);
		return "redirect:/manager/history/order/hoan";
	}


}
