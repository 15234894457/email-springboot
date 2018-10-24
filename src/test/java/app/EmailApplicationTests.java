package app;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import app.service.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailApplicationTests {

	@Resource
	MailService mailService ;

	@Resource
	TemplateEngine templateEngine ;
	@Test
	public void contextLoads() {
	}

	@Test
	public void sendSimpleMailTest(){
		mailService.sendSimpleMail("839433015@qq.com","这是第一封邮件","这是邮件内容");
	}

	@Test
	public void sendHtmlMail() throws Exception{

		mailService.sendHtmlMail("839433015@qq.com","这是第一封邮件","<h1>这是邮件内容</h1>");
	}

	@Test
	public void sendAttachmentsMail() throws Exception{

		String filePath = "E:\\《北京市房屋租赁合同（自行成交版）》.doc";
		mailService.sendAttachmentsMail("839433015@qq.com","这是带附件的邮件","<h1>这是带附件的邮件</h1>",filePath);
	}

	@Test
	public void sendInlineMail() throws Exception{

		String filePath = "E:\\20180730172448.jpg";
		String  content = "<html><body>图片:<img src=\'cid:001\'/></body></html>";
		mailService.sendInlinResourceMail("839433015@qq.com","这是带图片的邮件",content,filePath,"001");
	}

	@Test
	public void sendTemplateMail()throws Exception{
		Context context = new Context();
		context.setVariable("id","006");
		String emailContent = templateEngine.process("emailTemplate",context);
		mailService.sendHtmlMail("839433015@qq.com","这是第一封模板邮件",emailContent);
	}
	
	@Test
	public void sendHtmlMailTime() throws Exception{
		
		String  content = "<html>\n"
				+ "<body>\n"
				+ "<h1>奉天承运、皇帝诏曰	</h1>\n"
				+ "</body>\n"
				+ "</html>";

		mailService.sendHtmlMailTime("839433015@qq.com","圣旨到，冯林强接旨",content);
	}
}
