package com.tierguardians.finances.repository;

import com.tierguardians.finances.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByUserId(String userId);
}