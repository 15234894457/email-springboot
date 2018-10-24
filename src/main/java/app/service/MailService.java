package app.service;
import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender ;

    
    /*
     * from，即为邮件发送者，一般设置在配置文件中

	to，邮件接收者，此参数可以为数组，同时发送多人
	
	subject，邮件主题
	
	content，邮件的主体
	
	邮件发送者 from 一般采用固定的形式写到配置文件中。
   
	一	发送失败
		
		因为各种原因，总会有邮件发送失败的情况，比如：邮件发送过于频繁、网络异常等。在出现这种情况的时候，我们一般会考虑重新重试发送邮件，会分为以下几个步骤来实现：
		
		接收到发送邮件请求，首先记录请求并且入库。
		
		调用邮件发送接口发送邮件，并且将发送结果记录入库。
		
		启动定时系统扫描时间段内，未发送成功并且重试次数小于3次的邮件，进行再次发送.
		
		重新发送邮件的时间，建议以 2 的次方间隔时间，比如：2、4、8、16 ...
		
		常见的错误返回码：
		
		421 HL:ICC 该IP同时并发连接数过大，超过了网易的限制，被临时禁止连接。
		
		451 Requested mail action not taken: too much fail authentication 登录失败次数过多，被临时禁止登录。请检查密码与帐号验证设置
		
		553 authentication is required，密码配置不正确
		
		554 DT:SPM 发送的邮件内容包含了未被许可的信息，或被系统识别为垃圾邮件。请检查是否有用户发送病毒或者垃圾邮件；
		
		550 Invalid User 请求的用户不存在
		
		554 MI:STC 发件人当天内累计邮件数量超过限制，当天不再接受该发件人的投信。
		
		如果使用一个邮箱频繁发送相同内容邮件，也会被认定为垃圾邮件，报 554 DT:SPM 错误
     * 
     * 
     * 
     * 
     */
    
    
    @Value("${spring.mail.username}")
    private String from ;
    public void sendSimpleMail(String to , String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        try {
        	mailSender.send(message);
        	System.out.println("cg");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sb"+e);
		}
        
    }

    /**
		文本邮件发送代码对比，富文本邮件发送使用 MimeMessageHelper 类。MimeMessageHelper 支持发送复杂邮件模板，支持文本、附件、HTML、图片等
     */
    public void sendHtmlMail(String to , String subject, String content) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);
        message.setFrom(from);
        try {
        	mailSender.send(message);
        	System.out.println("cg");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sb"+e);
		}
    }

    /*
     * 添加多个附件可以使用多条 helper.addAttachment(fileName, file)


     */
    public void sendAttachmentsMail(String to , String subject , String content,
                                    String filePath) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName,file);
        mailSender.send(message);
    }
	/*
	 * 添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和helper.addInline(rscId, res) 来实现
	 */
    public void sendInlinResourceMail(String to ,String subject , String content,
                                      String srcPath,String srcId) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        FileSystemResource resource = new FileSystemResource(new File(srcPath));
        helper.addInline(srcId,resource);
        mailSender.send(message);
    }
    
    public void sendHtmlMailTime(String to , String subject, String content) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        try {
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        message.setFrom(from);
        
        	mailSender.send(message);
        	System.out.println("cg");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sb"+e);
		}
    }
}
