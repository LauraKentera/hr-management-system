const BASE_URL = "http://localhost:8080"; // Or use env var later

const ApiEndpoints = {
    auth: {
        login: `${BASE_URL}/auth/login`,
    },
    user: {
        getAll: `${BASE_URL}/api/users`, // adjust port if needed
        create: `${BASE_URL}/api/users`,
        update: (id) => `${BASE_URL}/api/users/${id}`,
        delete: (id) => `${BASE_URL}/api/users/${id}`,
    },
    employee: {
        getAll: `${BASE_URL}/api/employees`,
        getByUserId: (userId) => `${BASE_URL}/api/employees/${userId}`,
        create: `${BASE_URL}/api/employees`,
        update: (id) => `${BASE_URL}/api/employees/${id}`,
        delete: (id) => `${BASE_URL}/api/employees/${id}`,
    },
    department: {
        getAll: `${BASE_URL}/departments`,
        create: `${BASE_URL}/departments`,
    },
    absences: {
        getAll: `${BASE_URL}/api/employee-absences`,
        create: `${BASE_URL}/api/employee-absences`,
    },
    benefit: {
        getAll: `${BASE_URL}/api/benefits`,
        create: `${BASE_URL}/api/benefits`,
    },
    role: {
        getAll: `${BASE_URL}/api/roles`,
        create: `${BASE_URL}/api/roles`,
    },

};

export default ApiEndpoints;
