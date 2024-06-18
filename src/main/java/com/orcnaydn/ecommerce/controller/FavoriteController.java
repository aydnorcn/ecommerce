package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.entity.Favorite;
import com.orcnaydn.ecommerce.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("favorites/{id}")
    public Favorite getFavoriteById(@PathVariable("id") Long favoriteId) {
        return favoriteService.getFavoriteById(favoriteId);
    }

    @GetMapping("users/{id}/favorites")
    public List<Favorite> getUserFavorites(@PathVariable("id") UUID userId) {
        return favoriteService.getUserFavorites(userId);
    }

    @PostMapping("products/{id}/favorites")
    public Favorite createFavorite(@PathVariable("id") UUID productId) {
        return favoriteService.createFavorite(productId);
    }

    @DeleteMapping("favorites/{id}")
    public String deleteFavoriteById(@PathVariable("id") Long favoriteId) {
        return favoriteService.removeFavoriteById(favoriteId);
    }

    @DeleteMapping("products/{id}/favorites")
    public String deleteFavoriteByProductId(@PathVariable("id") UUID productId) {
        return favoriteService.removeFavoriteByProductId(productId);
    }
}