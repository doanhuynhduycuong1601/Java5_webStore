package cuong.run.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;


public interface Param {

	/**
	 * Đọc chuỗi giá trị của tham số
	 * 
	 * @param name         tên tham số
	 * @param defaultValue giá trị mặc định
	 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
	 */
	public String getString(String name, String defaultValue);
	
	
	/**
	 * Đọc mảng giá trị của tham số
	 * 
	 * @param name         tên tham số
	 * @param regex mảng giá trị cần cắt
	 * @return mảng mới hoặc null nếu không tồn tại
	 */
	public String[] getStrings(String name, String regex);
	

	/**
	 * Đọc số nguyên giá trị của tham số
	 * 
	 * @param name         tên tham số
	 * @param defaultValue giá trị mặc định
	 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
	 */
	public int getInt(String name, int defaultValue);

	/**
	 * Đọc số thực giá trị của tham số
	 * 
	 * @param name         tên tham số
	 * @param defaultValue giá trị mặc định
	 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
	 */
	public double getDouble(String name, double defaultValue);

	public float getFloat(String name, Float defaultValue);

	/**
	 * Đọc giá trị boolean của tham số
	 * 
	 * @param name         tên tham số
	 * @param defaultValue giá trị mặc định
	 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
	 */
	public boolean getBoolean(String name, boolean defaultValue);

	/**
	 * Đọc giá trị thời gian của tham số
	 * 
	 * @param name    tên tham số
	 * @param pattern là định dạng thời gian
	 * @return giá trị tham số hoặc null nếu không tồn tại
	 * @throws RuntimeException lỗi sai định dạng
	 */
	public Date getDate(String name, String pattern);

	/**
	 * Lưu file upload vào thư mục
	 * 
	 * @param file chứa file upload từ client
	 * @param path đường dẫn tính từ webroot
	 * @return đối tượng chứa file đã lưu hoặc null nếu không có file upload
	 * @throws RuntimeException lỗi lưu file
	 */
	public File save(MultipartFile file, String path);
	
	public String saves(MultipartFile file, String path);
	
	public File save(String name, String path);
	
	public String saves(String name, String path);
	
	/**
	 * Lưu file upload vào thư mục
	 * 
	 * @param file[] chứa file[] upload từ client
	 * @param path đường dẫn tính từ webroot
	 * @return đối tượng chứa string[] đã lưu hoặc null nếu không có file upload
	 * @throws RuntimeException lỗi lưu file
	 */
	public String[] save(MultipartFile[] file, String path) throws IllegalStateException, IOException;
	public String[] saves(MultipartFile[] file, String path);
	
	
	public String save(File f);
	
	public File dir(String path);

}
