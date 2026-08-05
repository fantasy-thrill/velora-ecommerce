// Page Sections
const verifyEmailSection = document.getElementById("verify-email-section");
const resetPasswordSection = document.getElementById("reset-password-section");
const successSection = document.getElementById("success-section");

// Forms
const verifyEmailForm = document.getElementById("verify-email-form");
const resetPasswordForm = document.getElementById("reset-password-form");

// Tokens
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

// Error Messages
const emailError = document.getElementById("email-error");
const passwordError = document.getElementById("password-error");
const confirmPasswordError = document.getElementById("confirm-password-error");

// Buttons
const backButton = document.getElementById("back-button");

// Keep Track Of Verified Email
let verifiedEmail = "";

// Verify Email
verifyEmailForm.addEventListener(
    "submit",
    async function(event) {
        event.preventDefault();
        emailError.textContent = "";

        const email = document.getElementById("email").value;

        try {
            const response = await fetch(
                "/auth/verify-email",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        [csrfHeader]: csrfToken
                    },
                    body: JSON.stringify({
                        email: email
                    })
                });

            const result = await response.json();

            if (result.exists) {
                verifiedEmail = email;
                verifyEmailSection.classList.add("hidden");
                resetPasswordSection.classList.remove("hidden");

            } else {
                emailError.textContent = result.errorMessage;
            }

        } catch (error) {
            emailError.textContent = "An unexpected error occurred.";
            console.error(error);
        }
    }
);

// Reset Password
resetPasswordForm.addEventListener(
    "submit",
    async function(event) {
        event.preventDefault();

        passwordError.textContent = "";
        confirmPasswordError.textContent = "";

        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirmPassword").value;

        // Client-side check
        if (password !== confirmPassword) {
            confirmPasswordError.textContent = "Passwords do not match.";
            return;
        }

        try {
            const response = await fetch(
                "/auth/reset-password?email=" + encodeURIComponent(verifiedEmail),
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        [csrfHeader]: csrfToken
                    },
                    body: JSON.stringify({
                        password: password,
                        confirmPassword: confirmPassword
                    })
                });

            const result = await response.json();

            if (result.success) {
                resetPasswordSection.classList.add("hidden");
                successSection.classList.remove("hidden");

            } else {
                passwordError.textContent = result.message;
            }

        } catch (error) {
            passwordError.textContent = "An unexpected error occurred.";
            console.error(error);
        }
    }
);

// Back Button
backButton.addEventListener(
    "click",
    function() {
        passwordError.textContent = "";
        confirmPasswordError.textContent = "";
        resetPasswordSection.classList.add("hidden");
        verifyEmailSection.classList.remove("hidden");
    }
);