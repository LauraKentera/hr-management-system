import ApiEndpoints from "../api/ApiEndpoints";

export async function login(username, password) {
    const response = await fetch(ApiEndpoints.auth.login, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include", // <-- THIS FIXES OPTIONS 403
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

    // 💾 Fetch and save employeeId
    const empRes = await fetch(`http://localhost:8080/api/employees/by-user/${data.userId}`, {
        credentials: "include", // Just to be safe
    });

    if (!empRes.ok) {
        throw new Error("Failed to fetch employee data");
    }

    const employee = await empRes.json();
    localStorage.setItem("employeeId", employee.id);

    return data;
}
