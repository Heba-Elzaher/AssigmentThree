package com.example.assigmentthree.repository;

import com.example.assigmentthree.model.TreeData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<TreeData, Long> {
}
