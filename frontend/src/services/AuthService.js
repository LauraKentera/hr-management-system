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

    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);
    localStorage.setItem("userId", data.userId);

    return data;
}
