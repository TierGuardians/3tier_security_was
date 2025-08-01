package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Asset;
import com.tierguardians.finances.dto.ApiResponse;
import com.tierguardians.finances.dto.AssetRequestDto;
import com.tierguardians.finances.dto.AssetUpdateRequestDto;
import com.tierguardians.finances.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    // 자산 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<Asset>>> getAssets(Authentication authentication) {
        String userId = authentication.getName(); // JWT 토큰에서 추출된 userId
        List<Asset> assets = assetService.getAssetsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("자산 목록 조회 성공", assets));
    }

    // 자산 등록
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addAsset(@RequestBody AssetRequestDto dto, Authentication authentication) {
        String userId = authentication.getName();
        assetService.addAsset(dto, userId);
        return ResponseEntity.status(201).body(ApiResponse.success("자산 등록 완료"));
    }

    // 자산 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateAsset(@PathVariable Long id, @RequestBody AssetUpdateRequestDto dto, Authentication authentication) {
        String userId = authentication.getName();
        assetService.updateAsset(id, dto, userId);
        return ResponseEntity.ok(ApiResponse.success("자산 수정 완료"));
    }

    // 자산 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAsset(@PathVariable Long id, Authentication authentication) {
        String userId = authentication.getName();
        assetService.deleteAsset(id, userId);
        return ResponseEntity.ok(ApiResponse.success("자산 삭제 완료"));
    }
}
