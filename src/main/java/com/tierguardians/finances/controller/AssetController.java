package com.tierguardians.finances.controller;

import com.tierguardians.finances.domain.Asset;
import com.tierguardians.finances.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAssets(@RequestParam String userId) {
        List<Asset> assets = assetService.getAssetsByUserId(userId);
        return ResponseEntity.ok(assets);
    }
}
