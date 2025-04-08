package product.dto;

import product.domain.Product;

public class ProductInfoResponse {

    private final String productName;
    private final String detail;
    private final int price;

    private ProductInfoResponse(String productName, String detail, int price) {
        this.productName = productName;
        this.detail = detail;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public String getDetail() {
        return detail;
    }

    public int getPrice() {
        return price;
    }

    public static ProductInfoResponse of(Product product) {
        return new ProductInfoResponse(product.getName(), product.getDetail(), product.getSalePrice());
    }
}
