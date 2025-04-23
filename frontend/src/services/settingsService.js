import axios from 'axios';

const API = '/api';

// Nationalities
export const getNationalities = () => axios.get(`${API}/nationalities`);
export const createNationality = data => axios.post(`${API}/nationalities`, data);
export const updateNationality = (id, data) => axios.put(`${API}/nationalities/${id}`, data);
export const deleteNationality = id => axios.delete(`${API}/nationalities/${id}`);

// Education Levels
export const getEducationLevels = () => axios.get(`${API}/education-levels`);
export const createEducationLevel = data => axios.post(`${API}/education-levels`, data);
export const updateEducationLevel = (id, data) => axios.put(`${API}/education-levels/${id}`, data);
export const deleteEducationLevel = id => axios.delete(`${API}/education-levels/${id}`);
