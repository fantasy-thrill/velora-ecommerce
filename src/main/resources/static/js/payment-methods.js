document.addEventListener("DOMContentLoaded", () => {
    /*  Add payment card modal  */
    const paymentModal = document.getElementById("payment-modal");
    const addPaymentButton = document.getElementById("add-payment-method-button");
    const closePaymentModal = document.getElementById("close-payment-modal");

    const paymentModalOverlay = paymentModal?.querySelector(".modal-overlay");
    const shouldOpenNewPaymentModal = paymentModal.dataset.open === "true"
    if (shouldOpenNewPaymentModal) openPaymentModal()

    function openPaymentModal() {
        paymentModal.classList.remove("hidden");
    }

    function closePaymentModalFunction() {
        paymentModal.classList.add("hidden");
    }

    if (addPaymentButton) addPaymentButton.addEventListener("click", openPaymentModal);
    if (closePaymentModal) closePaymentModal.addEventListener("click", closePaymentModalFunction);
    if (paymentModalOverlay) paymentModalOverlay.addEventListener("click", closePaymentModalFunction);


    /*  Edit payment card modal  */
    const editPaymentModal = document.getElementById("edit-payment-modal");
    const closeEditPaymentModal = document.getElementById("close-edit-payment-modal");
    const editPaymentModalOverlay = editPaymentModal?.querySelector(".modal-overlay");
    const editPaymentForm = document.getElementById("edit-payment-form");
    const editCardId = document.getElementById("edit-card-id");
    const editCardholderName = document.getElementById("edit-cardholder-name")
    const editCardType = document.getElementById("edit-card-type")
    const editCardProcessor = document.getElementById("edit-card-processor");
    const editCardNumber = document.getElementById("edit-card-number");
    const editExpirationMonth = document.getElementById("edit-expiration-month");
    const editExpirationYear = document.getElementById("edit-expiration-year");

    const shouldOpenEditPaymentModal = editPaymentModal.dataset.open === "true";
    const editPaymentCardId = editPaymentModal.dataset.cardId;

    if (shouldOpenEditPaymentModal && editPaymentCardId) {
        const editButton = document.querySelector(`.edit-card-button[data-card-id="${editPaymentCardId}"]`);

        if (editButton) openEditPaymentModal(editButton);
    }

    function openEditPaymentModal(button) {
        const cardId = button.dataset.cardId;
        const nameOnCard = button.dataset.cardHolderName
        const updateUrl = button.dataset.updateUrl;
        const cardType = button.dataset.cardType;
        const processor = button.dataset.cardProcessor;
        const expiration = button.dataset.cardExpiration;
        const lastFour = button.dataset.cardLastFour;

        editPaymentForm.action = updateUrl;
        editCardId.value = cardId;
        editCardholderName.value = nameOnCard
        editCardProcessor.value = processor;
        editCardType.value = cardType
        editCardNumber.value = "************" + lastFour;

        if (expiration) {
            const expirationParts = expiration.split("-");

            const year = expirationParts[0];
            const month = expirationParts[1];

            editExpirationYear.value = year;
            editExpirationMonth.value = month;
        }

        editPaymentModal.classList.remove("hidden");
    }

    document.querySelectorAll(".edit-card-button").forEach(button =>
        button.addEventListener("click", () => openEditPaymentModal(button))
    );

    function closeEditPaymentModalFunction() {
        editPaymentModal.classList.add("hidden");
    }

    if (closeEditPaymentModal) closeEditPaymentModal.addEventListener("click", closeEditPaymentModalFunction);
    if (editPaymentModalOverlay) editPaymentModalOverlay.addEventListener("click", closeEditPaymentModalFunction);


    /*  Delete payment card confirmation modal  */
    const deleteCardModal = document.getElementById("delete-card-modal");
    const closeDeleteCardModal = document.getElementById("close-delete-card-modal");
    const cancelDeleteCard = document.getElementById("cancel-delete-card");
    const deleteCardForm = document.getElementById("delete-card-form");
    const deleteCardModalOverlay = deleteCardModal?.querySelector(".modal-overlay");


    function openDeleteCardModal(button) {
        const deleteUrl = button.dataset.deleteUrl;
        deleteCardForm.action = deleteUrl;
        deleteCardModal.classList.remove("hidden");
    }

    document.querySelectorAll(".delete-card-button").forEach(button => {
        button.addEventListener("click", () => openDeleteCardModal(button));
    });

    function closeDeleteCardModalFunction() {
        deleteCardModal.classList.add("hidden");
    }

    if (closeDeleteCardModal) closeDeleteCardModal.addEventListener("click", closeDeleteCardModalFunction);
    if (cancelDeleteCard) cancelDeleteCard.addEventListener("click", closeDeleteCardModalFunction);
    if (deleteCardModalOverlay) deleteCardModalOverlay.addEventListener("click", closeDeleteCardModalFunction);


    /*  Escape key  */
    document.addEventListener("keydown", event => {
        if (event.key !== "Escape") return;

        if (paymentModal && !paymentModal.classList.contains("hidden")) closePaymentModalFunction();
        if (editPaymentModal && !editPaymentModal.classList.contains("hidden")) closeEditPaymentModalFunction();
        if (deleteCardModal && !deleteCardModal.classList.contains("hidden")) closeDeleteCardModalFunction();
    });
});