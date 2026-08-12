document.addEventListener("DOMContentLoaded", () => {

    const addressSelection = document.getElementById("address-selection");
    const addressConfirmed = document.getElementById("address-confirmed");

    const paymentSelection = document.getElementById("payment-selection");
    const paymentConfirmed = document.getElementById("payment-confirmed");

    const shippingSelection = document.getElementById("shipping-selection");
    const shippingConfirmed = document.getElementById("shipping-confirmed");

    const addressModal = document.getElementById("address-modal");
    const paymentModal = document.getElementById("payment-modal");

    const differentAddressButton = document.getElementById("different-address-button");
    const confirmAddressButton = document.getElementById("confirm-address-button");
    const changeAddressButton = document.getElementById("change-address-button");
    const closeAddressModal = document.getElementById("close-address-modal");

    const newPaymentButton = document.getElementById("new-payment-button");
    const confirmPaymentButton = document.getElementById("confirm-payment-button");
    const changePaymentButton = document.getElementById("change-payment-button");
    const closePaymentModal = document.getElementById("close-payment-modal");

    const confirmShippingButton = document.getElementById("confirm-shipping-button");
    const changeShippingButton = document.getElementById("change-shipping-button");

    if (confirmAddressButton) {
        confirmAddressButton.addEventListener("click", () => {
            addressSelection.classList.add("hidden");
            addressConfirmed.classList.remove("hidden");
        });
    }

    if (changeAddressButton) {
        changeAddressButton.addEventListener("click", () => {
            addressConfirmed.classList.add("hidden");
            addressSelection.classList.remove("hidden");
        });
    }

    if (differentAddressButton) {
        differentAddressButton.addEventListener("click", () => {
            addressModal.classList.remove("hidden");
        });
    }

    if (closeAddressModal) {
        closeAddressModal.addEventListener("click", () => {
            addressModal.classList.add("hidden");
        });
    }

    if (addressModal) {
        addressModal.querySelector(".modal-overlay").addEventListener("click", () => {
            addressModal.classList.add("hidden");
        });
    }

    if (confirmPaymentButton) {
        confirmPaymentButton.addEventListener("click", () => {
            const selectedPayment = document.querySelector(
                'input[name="paymentMethodId"]:checked'
            );

            if (!selectedPayment) {
                return;
            }

            const paymentOption = selectedPayment.closest(".payment-option");
            const paymentText = paymentOption
                ? paymentOption.querySelector("span").textContent.trim()
                : "";

            const confirmedPaymentText =
                document.getElementById("confirmed-payment-text");

            if (confirmedPaymentText) {
                confirmedPaymentText.textContent = paymentText;
            }

            paymentSelection.classList.add("hidden");
            paymentConfirmed.classList.remove("hidden");
        });
    }

    if (changePaymentButton) {
        changePaymentButton.addEventListener("click", () => {
            paymentConfirmed.classList.add("hidden");
            paymentSelection.classList.remove("hidden");
        });
    }

    if (newPaymentButton) {
        newPaymentButton.addEventListener("click", () => {
            paymentModal.classList.remove("hidden");
        });
    }

    if (closePaymentModal) {
        closePaymentModal.addEventListener("click", () => {
            paymentModal.classList.add("hidden");
        });
    }

    if (paymentModal) {
        paymentModal.querySelector(".modal-overlay").addEventListener("click", () => {
            paymentModal.classList.add("hidden");
        });
    }

    if (confirmShippingButton) {
        confirmShippingButton.addEventListener("click", () => {
            const selectedShipping = document.querySelector(
                'input[name="shippingSpeed"]:checked'
            );

            if (!selectedShipping) {
                return;
            }

            const shippingText = selectedShipping
                .closest(".shipping-option")
                .querySelector("span")
                .textContent
                .trim();

            const estimatedDelivery = document.getElementById("estimated-delivery");

            if (estimatedDelivery) {
                estimatedDelivery.textContent = shippingText;
            }

            shippingSelection.classList.add("hidden");
            shippingConfirmed.classList.remove("hidden");
        });
    }

    if (changeShippingButton) {
        changeShippingButton.addEventListener("click", () => {
            shippingConfirmed.classList.add("hidden");
            shippingSelection.classList.remove("hidden");
        });
    }

    document.addEventListener("keydown", (event) => {
        if (event.key === "Escape") {
            if (addressModal) {
                addressModal.classList.add("hidden");
            }

            if (paymentModal) {
                paymentModal.classList.add("hidden");
            }
        }
    });
});