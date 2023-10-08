package com.project.shopapp.controller;

import com.project.shopapp.dtos.*;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.response.ProductListResponse;
import com.project.shopapp.response.ProductResponse;
import com.project.shopapp.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping("")
    //POST http://localhost:8088/v1/api/products
    public ResponseEntity<?> createProduct(
	    @Valid @RequestBody ProductDTO productDTO,
	    BindingResult result
    ) {
	try {
	    if (result.hasErrors()) {
		List<String> errorMessages = result.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.toList();
		return ResponseEntity.badRequest().body(errorMessages);
	    }
	    Product newProduct = productService.createProduct(productDTO);
	    return ResponseEntity.ok(newProduct);
	} catch (Exception e) {
	    return ResponseEntity.badRequest().body(e.getMessage());
	}
    }

    @PostMapping(value = "uploads/{id}",
	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //POST http://localhost:8088/v1/api/products
    public ResponseEntity<?> uploadImages(
	    @PathVariable("id") Long productId,
	    @ModelAttribute("files") List<MultipartFile> files
    ) {
	try {
	    Product existingProduct = productService.getProductById(productId);
	    files = files == null ? new ArrayList<MultipartFile>() : files;
	    if (files.size() > ProductImage.MAXIMUM_IMAGE_PER_PRODUCT) {
		return ResponseEntity.badRequest().body("Test");
	    }
	    List<ProductImage> productImages = new ArrayList<>();
	    for (MultipartFile file : files) {
		if (file.getSize() == 0) {
		    continue;
		}
		// check size and format images
		if (file.getSize() > 10 * 1024 * 1024) { // Size > 10MB
		    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
			    .body("This number of images are less equal than 5");
		}
		String contentType = file.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
		    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Test");
		}
		// Save file and update the thumbnail into the DTO table
		String filename = storeFile(file);
		// Save the object into the database
		ProductImage productImage = productService.createProductImage(
			existingProduct.getId(),
			ProductImageDTO.builder()
				.imageUrl(filename)
				.build()
		);
		productImages.add(productImage);
	    }
	    return ResponseEntity.ok().body(productImages);
	} catch (Exception e) {
	    return ResponseEntity.badRequest().body(e.getMessage());
	}
    }

    private String storeFile(MultipartFile file) throws IOException {
	if (isImageFile(file) || file.getOriginalFilename() != null) {
	    throw new IOException("Invalid image format");
	}
	String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
	String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
	java.nio.file.Path uploadDir = Paths.get("uploads");
	if (!Files.exists(uploadDir)) {
	    Files.createDirectories(uploadDir);
	}
	java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
	Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
	return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
	String contentType = file.getContentType();
	return contentType != null && contentType.startsWith("image/");
    }

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(
	    @RequestParam("page") int page,
	    @RequestParam("limit") int limit
    ) {
	PageRequest pageRequest = PageRequest.of (page, limit, Sort.by("createdAt").descending());
	Page<ProductResponse> productsPage = productService.getAllProducts(pageRequest);
	int totalPages = productsPage.getTotalPages();
	List<ProductResponse> products = productsPage.getContent();
	return ResponseEntity.ok(ProductListResponse.builder()
			.products(products)
			.totalPage(totalPages)
		.build());
    }

    //http://localhost:8088/api/v1/products/6
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(
	    @PathVariable("id") String productId
    ) {
	return ResponseEntity.ok("Product with ID: " + productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
	return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
    }
}