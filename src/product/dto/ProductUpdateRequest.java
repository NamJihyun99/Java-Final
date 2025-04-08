package product.dto;

import java.time.LocalDate;

public class ProductUpdateRequest {

    private final String name;
    private final String detail;
    private final int salePrice;

    public ProductUpdateRequest(String name, String detail, int salePrice) {
        this.name = name;
        this.detail = detail;
        this.salePrice = salePrice;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getSalePrice() {
        return salePrice;
    }
}
