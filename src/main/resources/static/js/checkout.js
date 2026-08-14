document.addEventListener("DOMContentLoaded", () => {
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

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

    const newAddressForm = document.querySelector("#address-modal form");
    const newPaymentForm = document.querySelector("#payment-modal form");

    function getCsrfHeaders() {
        const headers = {};

        if (csrfToken && csrfHeader) {
            headers[csrfHeader] = csrfToken;
        }

        return headers;
    }

    function updatePlaceOrderButton() {
        const placeOrderButton = document.getElementById("place-order-button");

        const addressSelected = addressConfirmed && !addressConfirmed.classList.contains("hidden");
        const paymentSelected = paymentConfirmed && !paymentConfirmed.classList.contains("hidden");
        const shippingSelected = shippingConfirmed && !shippingConfirmed.classList.contains("hidden");

        placeOrderButton.disabled = !(addressSelected && paymentSelected && shippingSelected);
    }

    // ADDRESS
    if (confirmAddressButton) {
        confirmAddressButton.addEventListener("click", async () => {
            const address = {
                customerFullName: document.getElementById("saved-address-full-name").value,
                street: document.getElementById("saved-address-street").value,
                city: document.getElementById("saved-address-city").value,
                state: document.getElementById("saved-address-state").value,
                zipCode: document.getElementById("saved-address-zip").value
            };

            try {
                const response = await fetch("/checkout/select-address", {
                    method: "POST",
                    headers: {
                        ...getCsrfHeaders(),
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(address)
                });

                if (!response.ok) {
                    console.error("Unable to save delivery address.");
                    return;
                }

                const confirmedAddress = document.querySelector(".confirmed-address");

                if (confirmedAddress) {
                    confirmedAddress.innerHTML = `
                        <span>${address.customerFullName}</span><br>
                        <span>${address.street}</span><br>
                        <span>${address.city}, ${address.state} ${address.zipCode}</span>
                    `;
                }

                addressSelection.classList.add("hidden");
                addressConfirmed.classList.remove("hidden");

                updatePlaceOrderButton();
                console.log("Address confirmed.")

            } catch (error) {
                console.error("Error selecting address: ", error);
            }
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

    // PAYMENT
    if (confirmPaymentButton) {
        confirmPaymentButton.addEventListener("click", async () => {
            const selectedPayment = document.querySelector('input[name="paymentMethodId"]:checked');
            if (!selectedPayment) return;

            const paymentMethodId = selectedPayment.value;

            try {
                const response = await fetch(
                    `/checkout/select-payment?paymentMethodId=${encodeURIComponent(paymentMethodId)}`,
                    {
                        method: "POST",
                        headers: {
                            ...getCsrfHeaders(),
                            "Content-Type": "application/json"
                        }
                    }
                );

                if (!response.ok) {
                    console.error("Unable to save payment method.");
                    return;
                }

                const paymentOption = selectedPayment.closest(".payment-option");
                const paymentText = paymentOption ? paymentOption.querySelector("span").innerHTML : "";

                const confirmedPaymentText = document.getElementById("confirmed-payment-text");
                if (confirmedPaymentText) confirmedPaymentText.innerHTML = paymentText;

                paymentSelection.classList.add("hidden");
                paymentConfirmed.classList.remove("hidden");

                updatePlaceOrderButton();
                console.log("Payment method confirmed.")

            } catch (error) {
                console.error("Error selecting payment method: ", error);
            }
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

    // SHIPPING
    if (confirmShippingButton) {
        confirmShippingButton.addEventListener("click", async () => {
            const selectedShipping = document.querySelector('input[name="shippingSpeed"]:checked');
            if (!selectedShipping) return;

            const shippingSpeed = selectedShipping.value;

            try {
                const response = await fetch(
                    `/checkout/shipping?shippingSpeed=${encodeURIComponent(shippingSpeed)}`,
                    {
                        method: "POST",
                        headers: {
                            ...getCsrfHeaders(),
                            "Content-Type": "application/json"
                        }
                    }
                );

                if (!response.ok) {
                    console.error("Unable to save shipping method.");
                    return;
                }

                const shippingText = selectedShipping
                    .closest(".shipping-option")
                    .querySelector("span")
                    .textContent
                    .trim();

                const estimatedDelivery = document.getElementById("estimated-delivery");
                if (estimatedDelivery) estimatedDelivery.textContent = shippingText;

                shippingSelection.classList.add("hidden");
                shippingConfirmed.classList.remove("hidden");

                const summary = await response.json();

                const subtotal = document.getElementById("subtotal")
                const shipping = document.getElementById("shipping")
                const finalTotal = document.getElementById("total")

                if (subtotal && shipping && finalTotal) {
                    subtotal.textContent = formatCurrency(summary.subtotal);
                    shipping.textContent = formatCurrency(summary.shipping);
                    finalTotal.textContent = formatCurrency(summary.total);
                }

                updatePlaceOrderButton();
                console.log("Shipping speed confirmed.")

            } catch (error) {
                console.error("Error selecting shipping method:", error);
            }
        });
    }

    if (changeShippingButton) {
        changeShippingButton.addEventListener("click", () => {
            shippingConfirmed.classList.add("hidden");
            shippingSelection.classList.remove("hidden");
        });
    }

    // ADDING A NEW ADDRESS
    if (newAddressForm) {
        newAddressForm.addEventListener("submit", async (event) => {
            event.preventDefault()

            const formData = new FormData(newAddressForm)
            const newAddress = {
                customerFullName: formData.get("customerFullName"),
                street: formData.get("street"),
                city: formData.get("city"),
                state: formData.get("state"),
                zipCode: formData.get("zipCode")
            }

            try {
                const response = await fetch("/checkout/address/new", {
                    method: "POST",
                    headers: getCsrfHeaders(),
                    body: JSON.stringify(newAddress)
                });

                if (!response.ok) {
                    console.error("Unable to add new address.");
                    return;
                }

                const confirmedAddress = document.querySelector(".confirmed-address");

                if (confirmedAddress) {
                    confirmedAddress.innerHTML = `
                        <span>${newAddress.customerFullName}</span><br>
                        <span>${newAddress.street}</span><br>
                        <span>${newAddress.city}, ${newAddress.state} ${newAddress.zipCode}</span>
                    `;
                }

                addressModal.classList.add("hidden");
                addressSelection.classList.add("hidden")
                addressConfirmed.classList.remove("hidden");

                updatePlaceOrderButton();
                console.log("New address added to checkout.")

            } catch (error) {
                console.error("Error selecting address: ", error);
            }
        })
    }

    // ADDING A NEW PAYMENT CARD
    if (newPaymentForm) {
        newPaymentForm.addEventListener("submit", async (event) => {
            event.preventDefault();

            const expirationMonth = document.getElementById("expiration-month").value;
            const expirationYear = document.getElementById("expiration-year").value;

            if (!expirationMonth || !expirationYear) {
                console.error("Expiration month and year are required.");
                return;
            }

            document.getElementById("expiration-date").value = `${expirationYear}-${expirationMonth}-01`;

            const formData = new FormData(newPaymentForm);

            try {
                const response = await fetch("/checkout/payment/new", {
                    method: "POST",
                    headers: getCsrfHeaders(),
                    body: formData
                });

                if (!response.ok) {
                    console.error("Unable to add new payment card.");
                    return;
                }

                const newCard = await response.json();

                const confirmedPaymentText = document.getElementById("confirmed-payment-text");

                if (confirmedPaymentText) {
                    confirmedPaymentText.innerHTML = `
                        <strong>${newCard.cardProcessor}</strong> ending in <strong>${newCard.lastFourDigits}</strong>
                    `;
                }

                paymentModal.classList.add("hidden");
                paymentSelection.classList.add("hidden");
                paymentConfirmed.classList.remove("hidden");

                updatePlaceOrderButton();
                console.log("New payment card added to checkout.");

            } catch (error) {
                console.error("Error adding new payment card:", error);
            }
        });
    }

    // CLOSE MODALS WITH ESCAPE
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

function formatCurrency(amount) {
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD"
    }).format(Number(amount));
}