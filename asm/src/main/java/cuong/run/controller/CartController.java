package cuong.run.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cuong.run.service.OrderService;
import cuong.run.service.Session;
import cuong.run.service.ShoppingCartService;
import cuong.run.service.impl.SerParam;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("cart")
public class CartController {
	final ShoppingCartService cartService;
	final SerParam param;
	final OrderService orderServicel;
	final Session session;
	@GetMapping
	public String view(Model model) {
		model.addAttribute("page", "include/cart.jsp");
		model.addAttribute("cart", cartService.getItems());
		model.addAttribute("count", cartService.getTotalCount());
		model.addAttribute("amount", cartService.getAmountFormat());

		session.remove("security-uri");
		return "index";
	}

	@PostMapping("/update/{id}")
	public String update(@PathVariable("id") int id) {
		int quantity = param.getInt("qty", 0);
		if (quantity >= 0 && quantity <= 5) {
			cartService.update(id, quantity);
		}
		return "redirect:/cart";
	}

	@GetMapping("/remove/{id}")
	public String remove(@PathVariable("id") int id) {
		cartService.remove(id);
		return "redirect:/cart";
	}

	@GetMapping("/clear")
	public String clear() {
		cartService.clear();
		return "redirect:/cart";
	}

	@GetMapping("/buy")
	public String buy(Model model) {
		model.addAttribute("msg", orderServicel.buy());
		return "forward:/cart";
	}

}
