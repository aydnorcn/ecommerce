package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.store.CreateStoreDto;
import com.orcnaydn.ecommerce.dto.store.UpdateStoreDto;
import com.orcnaydn.ecommerce.entity.Review;
import com.orcnaydn.ecommerce.entity.Store;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.exception.AlreadyExistsException;
import com.orcnaydn.ecommerce.exception.NoAuthorityException;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.filter.StoreFilter;
import com.orcnaydn.ecommerce.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;


    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("Store not found!"));
    }

    public PageResponseDto<Store> getStores(UUID userId, Float minTotalScore, Float maxTotalScore, int pageNo, int pageSize) {
        User user = userService.getUserIfExists(userId);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Specification<Store> specification = StoreFilter.filter(user, minTotalScore, maxTotalScore);
        Page<Store> stores = storeRepository.findAll(specification, pageable);

        return new PageResponseDto<>(stores.getContent(), stores.getNumber(), stores.getSize(), stores.getTotalElements(), stores.getTotalPages());
    }

    public Store createStore(CreateStoreDto dto) {
        if (storeRepository.existsByNameIgnoreCase(dto.getName()))
            throw new AlreadyExistsException("This store name already taken!");


        return storeRepository.save(initializeStore(dto));
    }

    public Store updateStore(Long storeId, UpdateStoreDto dto) {
        Store store = getStoreById(storeId);

        if (!hasUserAuthority(store))
            throw new NoAuthorityException("You don't have permission!");

        store.setName(dto.getName());
        return storeRepository.save(store);
    }

    public String deleteStore(Long storeId) {
        Store store = getStoreById(storeId);

        if (!hasUserAuthority(store))
            throw new NoAuthorityException("You don't have permission!");

        storeRepository.delete(store);

        return "Store successfully deleted!";
    }


    public boolean hasUserAuthority(Store store) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        return store.getOwners().contains(currentUser) || userService.isCurrentAuthenticatedUserAdmin();
    }

    public void calculateStoreTotalScore(Long storeId) {
        Store store = getStoreById(storeId);

        int totalScore = 0;

        store.getProducts().stream()
                .flatMap(product -> product.getReviews().stream())
                .mapToInt(Review::getScore)
                .sum();

        long reviewCount = store.getProducts().stream()
                .flatMap(product -> product.getReviews().stream())
                .count();

        store.setTotalScore((float) totalScore / (float) reviewCount);
        storeRepository.save(store);
    }

    private Store initializeStore(CreateStoreDto dto) {
        Store store = new Store();
        store.setName(dto.getName());
        store.setTotalScore(0f);
        List<User> owners = new ArrayList<>();
        owners.add(userService.getCurrentAuthenticatedUser());

        if (dto.getOwnersId() != null && !dto.getOwnersId().isEmpty()) {
            dto.getOwnersId().forEach(x -> {
                owners.add(userService.getUserById(x));
            });
        }
        store.setOwners(owners);
        return store;
    }

    public Store getStoreIfExists(Long storeId) {
        return (storeId != null) ? getStoreById(storeId) : null;
    }
}