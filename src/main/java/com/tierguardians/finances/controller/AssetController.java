package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Asset;
import com.tierguardians.finances.dto.AssetRequestDto;
import com.tierguardians.finances.service.AssetService;
import org.springframework.http.ResponseEntity;
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

}
