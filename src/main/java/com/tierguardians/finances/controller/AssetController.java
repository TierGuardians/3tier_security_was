package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Asset;
import com.tierguardians.finances.dto.AssetRequestDto;
import com.tierguardians.finances.dto.AssetUpdateRequestDto;
import com.tierguardians.finances.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    // 자산 목록 조회
    @GetMapping
    public ResponseEntity<List<Asset>> getAssets(@RequestParam String userId) {
        List<Asset> assets = assetService.getAssetsByUserId(userId);
        return ResponseEntity.ok(assets);
    }

    // 자산 등록
    @PostMapping
    public ResponseEntity<Map<String, String>> addAsset(@RequestBody AssetRequestDto dto) {
        assetService.addAsset(dto);
        return ResponseEntity.status(201).body(Map.of("message", "자산 등록 완료"));
    }

    // 자산 수정
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateAsset(
            @PathVariable Long id,
            @RequestBody AssetUpdateRequestDto dto
    ) {
        assetService.updateAsset(id, dto);
        return ResponseEntity.ok(Map.of("message", "자산 수정 완료"));
    }

}
