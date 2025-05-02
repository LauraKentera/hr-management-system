const BASE_URL = "http://localhost:8080"; // Or use env var later

const ApiEndpoints = {
    auth: {
        login: `${BASE_URL}/auth/login`,
    },
    user: {
        getAll: `${BASE_URL}/users`,
        getById: (id) => `${BASE_URL}/users/${id}`,
    },
    employee: {
        getAll: `${BASE_URL}/employees`,
        create: `${BASE_URL}/employees`,
        update: (id) => `${BASE_URL}/employees/${id}`,
        delete: (id) => `${BASE_URL}/employees/${id}`,
    },
    department: {
        getAll: `${BASE_URL}/departments`,
        create: `${BASE_URL}/departments`,
    },
    // Add more modules as needed
};

export default ApiEndpoints;
