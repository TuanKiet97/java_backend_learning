package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.InvalidParamException;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.CatetoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CatetoryRepository catetoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
	Category exitsCategory = catetoryRepository.findById(productDTO.getCategoryId())
		.orElseThrow(()-> new DataNotFoundException("Can not find the category id"+ productDTO.getCategoryId()));
	Product newProduct = Product.builder()
		.name(productDTO.getName())
		.price(productDTO.getPrice())
		.description(productDTO.getDescription())
		.thumbnail(productDTO.getThumbnail())
		.category(exitsCategory)
		.build();
	return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long productId) throws DataNotFoundException {
	return productRepository.findById(productId)
		.orElseThrow(()-> new DataNotFoundException("Can not find product with id" + productId));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
	return productRepository.findAll(pageRequest).map(product -> {
	    ProductResponse productResponse = ProductResponse.builder()
		    .name(product.getName())
		    .price(product.getPrice())
		    .thumbnail(product.getThumbnail())
		    .description(product.getDescription())
		    .categoryId(product.getId())
		    .build();
	    productResponse.setCreatedAt(product.getCreatedAt());
	    productResponse.setUpdatedAt(product.getUpdatedAt());
	    return productResponse;
	});
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException {
	Product existsProduct = getProductById(id);
	Category exitsCategory = catetoryRepository.findById(productDTO.getCategoryId())
		.orElseThrow(()-> new DataNotFoundException("Can not find the category id"+ productDTO.getCategoryId()));
	if (existsProduct != null) {
	    existsProduct.setName(productDTO.getName());
	    existsProduct.setCategory(exitsCategory);
	    existsProduct.setPrice(productDTO.getPrice());
	    existsProduct.setDescription(productDTO.getDescription());
	    existsProduct.setThumbnail(productDTO.getThumbnail());
	    return productRepository.save(existsProduct);
	}
	return null;
    }

    @Override
    public void deleteProduct(long productId) {
	Optional<Product> optionalProduct = productRepository.findById(productId);
	optionalProduct.ifPresent(productRepository::delete);

    }

    @Override
    public boolean existsByName(String name) {
	return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException {
	Product exitsProduct = productRepository.findById(productId)
		.orElseThrow(()-> new DataNotFoundException("Can not find the category id"+ productImageDTO.getProductId()));
	ProductImage newProductImage = ProductImage.builder()
		.product(exitsProduct)
		.imageUrl(productImageDTO.getImageUrl())
		.build();
	int size = productImageRepository.findByProductId(productId).size();
	if (size >= ProductImage.MAXIMUM_IMAGE_PER_PRODUCT ) {
	    throw new InvalidParamException("Size must be less equal than" + ProductImage.MAXIMUM_IMAGE_PER_PRODUCT);
	}
	return productImageRepository.save(newProductImage);
    }
}
