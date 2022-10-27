package com.bridgelabz.bs.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bs.book.dto.CartDto;
import com.bridgelabz.bs.book.model.Cart;
import com.bridgelabz.bs.book.service.ICartService;
import com.bridgelabz.bs.utility.Response;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {
	@Autowired
    public ICartService cartService;

    @GetMapping( "/getAllCartItems")
    public ResponseEntity<Response> getAllItems(){
        List<Cart> cartList = cartService.getAllItems();
        Response response = new Response("Get call successful", cartList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/addToCart")
    public ResponseEntity<Response> addToCart(@RequestHeader String token, @RequestBody CartDto cartDto){
        Cart cart = cartService.addItem(token, cartDto);
        Response response = new Response("Added to Cart successfully", cart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getCartById")
    public ResponseEntity<Response> getCartById(@RequestParam int id){
        Cart cart = cartService.getCartItemById(id);
        Response response = new Response("Get call for id successful", cart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getCartByUserId")
    public ResponseEntity<Response> getCartByUserId(@RequestHeader String token){
        List<Cart> carts = cartService.getCartItemByUserId(token);
        Response response = new Response("Cart details for User ", carts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/editCartItem")
    public ResponseEntity<Response> editCartItem(@RequestBody CartDto cartDto, @RequestParam int id){
        Cart cart = cartService.editCart(id, cartDto);
        Response response = new Response("Cart updated successfully", cart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteItemFromCart")
    public ResponseEntity<Response> deleteCartItem(@RequestParam int id){
        cartService.deleteItem(id);
        Response response = new Response("Item From Cart Deleted Successfully", "Cart item deleted for id: "+id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/updateQtyInCart")
    public ResponseEntity<Response> updateQuantityInCart(@RequestParam int id, @RequestBody CartDto cartDto){
        Cart cart = cartService.updateBookQuantityInCart(id, cartDto);
        Response response = new Response("Book Quantity updated in Cart successfully", cart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
