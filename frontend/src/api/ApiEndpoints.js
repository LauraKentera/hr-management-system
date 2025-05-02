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
        getAll: `${BASE_URL}/employees`,
        getByUserId: (userId) => `${BASE_URL}/api/employees/by-user/${userId}`,
        create: `${BASE_URL}/employees`,
        update: (id) => `${BASE_URL}/employees/${id}`,
        delete: (id) => `${BASE_URL}/employees/${id}`,
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
    payroll: {
        getAll: `${BASE_URL}/api/payrolls`,
        getById: (id) => `${BASE_URL}/api/payrolls/${id}`,
        create: `${BASE_URL}/api/payrolls`,
        update: (id) => `${BASE_URL}/api/payrolls/${id}`,
        delete: (id) => `${BASE_URL}/api/payrolls/${id}`,
        generate: `${BASE_URL}/api/payrolls/generate`,
    },

};

export default ApiEndpoints;
