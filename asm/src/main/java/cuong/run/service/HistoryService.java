package cuong.run.service;

import org.springframework.ui.Model;

import cuong.run.model.OrdersItems;

public interface HistoryService {
	void getOrderResponses(Model model,int status);
	
	
	OrdersItems hoan(int id);
	String updateOrder(OrdersItems item);
	
	String saveCancelOrder(Integer id);
}
