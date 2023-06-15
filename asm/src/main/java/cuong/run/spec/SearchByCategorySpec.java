package cuong.run.spec;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import cuong.run.model.Product;
import cuong.run.web.request.ProductSearchRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchByCategorySpec {

	public Specification<Product> getSearchSpecification(ProductSearchRequest productSearchRequest,Integer categoryID) {
		return (root, query, criteriaBuilder) -> {
			Predicate categoryNames = criteriaBuilder.equal(root.get("category").get("id"), categoryID);
			List<Predicate> predicatePrice = new ArrayList<>();
			if(productSearchRequest.getPriceBetWeen() != null) {
				if(productSearchRequest.getPriceBetWeen().length > 0) {
					for (String price : productSearchRequest.getPriceBetWeen()) {
						predicatePrice.add(get(criteriaBuilder, root, price));
					}
				}else {
					predicatePrice.add(get(criteriaBuilder, root, "0,100000000000"));
				}
			}else {
				predicatePrice.add(get(criteriaBuilder, root, "0,100000000000"));
			}
			
			
			return criteriaBuilder
					.and(categoryNames,
					criteriaBuilder.or(predicatePrice.toArray(new Predicate[0]))
					);
		};
	}

	private Predicate get(CriteriaBuilder criteriaBuilder, Root<Product> root, String price) {
		String[] valueBETWEEN = price.split(",");
		return criteriaBuilder.between(root.get("price"), Double.valueOf(valueBETWEEN[0]), Double.valueOf(valueBETWEEN[1]));

	}
	
}
