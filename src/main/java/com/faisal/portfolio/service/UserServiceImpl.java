package com.faisal.portfolio.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{

	private JavaMailSender mailSender;

	public UserServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public boolean sendEmail(String subject, String body, String to) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			mailSender.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean processClientEmail(String name, String email, String subject1, String message1) {
		try {
			String subject = "Client Message - " + name;
			String htmlBody = buildClientMessageHtml(name, email, subject1, message1);

			// Send mail to yourself
			boolean sent = sendEmail(subject, htmlBody, "faisalmastoi341@gmail.com");

			if (sent) {
				// Send auto-reply to client
				sendAutoReply(name, email, subject1);
			}

			return sent;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public void sendAutoReply(String name, String email, String subject1) {
		try {
			String replySubject = "✅ Thank you for reaching out, " + name + "!";
			String replyBody = """
            <div style="max-width:650px;margin:25px auto;padding:25px;
                border:1px solid #e0e0e0;border-radius:12px;
                font-family:Arial,Helvetica,sans-serif;
                background-color:#f7f9fc;color:#333;">
                
                <h2 style="text-align:center;color:#0B5ED7;margin-bottom:20px;">
                    Thank You for Contacting Me
                </h2>

                <p style="font-size:16px;line-height:1.7;">
                    Hi <b>""" + name + """
                    </b>,<br><br>
                    I’ve received your message regarding <b>‘""" + subject1 + """
                    ’</b>.
                    Thank you for taking the time to reach out — I truly appreciate your interest.
                    I'll review your message and get back to you as soon as possible.
                </p>

                <hr style="margin:25px 0;border:none;border-top:1px solid #ddd;">

                <h3 style="color:#0B5ED7;margin-bottom:10px;">About Me</h3>
                <p style="line-height:1.7;font-size:15px;">
                    I’m <b>Faisal Ali</b>, a Java Backend Developer with 1.4+ years of
                    professional experience in enterprise-level backend development.
                    I specialize in building secure, scalable, and high-performance systems using
                    Java, Spring Boot, Spring Security, JWT,
                    and databases like Oracle and MySQL.
                </p>

                <p style="line-height:1.7;font-size:15px;">
                    My work focuses on problem-solving, performance optimization, and
                    security-compliant architectures for fintech and enterprise solutions.
                    I’m also experienced in Agile environments, system design,
                    testing cycles, and production support.
                </p>

                <div style="margin-top:25px;padding:15px;border-left:4px solid #0B5ED7;
                    background:#fff;border-radius:6px;">
                    <p style="margin:0;font-size:14px;line-height:1.7;">
                        If you’d like to learn more about my work or discuss potential collaborations,
                        feel free to visit my portfolio or connect with me:
                    </p>
                    <ul style="margin:10px 0 0 20px;padding:0;font-size:14px;line-height:1.6;">
                        <li>🌐 <b>Portfolio:</b> 
                            <a href="https://faisalportfolio.up.railway.app/" style="color:#0B5ED7;text-decoration:none;">
                                faisalportfolio.up.railway.app
                            </a>
                        </li>
                        <li>💼 <b>LinkedIn:</b> 
                            <a href="www.linkedin.com/in/faisalmastoi" style="color:#0B5ED7;text-decoration:none;">
                                linkedin.com/in/faisalmastoi
                            </a>
                        </li>
                        <li>📧 <b>Email:</b> 
                            <a href="mailto:faisalmastoi341@gmail.com" style="color:#0B5ED7;text-decoration:none;">
                                faisalmastoi341@gmail.com
                            </a>
                        </li>
                    </ul>
                </div>

                <p style="margin-top:30px;text-align:center;font-size:13px;color:#999;">
                    — This is an automated confirmation message —<br>
                    Thank you again for getting in touch!
                </p>
            </div>
        """;

			sendEmail(replySubject, replyBody, email);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void removeSessionMessage() {
		HttpSession sessions = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession();
		sessions.removeAttribute("message");

	}

	private String buildClientMessageHtml(String name, String email, String subject1, String message1) {
		return """
        <div style="max-width:600px;margin:20px auto;padding:20px;
            border:1px solid #e0e0e0;border-radius:10px;
            font-family:Arial,Helvetica,sans-serif;
            background-color:#f9fafb;color:#333;">

            <h2 style="text-align:center;color:#4A90E2;margin-bottom:20px;">
                New Contact Message
            </h2>

            <table style="width:100%;border-collapse:collapse;">
                <tr>
                    <td style="padding:8px 0;font-weight:bold;width:120px;">Name:</td>
                    <td style="padding:8px 0;">""" + name + """
                </td>
                </tr>
                <tr>
                    <td style="padding:8px 0;font-weight:bold;">Email:</td>
                    <td style="padding:8px 0;">""" + email + """
                </td>
                </tr>
                <tr>
                    <td style="padding:8px 0;font-weight:bold;">Subject:</td>
                    <td style="padding:8px 0;">""" + subject1 + """
                </td>
                </tr>
            </table>

            <div style="margin-top:20px;padding:15px;background:#fff;
                border-left:4px solid #4A90E2;border-radius:6px;">
                <h3 style="margin:0 0 10px 0;color:#4A90E2;">Message:</h3>
                <p style="margin:0;font-style:italic;color:#555;line-height:1.6;">
                    """ + message1 + """
                </p>
            </div>

            <p style="margin-top:25px;text-align:center;font-size:12px;color:#999;">
                — Sent from <b>Faisal Portfolio Contact Form</b> —
            </p>
        </div>
    """;
	}


}
