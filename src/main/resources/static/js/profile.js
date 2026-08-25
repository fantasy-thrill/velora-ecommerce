const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
const updateMessage = document.querySelector(".update-message")

const editProfileButton = document.getElementById("edit-profile-button");
const editProfileModal = document.getElementById("edit-profile-modal");

const changePasswordButton = document.getElementById("change-password-button");
const changePasswordModal = document.getElementById("change-password-modal");
const changePasswordForm = document.querySelector("#change-password-modal form");

function getCsrfHeaders() {
    const headers = {};
    if (csrfToken && csrfHeader) headers[csrfHeader] = csrfToken;

    return headers;
}

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

if (changePasswordForm) {
    changePasswordForm.addEventListener("submit", async (event) => {
        event.preventDefault()

        const formData = new FormData(changePasswordForm)

        try {
            const response = await fetch("/account/change-password", {
                method: "POST",
                headers: getCsrfHeaders(),
                body: formData
            })

            const data = await response.json()

            if (!response.ok) {
                const errorMessage = document.querySelector(".form-error")
                errorMessage.style.display = "block"

                for (const message of data.messages) errorMessage.textContent = message + "\n"
                changePasswordForm.reset()
                return;
            }

            changePasswordModal.style.display = "none"
            updateMessage.style.display = "block"
            updateMessage.textContent = data.messages[0]
            changePasswordForm.reset()

        } catch (error) {
            console.error("Unable to update password.\n", error)
        }
    })
}


// Close either modal when pressing Escape
document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
        editProfileModal.style.display = "none";
        changePasswordModal.style.display = "none";
    }
});