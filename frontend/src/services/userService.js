import axios from 'axios';

const BASE_URL = '/api/users';
const ROLE_URL = '/api/roles';

// User endpoints
export const getUsers = () => axios.get(BASE_URL);
export const getUser = (id) => axios.get(`${BASE_URL}/${id}`);
export const createUser = (data) => axios.post(BASE_URL, data);
export const updateUser = (id, data) => axios.put(`${BASE_URL}/${id}`, data);
export const deleteUser = (id) => axios.delete(`${BASE_URL}/${id}`);

// Role endpoints
export const getRoles = () => axios.get(ROLE_URL);
export const createRole = (data) => axios.post(ROLE_URL, data);
export const updateRole = (id, data) => axios.put(`${ROLE_URL}/${id}`, data);
export const deleteRole = (id) => axios.delete(`${ROLE_URL}/${id}`);

export default {
  getUsers,
  getUser,
  createUser,
  updateUser,
  deleteUser,
  getRoles,
  createRole,
  updateRole,
  deleteRole,
};
