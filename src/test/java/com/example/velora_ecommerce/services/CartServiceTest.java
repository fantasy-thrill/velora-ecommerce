package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.repositories.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CustomerService customerService;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void shouldGetCartItemCount() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("john@example.com");
        Cart cart = new Cart();

        CartItem laptop = new CartItem();
        laptop.setQuantity(2);

        CartItem keyboard = new CartItem();
        keyboard.setQuantity(3);

        cart.addItem(laptop);
        cart.addItem(keyboard);

        customer.setCart(cart);

        when(customerService.getCustomerByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        // Act
        int result = cartService.getCartItemCount("john@example.com");

        // Assert
        assertEquals(5, result);
        verify(customerService).getCustomerByEmail("john@example.com");
    }

    @Test
    void shouldAddItemToCart() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();
        customer.setCart(cart);
        cart.setCustomer(customer);

        Product product = new Product();
        product.setName("Laptop");

        when(customerService.getCustomerByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        // Act
        cartService.addItemToCart("john@example.com", product, 2);

        // Assert
        assertEquals(1, cart.getItems().size());
        CartItem addedItem = cart.getItems().get(0);

        assertEquals(product, addedItem.getProduct());
        assertEquals(2, addedItem.getQuantity());
        assertEquals(cart, addedItem.getCart());
    }

    @Test
    void shouldRemoveItemFromCart() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();

        CartItem item1 = new CartItem();
        item1.setId(1L);

        CartItem item2 = new CartItem();
        item2.setId(2L);

        CartItem item3 = new CartItem();
        item3.setId(3L);

        CartItem item4 = new CartItem();
        item4.setId(4L);

        cart.addItem(item1);
        cart.addItem(item2);
        cart.addItem(item3);
        cart.addItem(item4);

        customer.setCart(cart);

        when(customerService.getCustomerByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        // Act
        cartService.removeItemFromCart("john@example.com", 1L);

        // Assert
        assertEquals(3, cart.getItems().size());
        assertTrue(
                cart.getItems()
                        .stream()
                        .noneMatch(item -> item.getId().equals(1L))
        );
    }

    @Test
    void shouldUpdateCartItemQuantity() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();

        CartItem item = new CartItem();
        item.setId(1L);
        item.setQuantity(2);

        cart.addItem(item);
        customer.setCart(cart);

        when(customerService.getCustomerByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        // Act
        cartService.updateCartItemQuantity("john@example.com", 1L, 5);

        // Assert
        assertEquals(5, item.getQuantity());
        verify(cartRepository).save(cart);
    }

    @Test
    void shouldRemoveCartItemWhenQuantityIsZero() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();

        CartItem item = new CartItem();
        item.setId(1L);
        item.setQuantity(2);

        cart.addItem(item);
        customer.setCart(cart);

        when(customerService.getCustomerByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        // Act
        cartService.updateCartItemQuantity("john@example.com", 1L, 0);

        // Assert
        assertTrue(cart.getItems().isEmpty());
        verify(cartRepository, never()).save(any());
    }
}
