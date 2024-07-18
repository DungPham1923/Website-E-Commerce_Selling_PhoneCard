package Controller.brand;

import Controller.authentication.BaseRequireAuthentication;
import DAL.BrandDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.Brand;
import Model.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
public class Addbrand extends BaseRequireAuthentication{

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response, AccountLogin account)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Name field cannot null!");
            return;
        }
        BrandDAO brandDAO = new BrandDAO();

        boolean isExist = brandDAO.getBrandByName(name) == null ? false : true;
        if (isExist) {
            UserDAO u = new UserDAO();
            List<Brand> brandsList = new ArrayList<Brand>();

            if (account == null) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                User user = u.getUserById(account.getUser().getID());
                if (user.getRole().getID() != 1) {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    brandsList = brandDAO.getListBrand();
                    request.setAttribute("brands", brandsList);
                    request.setAttribute("userId", user.getID());
                    request.setAttribute("errorName", "This brand name is already exist!");
                    request.getRequestDispatcher("managebrand.jsp").forward(request, response);
                }
            }
        } else {
            Part filePart = request.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String projectRootPath = getProjectRootPath();

            String uploadPath = projectRootPath + File.separator + "web" + File.separator + "images" + File.separator + "logo";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                if (!created) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create directory for uploads.");
                    return;
                }
            }

            String filePath = uploadPath + File.separator + fileName;

            try {
                filePart.write(filePath);
            } catch (IOException e) {
                System.err.println("File upload failed: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "File upload failed.");
                return;
            }

            Brand brand = new Brand();
            brand.setName(name);
            brand.setImage(fileName); // Store relative path or URL

            boolean isAdded = brandDAO.addBrand(brand);
            if (isAdded) {
                response.sendRedirect("displayBrands");
            } else {
                // Show error page or alert
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create directory for uploads.");
                return;
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin accountLogin)
            throws ServletException, IOException {
        processRequest(request, response, accountLogin);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin accountLogin)
            throws ServletException, IOException {
        processRequest(request, response, accountLogin);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to add a new brand";
    }

    private String getProjectRootPath() throws IOException {
        // Resolve the real path to the 'build' directory
        String buildPath = getServletContext().getRealPath("/");

        // Use the build path to navigate up to the project root
        File buildDir = new File(buildPath);
        return buildDir.getParentFile().getParentFile().getCanonicalPath();
    }
}
