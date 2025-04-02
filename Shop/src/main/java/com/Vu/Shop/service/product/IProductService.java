
package com.Vu.Shop.service.product;

import java.util.List;
import com.Vu.Shop.model.Product;
import com.Vu.Shop.request.AddProductRequest;

public interface IProductService {
	Product addProduct(AddProductRequest request);
	Product getProductById(Long id);
	void deleteProductById(Long id);
	void updateProduct(Product product, Long productId);
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand, String name);
	Long countProductsByBrandAndName(String brand, String name);
}
