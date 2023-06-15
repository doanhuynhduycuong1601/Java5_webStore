package cuong.run.service;


public interface Cookie {
	/**
	 * Đọc cookie từ request
	 * 
	 * @param name tên cookie cần đọc
	 * @return đối tượng cookie đọc được hoặc null nếu không tồn tại
	 */
	jakarta.servlet.http.Cookie get(String name);

	/**
	 * Đọc giá trị của cookie từ request
	 * 
	 * @param name tên cookie cần đọc
	 * @return chuỗi giá trị đọc được hoặc rỗng nếu không tồn tại
	 */
	String getValue(String name);

	/**
	 * Tạo và gửi cookie về client
	 * 
	 * @param name  tên cookie
	 * @param value giá trị cookie
	 * @param hours thời hạn (giờ)
	 * @return đối tượng cookie đã tạo
	 */
	jakarta.servlet.http.Cookie add(String name, String value, int hours);

	/**
	* Xóa cookie khỏi client
	* @param name tên cookie cần xóa
	*/
	void remove(String name);

}
