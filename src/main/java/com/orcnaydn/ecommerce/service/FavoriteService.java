package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.entity.Favorite;
import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.exception.AlreadyExistsException;
import com.orcnaydn.ecommerce.exception.NoAuthorityException;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final ProductService productService;

    public Favorite getFavoriteById(Long favoriteId) {
        return favoriteRepository.findById(favoriteId).orElseThrow(() -> new ResourceNotFoundException("Favorite not found!"));
    }

    public List<Favorite> getUserFavorites(UUID userId) {
        return favoriteRepository.findAllByUser(userService.getUserById(userId));
    }

    public Favorite createFavorite(UUID productId) {
        User user = userService.getCurrentAuthenticatedUser();
        Product product = productService.getProductById(productId);

        if (favoriteRepository.existsByUserAndProduct(user, product))
            throw new AlreadyExistsException("This product already list on favorites!");

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        return favoriteRepository.save(favorite);
    }

    public String removeFavoriteById(Long favoriteId) {
        Favorite favorite = getFavoriteById(favoriteId);

        if (!hasUserAuthority(favorite))
            throw new NoAuthorityException("You don't have permission!");

        favoriteRepository.delete(favorite);
        return "Favorite successfully deleted!";
    }

    public String removeFavoriteByProductId(UUID productId) {
        Product product = productService.getProductById(productId);
        User user = userService.getCurrentAuthenticatedUser();

        if (!favoriteRepository.existsByUserAndProduct(user, product))
            throw new ResourceNotFoundException("This product not list on favorites!");

        Favorite favorite = favoriteRepository.findByUserAndProduct(user, product);

        favoriteRepository.delete(favorite);
        return "Favorite successfully deleted!";
    }


    public boolean hasUserAuthority(Favorite favorite) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        return favorite.getUser().equals(currentUser) || userService.isCurrentAuthenticatedUserAdmin();
    }
}