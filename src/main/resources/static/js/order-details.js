document.addEventListener("DOMContentLoaded", () => {
    const cancelOrderButton = document.getElementById("cancel-order-button");
    const cancelOrderModal = document.getElementById("cancel-order-modal");
    const closeCancelOrderModal = document.getElementById("close-cancel-order-modal");
    const forgetCancelOrder = document.getElementById("forget-cancel-order");
    const cancelOrderForm = document.getElementById("cancel-order-form");

    function openCancelOrderModal() {
        cancelOrderModal.classList.remove("hidden");
    }

    function closeCancelOrderModalFunction() {
        cancelOrderModal.classList.add("hidden");
    }

    cancelOrderButton.addEventListener("click", openCancelOrderModal);

    closeCancelOrderModal.addEventListener("click", closeCancelOrderModalFunction);

    forgetCancelOrder.addEventListener("click", closeCancelOrderModalFunction);

    cancelOrderModal.querySelector(".modal-overlay").addEventListener(
        "click",
        closeCancelOrderModalFunction
    );

    cancelOrderForm.addEventListener("submit", () => {
        cancelOrderModal.classList.add("hidden");
    });
});