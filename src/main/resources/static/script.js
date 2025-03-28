
    document.addEventListener("DOMContentLoaded", function () {
        const loginForm = document.getElementById("loginForm");
        const registerForm = document.getElementById("registerForm");
    
        // Handle Registration
        if (registerForm) {
            registerForm.addEventListener("submit", async (e) => {
                e.preventDefault();
                const name = document.getElementById("regName").value.trim();
                const email = document.getElementById("regEmail").value.trim();
                const password = document.getElementById("regPassword").value.trim();
                const role = document.getElementById("regRole").value.toUpperCase().trim();
    
                if (!name || !email || !password || !role) {
                    document.getElementById("message").innerText = "All fields are required!";
                    return;
                }
    
                const userData = { name, email, password, role };
    
                const response = await fetch("http://localhost:8080/api/user/register", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(userData)
                });
    
                const message = await response.text();
                document.getElementById("message").innerText = message;
            });
        }
    
        // Handle Login
        if (loginForm) {
            loginForm.addEventListener("submit", async (e) => {
                e.preventDefault();
                const email = document.getElementById("loginEmail").value.trim();
                const password = document.getElementById("loginPassword").value.trim();
    
                if (!email || !password) {
                    document.getElementById("message").innerText = "Please enter both email and password!";
                    return;
                }
    
                const response = await fetch("http://localhost:8080/api/user/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ email, password })
                });
    
                if (!response.ok) {
                    const errorData = await response.json();
                    document.getElementById("message").innerText = errorData.error || "Login failed!";
                    return;
                }
    
                const result = await response.json();
                document.getElementById("message").innerText = result.message;
    
                const roleRedirects = {
                    "PATIENT": "patient_dashboard.html",
                    "DOCTOR": "doctor_dashboard.html",
                    "ADMIN": "admin_dashboard.html"
                };
    
                if (result.role && roleRedirects[result.role.toUpperCase()]) {
                    window.location.href = roleRedirects[result.role.toUpperCase()];
                } else {
                    document.getElementById("message").innerText = "Unknown role!";
                }
            });
        }
    });
