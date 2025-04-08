package product.dto;

import product.domain.Product;

import java.time.LocalDate;

public class ProductSaveRequest {

    private final String name;
    private final String detail;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private int customerPrice;
    private final int salePrice;
    private final int quantity;
    private int deliveryFee;

    public ProductSaveRequest(String name,
                               String detail,
                               LocalDate startDate,
                               LocalDate endDate,
                               int salePrice,
                               int quantity) {
        this.name = name;
        this.detail = detail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePrice = salePrice;
        this.quantity = quantity;
    }

    public static Product of(ProductSaveRequest request, LocalDate date) {
        return new Product(request.name,
                request.detail,
                request.startDate,
                request.endDate,
                request.salePrice,
                request.quantity,
                date
        );
    }
}
