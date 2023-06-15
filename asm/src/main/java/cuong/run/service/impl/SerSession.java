package cuong.run.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cuong.run.service.Session;
import jakarta.servlet.http.HttpSession;

@Service
public class SerSession implements Session {
	@Autowired
	HttpSession session;

	@Override
	public <T> T get(String name) {
		return (T) session.getAttribute(name);
	}
	
	
	@Override
	public void set(String name, Object value) {
		session.setAttribute(name, value);
	}
	
	@Override
	public void remove(String name) {
		session.removeAttribute(name);
	}


}
