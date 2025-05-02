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
    benefit: {
        getAll: `${BASE_URL}/api/benefits`,
        getById: (id) => `${BASE_URL}/api/benefits/${id}`,
        create: `${BASE_URL}/api/benefits`,
        update: (id) => `${BASE_URL}/api/benefits/${id}`,
        delete: (id) => `${BASE_URL}/api/benefits/${id}`,
    },

    benefitItem: {
        getAll: `${BASE_URL}/api/benefit-items`,
        getById: (id) => `${BASE_URL}/api/benefit-items/${id}`,
        getByBenefitId: (benefitId) => `${BASE_URL}/api/benefit-items/benefit/${benefitId}`,
        create: `${BASE_URL}/api/benefit-items`,
        update: (id) => `${BASE_URL}/api/benefit-items/${id}`,
        delete: (id) => `${BASE_URL}/api/benefit-items/${id}`,
    },

    employeeBenefit: {
        getAll: `${BASE_URL}/api/employee-benefits`,
        getById: (id) => `${BASE_URL}/api/employee-benefits/${id}`,
        create: `${BASE_URL}/api/employee-benefits`,
        update: (id) => `${BASE_URL}/api/employee-benefits/${id}`,
        delete: (id) => `${BASE_URL}/api/employee-benefits/${id}`,
    },
};

export default ApiEndpoints;
