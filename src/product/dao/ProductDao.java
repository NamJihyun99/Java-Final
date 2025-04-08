package product.dao;

import product.domain.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao {

    void save(Product product);
    List<Product> findAll();
    List<Product> findEnables();
    List<Product> findByKeyword(String keyword);
    Product findById(String id);
    Map<String, Product> findAllByIds(List<String> ids);
    void updateInfo(Product product);
    void updateQuantity(Product product);
    void delete(String id);
}
