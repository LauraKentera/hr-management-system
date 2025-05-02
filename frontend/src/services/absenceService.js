import axios from 'axios';

const BASE = '/api/absences';
const TYPES = '/api/absence-types';

const getAbsences = () => axios.get(BASE);
const getAbsenceTypes = () => axios.get(TYPES);
const createAbsence = (data) => axios.post(BASE, data);
const updateAbsence = (id, data) => axios.put(`${BASE}/${id}`, data);

// New method to approve/deny absence
const approveOrDenyAbsence = (id, action, approvedBy) =>
    axios.put(`/api/employee-absences/${id}/${action}?approvedBy=${approvedBy}`);

export default {
  getAbsences,
  getAbsenceTypes,
  createAbsence,
  updateAbsence,
  approveOrDenyAbsence, // Include the new method for approve/deny
};
