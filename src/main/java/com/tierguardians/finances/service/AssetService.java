package com.tierguardians.finances.service;

import com.tierguardians.finances.domain.Asset;
import com.tierguardians.finances.dto.AssetRequestDto;
import com.tierguardians.finances.dto.AssetUpdateRequestDto;
import com.tierguardians.finances.repository.AssetRepository;
import com.tierguardians.finances.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public AssetService(AssetRepository assetRepository, UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    // 자산 목록 조회
    public List<Asset> getAssetsByUserId(String userId) {
        return assetRepository.findByUserId(userId);
    }

    // 자산 등록
    public void addAsset(AssetRequestDto dto) {
        if (!userRepository.existsById(dto.getUserId())) {
            throw new IllegalArgumentException("자산 등록 실패: 존재하지 않는 사용자입니다.");
        }

        Asset asset = new Asset();
        asset.setUserId(dto.getUserId());
        asset.setName(dto.getName());
        asset.setType(dto.getType());
        asset.setAmount(dto.getAmount());

        assetRepository.save(asset);
    }

    // 자산 수정
    @Transactional
    public void updateAsset(Long id, AssetUpdateRequestDto dto) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("자산 수정 실패: 자산이 존재하지 않습니다."));

        asset.setName(dto.getName());
        asset.setType(dto.getType());
        asset.setAmount(dto.getAmount());

        assetRepository.save(asset);
    }

    // 자산 삭제
    public void deleteAsset(Long id) {
        if (!assetRepository.existsById(id)) {
            throw new IllegalArgumentException("자산 삭제 실패: 자산이 존재하지 않습니다.");
        }
        assetRepository.deleteById(id);
    }

}
