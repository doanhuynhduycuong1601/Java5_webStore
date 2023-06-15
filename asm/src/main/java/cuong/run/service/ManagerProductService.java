package cuong.run.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import cuong.run.pojo.ManagerProductPojo;

public interface ManagerProductService {
	public void setPage();
	
	public void setSearch();
	
	public String home(Model model);
	
	public String edit(Model model, int id);
	
	
	public String update(ManagerProductPojo product, BindingResult result,
			Model model, MultipartFile file);
	
	public String reset(Model model);
	
	//trang create
	public String pageCreate(Model model);
}
