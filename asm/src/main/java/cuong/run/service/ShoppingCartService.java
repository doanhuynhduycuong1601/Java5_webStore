package cuong.run.service;

import java.util.Collection;
import java.util.Map;

import cuong.run.pojo.Cart;




public interface ShoppingCartService {
	/**
	 * Thêm mặt hàng vào giỏ hoặc tăng số lượng lên 1 nếu đã tồn tại
	 * 
	 * @param id là mã mặt hàng cần thêm
	 * @return mặt hàng đã được thêm vào hoặc cập nhật số lượng
	 */
	Cart add(Integer id);

	/**
	 * Xóa mặt hàng khỏi giỏ
	 * 
	 * @param id mã mặt hàng cần xóa
	 */
	void remove(Integer id);

	/**
	 * Thay đổi số lượng lên của mặt hàng trong giỏ
	 * 
	 * @param id  mã mặt hàng
	 * @param qty số lượng mới
	 * @return mặt hàng đã được thay đổi số lượng
	 */
	Cart update(Integer id, int qty);

	/**
	 * Xóa sạch các mặt hàng trong giỏ
	 */
	void clear();

	/**
	 * Lấy tất cả các mặt hàng trong giỏ
	 */
	Collection<Cart> getItems();
	
	
	int getQuantity(int key);

	/**
	 * Lấy tổng số lượng các mặt hàng trong giỏ
	 */
	int getTotalCount();
	
	
	//lấy số lượng id trong giỏ hàng
	int getCount();
	

	/**
	 * Lấy tổng số tiền tất cả các mặt hàng trong giỏ
	 */
	double getAmount();
	
	String getAmountFormat();
	
	Map<Integer, Cart> getCart();
	

}
