import axios from 'axios';

const API_URL = 'http://localhost:8080';  // Spring Boot backend running locally

// Login function
export const login = async (username, password) => {
    try {
        const response = await axios.post(`${API_URL}/login`, { username, password });
        if (response.data.token) {
            localStorage.setItem('token', response.data.token);  // Store token in localStorage
        }
        return response.data;
    } catch (error) {
        console.error('Authentication failed:', error);
    }
};

// Logout function
export const logout = () => {
    localStorage.removeItem('token');  // Remove token on logout
};

// Get the token from localStorage
export const getToken = () => {
    return localStorage.getItem('token');
};

// Check if user is authenticated by looking for a token in localStorage
export const isAuthenticated = () => {
    return !!getToken();  // Returns true if a token is found, false otherwise
};
