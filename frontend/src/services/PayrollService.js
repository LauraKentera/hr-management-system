import axios from 'axios';
import ApiEndpoints from '../api/ApiEndpoints';

const PayrollService = {
    getAll: () => axios.get(ApiEndpoints.payroll.getAll),
    getById: (id) => axios.get(ApiEndpoints.payroll.getById(id)),
    create: (data) => axios.post(ApiEndpoints.payroll.create, data),
    update: (id, data) => axios.put(ApiEndpoints.payroll.update(id), data),
    delete: (id) => axios.delete(ApiEndpoints.payroll.delete(id)),
    generate: (data) => axios.post(ApiEndpoints.payroll.generate, data),
};

export default PayrollService;
