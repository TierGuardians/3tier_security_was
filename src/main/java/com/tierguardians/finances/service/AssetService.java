package com.tierguardians.finances.service;

import com.tierguardians.finances.domain.Asset;
import com.tierguardians.finances.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> getAssetsByUserId(String userId) {
        return assetRepository.findByUserId(userId);
    }
}
