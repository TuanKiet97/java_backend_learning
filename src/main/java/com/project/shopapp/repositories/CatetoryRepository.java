package com.project.shopapp.repositories;

import com.project.shopapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatetoryRepository extends JpaRepository<Category, Long> {

}
