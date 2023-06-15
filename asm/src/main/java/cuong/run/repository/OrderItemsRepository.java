package cuong.run.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cuong.run.model.Orders;
import cuong.run.model.OrdersItems;
import cuong.run.model.ThongKeOrderItems;

public interface OrderItemsRepository extends JpaRepository<OrdersItems, Integer>{
	@Query("SELECT o from OrdersItems o where o.order.orderID = ?1")
	List<OrdersItems> findBy(int id);
	
	@Query("SELECT o from OrdersItems o where o.order.orderID = ?1 and o.iTemReturn =2")
	List<OrdersItems> findByStatus(int id);

	
	@Query("""
			Select new ThongKeOrderItems (o.product.id,o.product.names, o.product.img,sum(o.quantity*o.price),sum(o.quantity))
			from OrdersItems o
			where o.product.names like :names and o.order.statuss = 4 and o.iTemReturn in (1,2)
			group by o.product.id, o.product.names, o.product.img
			order by sum(o.quantity*o.price) desc
			""")
	Page<ThongKeOrderItems> thongke(String names,Pageable pageable);
	
	@Query("""
			Select new ThongKeOrderItems (o.product.id,o.product.names, o.product.img,sum(o.quantity*o.price),sum(o.quantity))
			from OrdersItems o
			where o.product.names like :names and month(o.order.orderDate) = :month and year(o.order.orderDate) = :year and o.order.statuss = 4 and o.iTemReturn in (1,2)
			group by o.product.id, o.product.names, o.product.img
			""")
	Page<ThongKeOrderItems> thongke(String names, int year, int month,Pageable pageable);

	@Query(""" 
			Select sum(o.quantity*o.price)
			from OrdersItems o
			where o.product.names like :names and o.order.statuss = 4 and o.iTemReturn in (1,2)
			group by o.order.statuss
			""")
	Double thongkeTotal(String names);
	
	@Query("""
			Select sum(o.quantity*o.price)
			from OrdersItems o
			where o.product.names like :names and month(o.order.orderDate) = :month and year(o.order.orderDate) = :year and o.order.statuss = 4 and o.iTemReturn in (1,2)
			group by o.order.statuss
			""")
	Double thongkeTotal(String names, int year, int month);
	
	

	@Query("""
			Select o from OrdersItems o
			where o.product.id = :idProduct and month(o.order.orderDate) = :month and year(o.order.orderDate) = :year and o.order.statuss = 4
			order by o.order.orderDate desc
			""")
	List<OrdersItems> tkOrderItemDetail(Integer idProduct,int year, int month);
	
	@Query("""
			Select o from OrdersItems o
			where o.product.id = :idProduct and o.order.statuss = 4
			order by o.order.orderDate desc
			""")
	List<OrdersItems> tkOrderItemDetail(Integer idProduct);
	
	@Query("SELECT o.order from OrdersItems o where o.iTemReturn = 2 order by o.order.orderDate desc")
	List<Orders> findByHoan();
}
