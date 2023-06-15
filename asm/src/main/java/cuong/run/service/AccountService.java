package cuong.run.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import cuong.run.model.Customer;
import cuong.run.pojo.ChangePass;
import cuong.run.pojo.LoginPojo;
import cuong.run.pojo.UpdateInfor;
import cuong.run.repository.CustomerRepository;
import cuong.run.service.impl.MailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	final CustomerRepository customerRepository;
	final Session session;
	final MailServiceImpl mail;
	final Param param;
	final Cookie cookie;

	public boolean register(LoginPojo loginPojo, BindingResult result, Model model) throws MessagingException {
		Optional<Customer> optional = customerRepository.findByEmailEquals(loginPojo.getEmail());

		if (optional.isPresent()) {
			result.rejectValue("email", "mail.unique", "Email đã tồn tại");
			return false;
		}

		// check register lần trước.
		LoginPojo registerLast = session.get("registerAccount");

		if (registerLast != null && !loginPojo.equals(registerLast)) {
			System.out.println("asdfsadf");
			session.remove("codeRegister");
		}

		// add register lần trước
		session.set("registerAccount", loginPojo);

		model.addAttribute("code", "");

		if (session.get("codeRegister") == null || session.get("codeRegister").equals("")) {
			String code = getRanDomCode();
			session.set("codeRegister", code);
			mail.queue(loginPojo.getEmail(), "Code register", formatCodeSendMail(code));
			result.rejectValue("email", "mail.send", "Code đã gửi đến mail của bạn.");
			return false;
		}

		String code = session.get("codeRegister");
		if (!code.equals(param.getString("code", ""))) {
			model.addAttribute("errorCode", "Code sai");
			return false;
		}

		session.remove("codeRegister");
		Customer customer = new Customer();
		customer.set(loginPojo);

		customerRepository.save(customer);
		return true;
	}

	public boolean login(LoginPojo loginPojo, BindingResult result, Model model) {
		Optional<Customer> customer = customerRepository.findByEmailEquals(loginPojo.getEmail());
		if (!customer.isPresent() || !customer.get().getPasswords().equals(loginPojo.getPasswords())) {
			result.rejectValue("email", "mail.notFound", "Email hoặc mật khẩu sai");
			result.rejectValue("passwords", "passwords.notFound", "Email hoặc mật khẩu sai");
			return false;
		}

		String remember = param.getString("remember", "");
		int hour = remember.equals("") ? 0 : 10 * 60 * 60;
		session.set("account", customer.get());
		cookie.add("account", loginPojo.getEmail(), hour);

		return true;
	}

	public String checkLoginUri(Model model) {
		String uri = session.get("security-uri");
		Customer customer = session.get("account");
		if (uri != null) {
			if (uri.equals("/store/cart")) {
				if(customer.getAddresss() == null) {
					model.addAttribute("msg", "Bạn phải nhập thông tin mới mua được hàng.");
					return "redirect:/account/update/infor";
				}
			}
			session.remove("security-uri");
			return "redirect:" + uri.substring(uri.indexOf("/") + 6);
		}
		session.remove("security-uri");
		return "redirect:/";
	}

	public boolean changepass(ChangePass change, BindingResult result, Model model) {
		Customer customer = session.get("account");
		if (!customer.getPasswords().equals(change.getPassCurrent())) {
			result.rejectValue("passCurrent", "passCurrent.incorrect", "Mật khẩu hiện tại không chính xác");
			return false;
		}

		if (!change.getPassNew().equals(change.getPassConfirm())) {
			result.rejectValue("passNew", "passNew.incorrect", "Mật khẩu mới và Xác nhận mật khẩu không giống");
			result.rejectValue("passConfirm", "passConfirm.incorrect", "Mật khẩu mới và Xác nhận mật khẩu không giống");
			return false;
		}

		customer.setPasswords(change.getPassNew());
		customerRepository.save(customer);
		session.set("account", customer);

		return true;
	}

	public void update(UpdateInfor update) {
		Customer customer = session.get("account");

		toCustomer(update, customer);

		session.set("account", customerRepository.save(customer));
	}
	
	public String checkUpdateUri() {
		String uri = session.get("security-uri");
		session.remove("security-uri");
		if (uri != null) {
			return "redirect:" + uri.substring(uri.indexOf("/") + 6);
		}
		return "redirect:/";
	}

	final String getRanDomCode() {
		StringBuilder str = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			int ranCode = random.nextInt(9);
			str.append(ranCode);
		}
		return str.toString().trim();
	}

	public void toCustomer(UpdateInfor updateInfor, Customer customer) {
		customer.setPhone(updateInfor.getPhone());
		customer.setAddresss(updateInfor.getAddress());
		customer.setGender(updateInfor.getGender());
		customer.setAge(Integer.valueOf(updateInfor.getAge()));
		customer.setFullname(updateInfor.getName());
	}

	public UpdateInfor toUpdateInfor(Customer customer) {
		String age = String.valueOf(customer.getAge());
		return UpdateInfor.builder()
				.name(customer.getFullname())
				.address(customer.getAddresss())
				.age(age.equals("null") ? "" : age)
				.email(customer.getEmail())
				.phone(customer.getPhone())
				.gender(customer.getGender()).build();
	}
	
	public String formatCodeSendMail(String code) {
		String htmlContent = "<div style=\"text-align: center;\">\r\n"
				+ "	<h3>Mã code để đăng ký của bạn là</h3>\r\n"
				+ "	<h1>"+code+"</h1>\r\n"
				+ "</div>";
		return htmlContent;
	}
}
