package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.response.StoreResponseDto;
import com.orcnaydn.ecommerce.dto.store.CreateStoreDto;
import com.orcnaydn.ecommerce.dto.store.UpdateStoreDto;
import com.orcnaydn.ecommerce.entity.Store;
import com.orcnaydn.ecommerce.mapper.StoreMapper;
import com.orcnaydn.ecommerce.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("stores/{id}")
    public StoreResponseDto getStoreById(@PathVariable("id") Long storeId) {
        return StoreMapper.mapToDto(storeService.getStoreById(storeId));
    }

    @GetMapping("stores")
    public PageResponseDto<StoreResponseDto> getStores(@RequestParam(name = "user", required = false) UUID userId,
                                                       @RequestParam(name = "minScore", required = false) Float minScore, @RequestParam(name = "maxScore", required = false) Float maxScore,
                                                       @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo, @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        PageResponseDto<Store> stores = storeService.getStores(userId, minScore, maxScore, pageNo, pageSize);

        return new PageResponseDto<>(stores.getContent().stream().map(StoreMapper::mapToDto).collect(Collectors.toList()),stores.getPageNo(),stores.getPageSize(),stores.getTotalElements(),stores.getTotalPages());
    }

    @PostMapping("stores")
    public StoreResponseDto createStore(@Valid @RequestBody CreateStoreDto dto) {
        return StoreMapper.mapToDto(storeService.createStore(dto));
    }

    @PutMapping("stores/{id}")
    public StoreResponseDto updateStore(@PathVariable("id") Long storeId, @Valid @RequestBody UpdateStoreDto dto) {
        return  StoreMapper.mapToDto(storeService.updateStore(storeId, dto));
    }

    @DeleteMapping("stores/{id}")
    public String deleteStore(@PathVariable("id") Long storeId) {
        return storeService.deleteStore(storeId);
    }
}