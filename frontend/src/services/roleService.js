import ApiEndpoints from "../api/ApiEndpoints";

export async function fetchRoles() {
    const response = await fetch(ApiEndpoints.role.getAll);  // Using the updated endpoint
    if (!response.ok) throw new Error("Failed to fetch roles");
    return await response.json();  // Ensure the response includes the permissions array
}

export async function createRole(roleData) {
    const response = await fetch(ApiEndpoints.role.create, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(roleData),
    });
    if (!response.ok) throw new Error("Failed to create role");
    return response.json();
}
