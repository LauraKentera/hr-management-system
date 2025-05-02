import ApiEndpoints from "../api/ApiEndpoints";

export async function login(username, password) {
    const response = await fetch(ApiEndpoints.auth.login, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
        throw new Error("Invalid credentials");
    }

    const data = await response.json();

    // Save token, role, and userId
    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);
    localStorage.setItem("userId", data.userId);

    // 👇 Fetch employeeId from backend
    const empRes = await fetch(`http://localhost:8080/api/employees/by-user/${data.userId}`);
    if (!empRes.ok) {
        throw new Error("Failed to fetch employee data");
    }

    const employee = await empRes.json();
    localStorage.setItem("employeeId", employee.id); // 💾 Now stored for use in absences

    return data;
}
