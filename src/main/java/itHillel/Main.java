package itHillel;

import itHillel.product.Product;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("book", 50.0, true, LocalDate.now()));
        products.add(new Product("book", 100.0, false, LocalDate.now().minusMonths(1)));
        products.add(new Product("music", 10.0, false, LocalDate.now()));

        String category = "book";
        double price = 60.0;

        try {
            List<Product> filteredProducts = Product.getProductsByCategoryAndPrice(products, category, price);
            System.out.println("Продукти з категорії " + category + " та ціною більшою за " + price);
            for (Product product : filteredProducts) {
                System.out.println(product.getType() + " " + product.getPrice() + " " + product.getDateAdded());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}