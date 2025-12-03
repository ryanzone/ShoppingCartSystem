package service;

import dao.ProductDAO;
import model.Product;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    /**
     * Get all products
     */
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    /**
     * Get products by category
     */
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.equals("All Categories")) {
            return getAllProducts();
        }
        return productDAO.getProductsByCategory(category);
    }

    /**
     * Get product by ID
     */
    public Product getProductById(String productId) {
        return productDAO.getProductById(productId);
    }

    /**
     * Check if stock is available
     */
    public boolean checkStock(String productId, int quantity) {
        Product product = productDAO.getProductById(productId);
        if (product == null) return false;
        return product.getStock() >= quantity;
    }
}