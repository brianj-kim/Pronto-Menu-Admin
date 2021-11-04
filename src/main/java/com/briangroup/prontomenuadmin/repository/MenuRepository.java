package com.briangroup.prontomenuadmin.repository;

import com.briangroup.prontomenuadmin.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByCategoryId(Long categoryId);
    Optional<Menu> findByIdAndCategoryId(Long id, Long categoryId);
}

