// src/services/departmentService.js
const departmentService = {
  getDepartments: async () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({
          data: [
            { id: 1, name: 'Engineering', manager: { id: 1, firstName: 'John', lastName: 'Doe' } },
            { id: 2, name: 'Marketing', manager: { id: 2, firstName: 'Jane', lastName: 'Smith' } },
            { id: 3, name: 'Sales', manager: { id: 3, firstName: 'Michael', lastName: 'Brown' } },
          ],
        });
      }, 1000); // Simulate API call with a delay
    });
  },

  createDepartment: async (data) => {
    console.log('Creating department with data:', data);
    return new Promise((resolve) => {
      setTimeout(() => resolve({ data: { id: 4, ...data } }), 1000);
    });
  },

  updateDepartment: async (id, data) => {
    console.log(`Updating department with ID ${id} and data:`, data);
    return new Promise((resolve) => {
      setTimeout(() => resolve({ data: { id, ...data } }), 1000);
    });
  },
};

export default departmentService;
