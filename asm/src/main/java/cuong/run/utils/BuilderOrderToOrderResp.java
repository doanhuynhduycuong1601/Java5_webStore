package cuong.run.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import cuong.run.model.Orders;
import cuong.run.model.OrdersItems;
import cuong.run.web.response.OrderItemsDetailResponse;
import cuong.run.web.response.OrderItemsTKResponse;
import cuong.run.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BuilderOrderToOrderResp {
	
	public List<OrderResponse> toRespOrders(List<Orders> ordres){
		return ordres
				.stream()
				.map(this::toRespOrder)
				.toList();
	}
	
	public Page<OrderResponse> toRespOrders(Page<Orders> ordres){
		return ordres.map(this::toRespOrder);
	}
	
	public OrderResponse toRespOrder(Orders order) {
		if(order == null) {
			return null;
		}
		return OrderResponse
				.builder()
				.orderId(order.getOrderID())
				.dateAt(GetString.getDate(order.getOrderDate()))
				.totalAmount(GetString.getVnd(order.getTotalAmount()))
				.address(order.getAddresss())
				.status(order.getStatuss())
				.phone(order.getPhone())
				.email(order.getCustomerID().getEmail())
				.build();
	}
	
	
	public List<OrderItemsDetailResponse> toRespOrderItems(List<OrdersItems> ordersItems){
		return ordersItems.stream()
				.map(this::toOrderItemsDetailResponse)
				.toList();
	}
	
	public OrderItemsDetailResponse toOrderItemsDetailResponse(OrdersItems ordersItems){
		Long ngay = ChronoUnit.DAYS.between(ordersItems.getOrder().getOrderDate(), LocalDateTime.now());
		return OrderItemsDetailResponse
				.builder()
				.id(ordersItems.getOrderItemsID())
				.idProduct(ordersItems.getProduct().getId())
				.img(ordersItems.getProduct().getImg())
				.price(GetString.getVnd(ordersItems.getPrice()))
				.quantity(ordersItems.getQuantity())
				.name(ordersItems.getProduct().getNames())
				.itemReturn(ordersItems.getITemReturn())
				.hoan(ngay>14?false:true)
				.build();
	}
	
	public List<OrderItemsTKResponse> toOrderItemsTKResponses(List<OrdersItems> ordersItems){
		return ordersItems.stream()
				.map(this::toOrderItemsTKResponse)
				.toList();
	}
	
	public OrderItemsTKResponse toOrderItemsTKResponse(OrdersItems ordersItems) {
		if(ordersItems == null) {
			return null;
		}
		
		return OrderItemsTKResponse
				.builder()
				.quantity(ordersItems.getQuantity())
				.price(GetString.getVnd(ordersItems.getPrice()))
				.order(toRespOrder(ordersItems.getOrder()))
				.build();
	}
	
}
