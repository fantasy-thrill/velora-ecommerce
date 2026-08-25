package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.CardProcessor;
import com.example.velora_ecommerce.enums.CardType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCardResponseDto {
    private Long paymentCardId;

    private String cardholderName;

    private CardType cardType;

    private CardProcessor cardProcessor;

    private String lastFourDigits;

    private LocalDate expirationDate;

    private String displayString;
}
