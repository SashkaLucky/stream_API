package itHillel.product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Product {
    private String type;
    private double price;
    private boolean discountAvailable;
    private LocalDate dateAdded;

    public Product(String type, double price, boolean discountAvailable, LocalDate dateAdded) {
        this.type = type;
        this.price = price;
        this.discountAvailable = discountAvailable;
        this.dateAdded = dateAdded;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isDiscountAvailable() {
        return discountAvailable;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public static List<Product> getProductsByCategoryAndPrice(List<Product> products, String category, double price) {
        List<Product> filteredProducts = products.stream()
                .filter(p -> p.getType().equalsIgnoreCase(category) && p.getPrice() > price)
                .collect(Collectors.toList());

        if (filteredProducts.isEmpty()) {
            throw new IllegalArgumentException("Продукт [категорія: " + category + "] не знайдено");
        }

        return filteredProducts;
    }

    public static List<Product> getDiscountedProductsByCategory(List<Product> products, String category) {
        List<Product> filteredProducts = products.stream()
                .filter(p -> p.getType().equalsIgnoreCase(category) && p.isDiscountAvailable())
                .collect(Collectors.toList());

        List<Product> discountedProducts = new ArrayList<>();
        for (Product product : filteredProducts) {
            double discountedPrice = product.getPrice() * 0.9;
            discountedProducts.add(new Product(product.getType(), discountedPrice, false, product.getDateAdded()));
        }

        return discountedProducts;
    }

    public static Product getCheapestProductByCategory(List<Product> products, String category) {
        List<Product> filteredProducts = products.stream()
                .filter(p -> p.getType().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        if (filteredProducts.isEmpty()) {
            throw new IllegalArgumentException("Продукт [категорія: " + category + "] не знайдено");
        }

        return filteredProducts.stream()
                .min(Comparator.comparingDouble(Product::getPrice))
                .orElseThrow();
    }

    public static List<Product> getLastNProducts(List<Product> products, int n) {
        int startIndex = Math.max(0, products.size() - n);
        return products.subList(startIndex, products.size());
    }

    public static double getTotalCostOfBooksAddedThisYear(List<Product> products) {
        return products.stream()
                .filter(p -> p.getType().equalsIgnoreCase("book"))
                .filter(p -> p.getPrice() <= 75)
                .filter(p -> p.getDateAdded().getYear() == LocalDate.now().getYear())
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public static Map<String, List<Product>> groupProductsByType(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getType));
    }
}
