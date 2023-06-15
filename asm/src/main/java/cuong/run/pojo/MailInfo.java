package cuong.run.pojo;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailInfo {
	String from;
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String body;
	File[] attachments;
	
	public MailInfo(String to, String subject, String body) {
		this.from = "Đoàn Huỳnh Duy Cương <doanhuynhduycuong16011601@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
		}


}
