package product.service;

import product.dao.ProductDao;
import product.domain.Product;
import product.dto.*;

import java.time.LocalDate;
import java.util.List;

public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void save(ProductSaveRequest request, LocalDate now) {
        productDao.save(ProductSaveRequest.of(request, now));
    }

    public List<ProductsResponse> getProducts() {
        return productDao.findEnables().stream().map(ProductsResponse::of).toList();
    }

    public List<ProductsResponse> getProductsByKeyword(String keyword) {
        return productDao.findByKeyword(keyword).stream().map(ProductsResponse::of).toList();
    }

    public List<ProductsAdminResponse> getProductsForAdmin() {
        return productDao.findAll().stream().map(ProductsAdminResponse::of).toList();
    }

    public ProductInfoResponse getProduct(String productId) {
        return ProductInfoResponse.of(productDao.findById(productId));
    }

    public void update(String id, ProductUpdateRequest request) {
        Product product = productDao.findById(id);
        product.updateName(request.getName());
        product.updateDetail(request.getDetail());
        product.updateSalePrice(request.getSalePrice());
        productDao.updateInfo(product);
    }

    public void update(String id, int quantity) {
        Product product = productDao.findById(id);
        product.updateQuantity(quantity);
        productDao.updateQuantity(product);
    }

    public void delete(String id) {
        productDao.findById(id);
        productDao.delete(id);
    }
}
