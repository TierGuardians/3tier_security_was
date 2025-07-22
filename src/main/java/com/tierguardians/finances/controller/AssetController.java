package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Asset;
import com.tierguardians.finances.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<Asset>>> getAssets(@RequestParam String userId) {
        List<Asset> assets = assetService.getAssetsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.<List<Asset>>builder()
                .success(true)
                .code(200)
                .message("자산 목록 조회 성공")
                .data(assets)
                .build());
    }

    // 자산 등록
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addAsset(@RequestBody AssetRequestDto dto) {
        assetService.addAsset(dto);
        return ResponseEntity.status(201).body(ApiResponse.<Void>builder()
                .success(true)
                .code(201)
                .message("자산 등록 완료")
                .data(null)
                .build());
    }

    // 자산 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateAsset(@PathVariable Long id, @RequestBody AssetUpdateRequestDto dto) {
        assetService.updateAsset(id, dto);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .code(200)
                .message("자산 수정 완료")
                .data(null)
                .build());
    }

    // 자산 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .code(200)
                .message("자산 삭제 완료")
                .data(null)
                .build());
    }

}
