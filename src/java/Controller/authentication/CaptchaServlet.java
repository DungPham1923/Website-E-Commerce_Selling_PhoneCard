/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Dat
 */
public class CaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int width = 150;
        int height = 50;

        // Tạo một hình ảnh
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Vẽ nền
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Vẽ một số nhiễu
        Random r = new Random();
        for (int i = 0; i < 50; i++) {
            int x1 = r.nextInt(width);
            int y1 = r.nextInt(height);
            int x2 = r.nextInt(width);
            int y2 = r.nextInt(height);
            g2d.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
            g2d.drawLine(x1, y1, x2, y2);
        }
 
        // Sinh số CAPTCHA
        String captcha = generateCaptchaText();
        request.getSession().setAttribute("captcha", captcha); // Lỗi captcha đăng nhập cùng browser thì bị  ghi đè
        
        
        //Sửa từ đây
        //B1: Tạo mã và đẩy captcha vào mã này: 
        //request.getSession().setAttribute("123456", captcha);
        //String code = generateCaptchaText();
        //String code = "Hello World";
        //B2: Lưu mã vào code, để qua bên jsp getSesion() gọi tên của captcha ra. 
        //request.getSession().setAttribute(code, code);
        //request.getSession().setAttribute(code, captcha);
        //B3: Bên JSP
        //request.getSession().getAttibute("code");
        //request.getSession().setAttribute("code", code);

        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString(captcha, 10, 40);

        g2d.dispose();

        // Đặt các header để tránh caching
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Xuất ảnh ra response
        response.setContentType("image/png");
        ImageIO.write(bufferedImage, "png", response.getOutputStream());
    }

    private String generateCaptchaText() {
        Random random = new Random();
        StringBuilder captchaText = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            captchaText.append(digit);
        }
        return captchaText.toString();
    }
}
