package cuong.run.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cuong.run.service.CategoryService;
import cuong.run.service.Session;
import cuong.run.service.ShoppingCartService;
import cuong.run.service.impl.ProductSearchServiceImpl;
import cuong.run.utils.SortProduct;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("*")
@RequiredArgsConstructor
public class AllController {
	final CategoryService categoryService;
	final ShoppingCartService shoppingCartService;
	final Session session;
	final ProductSearchServiceImpl productSearchService;
	
	@GetMapping
	private String test(Model model) {
		int page = 0;
		
		model.addAttribute("newProducts", productSearchService.home(page, SortProduct.sortDate()));
		
		model.addAttribute("priceProducts", productSearchService.home(page, SortProduct.sortPrice()));
		model.addAttribute("myCart", shoppingCartService.getCount());
		model.addAttribute("page", "include/home.jsp");
		return "index";
	}
	
	
//	
	@ModelAttribute("category")
	public void category(){
		session.set("category", categoryService.getAll());
	}
	
	
}
