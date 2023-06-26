package cuong.run.service;

public interface InforPageService {
	//lưu thông tin cái cần tìm kiếm
	public void setSearch(String name);
	//lấy thông tin tìm kiếm
	public String getSearch();
	
	
	//set giá trị số trang hiện tại.
	public void setPage();
	
	//lấy giá trị số trang hiện tại;
	public int getPage();
}
