import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL;

export const getContracts = (filters) =>
  axios.get(`${API_URL}/api/contracts`, { params: filters });

export const getContractById = (id) =>
  axios.get(`${API_URL}/api/contracts/${id}`);

export const createContract = (data) =>
  axios.post(`${API_URL}/api/contracts`, data);

export const updateContract = (id, data) =>
  axios.put(`${API_URL}/api/contracts/${id}`, data);

export const deleteContract = (id) =>
  axios.delete(`${API_URL}/api/contracts/${id}`);

export const getAnnexes = (contractId) =>
  axios.get(`${API_URL}/api/contracts/${contractId}/annexes`);

export const uploadAnnex = (contractId, file) => {
  const form = new FormData();
  form.append('file', file);
  return axios.post(
    `${API_URL}/api/contracts/${contractId}/annexes`,
    form,
    { headers: { 'Content-Type': 'multipart/form-data' } }
  );
};
