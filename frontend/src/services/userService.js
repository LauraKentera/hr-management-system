import ApiEndpoints from "../api/ApiEndpoints";

export async function fetchUsers() {
  const res = await fetch(ApiEndpoints.user.getAll);
  if (!res.ok) throw new Error("Failed to fetch users");
  return res.json();
}

export async function deleteUser(id) {
  const res = await fetch(ApiEndpoints.user.delete(id), {
    method: "DELETE",
  });
  if (!res.ok) throw new Error("Failed to delete user");
}

export async function createUser(userData) {
  const res = await fetch(ApiEndpoints.user.create, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });
  if (!res.ok) throw new Error("Failed to create user");
  return res.json();
}

export async function updateUser(id, userData) {
  const res = await fetch(ApiEndpoints.user.update(id), {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });
  if (!res.ok) throw new Error("Failed to update user");
  return res.json();
}
