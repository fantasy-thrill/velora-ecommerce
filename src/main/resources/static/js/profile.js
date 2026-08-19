const editProfileButton = document.getElementById("edit-profile-button");
const editProfileModal = document.getElementById("edit-profile-modal");

const changePasswordButton = document.getElementById("change-password-button");
const changePasswordModal = document.getElementById("change-password-modal");


// Open edit profile modal
editProfileButton.addEventListener("click", () => {
    editProfileModal.style.display = "flex";
});

let phoneNumberValue = document.getElementById("phone-number").value

if (phoneNumberValue) {
    const digits = phoneNumberValue.match(/\d/g)
    phoneNumberValue = digits.join("")
}


// Open change password modal
changePasswordButton.addEventListener("click", () => {
    changePasswordModal.style.display = "flex";
});


// Close modal when clicking outside the modal content
editProfileModal.addEventListener("click", (event) => {
    if (event.target === editProfileModal) {
        editProfileModal.style.display = "none";
    }
});

changePasswordModal.addEventListener("click", (event) => {
    if (event.target === changePasswordModal) {
        changePasswordModal.style.display = "none";
    }
});


// Close either modal when pressing Escape
document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
        editProfileModal.style.display = "none";
        changePasswordModal.style.display = "none";
    }
});