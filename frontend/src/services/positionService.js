import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL;

export const getPositions = () =>
  axios.get(`${API_URL}/api/positions`);

export const getPositionById = (id) =>
  axios.get(`${API_URL}/api/positions/${id}`);
