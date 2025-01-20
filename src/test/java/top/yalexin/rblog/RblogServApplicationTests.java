package top.yalexin.rblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yalexin.rblog.service.impl.SendEmailServiceImpl;
import top.yalexin.rblog.util.MD5Utils;

@SpringBootTest
class RblogServApplicationTests {

	@Autowired
	SendEmailServiceImpl sendEmailService;

	@Test
	void contextLoads() {
		sendEmailService.send(null, null, false);
	}

	@Test
	public void test(){
		System.out.println(MD5Utils.code("piygh"));
//		HashMap<String, String> randomValidateCode = RandomValidateCodeUtil.getRandomValidateCode(6);
//		System.out.println(randomValidateCode.get("code"));
//		System.out.println(randomValidateCode.get("base64img"));
	}

}
