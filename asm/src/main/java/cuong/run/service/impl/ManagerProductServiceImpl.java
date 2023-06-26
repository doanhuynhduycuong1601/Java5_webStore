package cuong.run.service.impl;

import java.text.DecimalFormat;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import cuong.run.model.Product;
import cuong.run.pojo.ManagerProductPojo;
import cuong.run.repository.ProductRepository;
import cuong.run.repository.projection.ProductProjection;
import cuong.run.service.ManagerProductService;
import cuong.run.service.Param;
import cuong.run.service.Session;
import cuong.run.utils.BuilderProduct;
import cuong.run.web.response.ProductResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerProductServiceImpl implements ManagerProductService {

	private final Param param;
	private final Session session;
	private final BuilderProduct builderProduct;
	private final ProductRepository productRepository;
	
	DecimalFormat decimalFormat = new DecimalFormat("0");
	private int page = 1;
	private String search = "";
	@Override
	public void setPage() {
		this.page = param.getInt("page", 1);
		
	}

	@Override
	public void setSearch() {
		this.search = param.getString("searchManagerProduct", "");
		
	}

	@Override
	public String home(Model model) {
		model.addAttribute("searchManagerProduct", this.search);
		model.addAttribute("managerProduct",this.get());
		model.addAttribute("page", "include/managerProductView.jsp");
		return "index";
	}

	
	@Override
	public String edit(Model model , int id) {
		ManagerProductPojo product = toManagerProductPojo(productRepository.findById(id).orElse(null));
		
		model.addAttribute("product", product);
		session.set("managerProductEdit", product);

		model.addAttribute("page", "include/managerProduct.jsp");
		return "index";
	}
	
	@Override
	public String reset(Model model) {
		model.addAttribute("page", "include/managerProduct.jsp");
		reset();
		return "index";
	}
	
	@Override
	public String pageCreate(Model model) {
		model.addAttribute("page", "include/managerProduct.jsp");
		reset();
		return "index";
	}
	
	
	private Page<ProductResponse> get() {
		return builderProduct
				.toProductResponses(productRepository.findBy("%"+this.search+"%",
						PageRequest.of(this.page - 1, 8),ProductProjection.class));
	}
	
	
	
	ManagerProductPojo toManagerProductPojo(Product product) {
		if(product == null) {
			return new ManagerProductPojo();
		}
		return ManagerProductPojo.builder().id(product.getId()).names(product.getNames())
				.quantity(String.valueOf(product.getQuantity())).dateAt(product.getDateAtt()).img(product.getImg())
				.price(decimalFormat.format(product.getPrice())).descript(product.getDescript()).existss(product.getExistss())
				.category(product.getCategory()).build();
	}
	
	public void reset() {
		session.remove("managerProductEdit");
		session.remove("managerProductImg");
	}
	
	
	@Caching(
		evict = {@CacheEvict(allEntries = true, cacheNames = {"productDate","productPrice","productSearch"}),
				@CacheEvict(cacheNames = "product", key = "#product.id")}
	)
	@Override
	public String update(ManagerProductPojo product, BindingResult result, Model model, MultipartFile file) {
		ManagerProductPojo ao = session.get("managerProductEdit");
		String img = param.saves(file, "img/products");
		if (img.equals("")) {
			if (ao != null) {
				product.setImg(ao.getImg());
			}
			String imgSession = session.get("managerProductImg");
			if (imgSession != null) {
				product.setImg(imgSession);
			}
			
			if(product.getImg()== null) {
				model.addAttribute("managerProductImgGet", "Bạn chưa chọn ảnh");
			}
			
		} else {
			product.setImg(img);
			session.set("managerProductImg", img);
		}

		if (result.hasErrors() || product.getImg() == null) {
			model.addAttribute("page", "include/managerProduct.jsp");
			return "index";
		}
		
		if(product.getId() == null || product.getId() == 0) {
			Product p = new Product();
			p.set(product);
			productRepository.save(p);
		}else {
			Product p = productRepository.findById(product.getId()).get();
			p.set(product);
			productRepository.save(p);
		}
		reset();
		
		return "redirect:/manager/product/view";
	}
}
