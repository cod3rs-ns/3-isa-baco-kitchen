package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MenuItemRepository extends JpaRepository <MenuItem, Integer> {
}
