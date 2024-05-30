package com.indigo.controller;


import com.indigo.entity.Favourite;
import com.indigo.entity.PropertyUser;
import com.indigo.repository.FavouriteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {
    private FavouriteRepository favouriteRepository;

    public FavouriteController(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }
    @PostMapping
    public ResponseEntity<Favourite> addfavourite(@RequestBody Favourite favourite, @AuthenticationPrincipal PropertyUser user){
        favourite.setPropertyUser(user);
        Favourite savedFavourite  = favouriteRepository.save(favourite);
        return  new ResponseEntity<>(savedFavourite, HttpStatus.CREATED);
        

    }

}
