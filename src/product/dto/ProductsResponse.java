package product.dto;

import product.domain.Product;

public class ProductsResponse {

    private final String id;
    private final String name;
    private final int salePrice;

    private ProductsResponse(String id, String name, int salePrice) {
        this.id = id;
        this.name = name;
        this.salePrice = salePrice;
    }

    public static ProductsResponse of(Product product) {
        return new ProductsResponse(product.getId(), product.getName(), product.getSalePrice());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalePrice() {
        return salePrice;
    }
}
