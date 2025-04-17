import axios from 'axios';

const API_URL = 'http://your-api-url.com';  // Replace with your API URL

export const login = async (username, password) => {
    try {
        const response = await axios.post(`${API_URL}/login`, { username, password });
        if (response.data.token) {
            localStorage.setItem('token', response.data.token);  // Store token in localStorage
        }
        return response.data;
    } catch (error) {
        console.error(error);
    }
};

export const logout = () => {
    localStorage.removeItem('token');  // Remove token on logout
};

export const getToken = () => {
    return localStorage.getItem('token');
};
