package cuong.run.interceptor;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import cuong.run.model.Customer;
import cuong.run.service.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AccountInterceptor implements HandlerInterceptor {
	Session session;
	
	public AccountInterceptor(Session session) {
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
			error = "Please has to login.";
		}
		
		if (error.length() > 0) { // có lỗi
			response.sendRedirect("/store/account/login?error="+error);
			return false;
		}
		return true;

	}
}
