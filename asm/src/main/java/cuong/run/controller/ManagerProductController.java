package cuong.run.controller;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import cuong.run.pojo.ManagerProductPojo;
import cuong.run.service.ManagerProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager/product")
public class ManagerProductController {
	private final ManagerProductService managerProductService;

	@GetMapping("/view")
	private String view(Model model) {
		return managerProductService.home(model);
	}

	@GetMapping("/edit/{id}")
	private String edit(@PathVariable("id") Optional<Integer> id, Model model) {
		return managerProductService.edit(model, id.orElse(0));
	}

	@PostMapping("/update")
	private String update(@Valid @ModelAttribute("product") ManagerProductPojo product, BindingResult result,
			Model model, @RequestParam("paramImg") MultipartFile file) {
		return managerProductService.update(product, result, model, file);
	}
	
	
	@GetMapping("/reset")
	public String reset(Model model,@ModelAttribute("product") ManagerProductPojo product) {
		return managerProductService.reset(model);
	}
	
	@GetMapping("/search")
	public String search() {
		managerProductService.setSearch();
		managerProductService.setPage();
		return "forward:/manager/product/view";
	}
	
	@GetMapping("/search/page")
	public String searchPage() {
		managerProductService.setPage();
		return "forward:/manager/product/view";
	}
	
	@GetMapping("/page/create")
	public String pageCreate(Model model,@ModelAttribute("product") ManagerProductPojo product) {
		return managerProductService.pageCreate(model);
	}



	@ModelAttribute("exists")
	private Map<Boolean, String> available() {
		return Map.of(true, "Còn bán", false, "Hết hàng");
	}

}
