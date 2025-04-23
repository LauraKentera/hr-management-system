import axios from 'axios';

const BASE = '/api/absences';
const TYPES = '/api/absence-types';

const getAbsences = () => axios.get(BASE);
const getAbsenceTypes = () => axios.get(TYPES);
const createAbsence = (data) => axios.post(BASE, data);
const updateAbsence = (id, data) => axios.put(`${BASE}/${id}`, data);

export default {
  getAbsences,
  getAbsenceTypes,
  createAbsence,
  updateAbsence,
};
