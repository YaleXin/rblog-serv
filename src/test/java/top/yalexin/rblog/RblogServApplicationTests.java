package top.yalexin.rblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yalexin.rblog.service.SendEmailServiceImpl;

@SpringBootTest
class RblogServApplicationTests {

	@Autowired
	SendEmailServiceImpl sendEmailService;

	@Test
	void contextLoads() {
		sendEmailService.send(null, null, false);
	}

	@Test
	public void test(){}

}
