package cuong.run.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import cuong.run.model.Customer;
import cuong.run.service.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
public class CustomerInterceptor implements HandlerInterceptor {
	Session session;
	
	public CustomerInterceptor(Session session) {
		super();
		this.session = session;
	}



	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		Customer customer = session.get("account");
		String error = "";
		if (customer == null) {
			error = "Please has to login. New can buy product";
			response.sendRedirect("/store/account/login?error="+error);
		}else {
			if(customer.getAddresss() == null) {
				error = "Please has to input address. New can buy product";
				response.sendRedirect("/store/account/update/infor?error="+error);
			}
		}
		
		if (error.length() > 0) { // có lỗi
			if(uri.equals("/store/cart/buy")) {
				session.set("security-uri", "/store/cart");
			}
			return false;
		}
		return true;

	}
}
