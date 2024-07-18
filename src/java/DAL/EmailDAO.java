
package DAL;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailDAO {
    static final String from = "noreply.ddhst@gmail.com";
    static final String password = "rdaz urgp prqi llvq";
    
    public static boolean sendEmail(String to, String tieuDe, String noiDung) {
        // Properties : khai báo các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(from, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(tieuDe, "UTF-8");
            msg.setSentDate(new Date());
            msg.setContent(noiDung, "text/HTML; charset=UTF-8");
            Transport.send(msg);
            System.out.println("Gửi email thành công");
            return true;
        } catch (Exception e) {
            System.out.println("Gặp lỗi trong quá trình gửi email");
            e.printStackTrace();
            return false;
        }
    }

    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10)); // sinh số ngẫu nhiên từ 0 đến 9
        }
        return sb.toString();

    }

    public static void main(String[] args) {
//        UserDAO dao = new UserDAO();
//        User o = dao.getUserbyEmail("DungPAHE173131@fpt.edu.vn");
//        
//        System.out.println(o);
//        
        String code = EmailDAO.generateRandomString();
        EmailDAO.sendEmail("binhnthe181882@fpt.edu.vn", "Sơn đẹp trai đã gửi cho em zai" + "", "<b>" + code + "</b>");

        
    }
}
    
