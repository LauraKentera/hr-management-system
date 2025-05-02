import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api';

// Nationalities
export const getNationalities = () => axios.get(`${BASE_URL}/nationalities`);
export const createNationality = data => axios.post(`${BASE_URL}/nationalities`, data);
export const updateNationality = (id, data) => axios.put(`${BASE_URL}/nationalities/${id}`, data);
export const deleteNationality = id => axios.delete(`${BASE_URL}/nationalities/${id}`);

// Education Levels
export const getEducationLevels = () => axios.get(`${BASE_URL}/education-levels`);
export const createEducationLevel = data => axios.post(`${BASE_URL}/education-levels`, data);
export const updateEducationLevel = (id, data) => axios.put(`${BASE_URL}/education-levels/${id}`, data);
export const deleteEducationLevel = id => axios.delete(`${BASE_URL}/education-levels/${id}`);
