const API_URL = '/api/employees';

const getAllEmployees = async () => {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error('Failed to fetch employees');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching employees:', error);
        throw error;
    }
};

const getEmployeeById = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) {
            throw new Error(`Failed to fetch employee with ID ${id}`);
        }
        return await response.json();
    } catch (error) {
        console.error(`Error fetching employee with ID ${id}:`, error);
        throw error;
    }
};

const createEmployee = async (employee) => {
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(employee),
        });
        if (!response.ok) {
            throw new Error('Failed to create employee');
        }
        return await response.json();
    } catch (error) {
        console.error('Error creating employee:', error);
        throw error;
    }
};

const updateEmployee = async (id, employee) => {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(employee),
        });
        if (!response.ok) {
            throw new Error(`Failed to update employee with ID ${id}`);
        }
        return await response.json();
    } catch (error) {
        console.error(`Error updating employee with ID ${id}:`, error);
        throw error;
    }
};

const deleteEmployee = async (id) => {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error(`Failed to delete employee with ID ${id}`);
        }
    } catch (error) {
        console.error(`Error deleting employee with ID ${id}:`, error);
        throw error;
    }
};
