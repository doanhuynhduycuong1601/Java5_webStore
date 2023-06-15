package cuong.run.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class SerCookie implements cuong.run.service.Cookie {

	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;

	@Override
	public Cookie get(String name) {
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public String getValue(String name) {
		Cookie c = get(name);
		return c == null ? "" : c.getValue();
	}

	@Override
	public Cookie add(String name, String value, int hours) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(hours);
		cookie.setPath("/");
		response.addCookie(cookie);
		return cookie;
	}

	@Override
	public void remove(String name) {
		Cookie c = get(name);
		if (c != null) {
			c.setMaxAge(0);
		}

	}

}
