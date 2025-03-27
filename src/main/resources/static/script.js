document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("loginForm");
    const registerForm = document.getElementById("registerForm");

    // 🔹 Handle Registration
    if (registerForm) {
        registerForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const username = document.getElementById("regUsername").value;
            const password = document.getElementById("regPassword").value;
            const role = document.getElementById("regRole").value.toUpperCase(); //  Convert to uppercase

            const response = await fetch("http://localhost:8080/api/user/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password, role })
            });

            const message = await response.text();
            document.getElementById("message").innerText = message;
        });
    }

    // 🔹 Handle Login
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const username = document.getElementById("loginUsername").value;
            const password = document.getElementById("loginPassword").value;

            const response = await fetch("http://localhost:8080/api/user/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                const errorData = await response.json();
                document.getElementById("message").innerText = errorData.error || "Login failed!";
                return;
            }

            const result = await response.json(); // Parse JSON response
            document.getElementById("message").innerText = result.message;

            // Convert role to uppercase before checking
            if (result.role) {
                const roleUpper = result.role.toUpperCase();
                const roleRedirects = {
                    "PATIENT": "patient_dashboard.html",
                    "DOCTOR": "doctor_dashboard.html",
                    "ADMIN": "admin_dashboard.html"
                };

                if (roleRedirects[roleUpper]) {
                    window.location.href = roleRedirects[roleUpper];
                } else {
                    document.getElementById("message").innerText = "Unknown role!";
                }
            } else {
                document.getElementById("message").innerText = "Unknown role!";
            }
        });
    }
});
