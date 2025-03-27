document.getElementById("treatmentPlanForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const planType = document.getElementById("planType").value;
    const patientId = localStorage.getItem("userId"); // Assuming the user ID is stored in local storage

    if (!patientId) {
        document.getElementById("message").innerText = "Error: Please log in first.";
        return;
    }

    fetch("/api/treatment/subscribe", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ patientId, planType })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("message").innerText = data.message;
    })
    .catch(error => {
        console.error("Error:", error);
        document.getElementById("message").innerText = "An error occurred. Please try again.";
    });
});
