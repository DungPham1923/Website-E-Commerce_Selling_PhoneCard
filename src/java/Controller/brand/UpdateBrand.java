package Controller.brand;

import DAL.BrandDAO;
import Model.Brand;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Files;

@MultipartConfig
public class UpdateBrand extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        int id = Integer.parseInt(request.getParameter("brandId"));
        Part filePart = request.getPart("image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        System.out.println(name + id + fileName);
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
        
        Brand newBrand = new Brand();
        newBrand.setId(id);
        newBrand.setName(name);
        newBrand.setImage(fileName);

        BrandDAO brandDAO = new BrandDAO();
        Brand oldBrand = brandDAO.getBrandById(id);
        if (oldBrand != null) {
            String oldFile = uploadPath + File.separator + oldBrand.getImage();
            File existingFile = new File(oldFile);
            if (existingFile.exists() && !existingFile.isDirectory()) {
                try {
                    Files.delete(existingFile.toPath());
                    System.out.println("Deleted existing file: " + oldFile);
                } catch (IOException e) {
                    System.err.println("Failed to delete existing file: " + e.getMessage());
                }
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
        
        boolean isUpdated = brandDAO.updateBrand(newBrand);
        if(isUpdated){
            response.sendRedirect("displayBrands");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
