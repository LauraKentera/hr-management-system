// src/services/positionService.js
const positionService = {
    getPositions: async () => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    data: [
                        { id: 1, name: 'Software Engineer', parentId: null },
                        { id: 2, name: 'Product Manager', parentId: null },
                        { id: 3, name: 'HR Manager', parentId: null },
                        { id: 4, name: 'Frontend Developer', parentId: 1 },
                        { id: 5, name: 'Backend Developer', parentId: 1 },
                        { id: 6, name: 'Recruiter', parentId: 3 },
                    ],
                });
            }, 1000);
        });
    },
};

export default positionService;
