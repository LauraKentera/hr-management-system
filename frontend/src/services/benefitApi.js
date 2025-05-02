import axios from 'axios';
import ApiEndpoints from '../api/ApiEndpoints';

const headers = {
    'Content-Type': 'application/json',
};

// -------------------- Benefit --------------------
export const fetchAllBenefits = async () => {
    const res = await axios.get(ApiEndpoints.benefit.getAll);
    return res.data;
};

export const fetchBenefitById = async (id) => {
    const res = await axios.get(ApiEndpoints.benefit.getById(id));
    return res.data;
};

export const createBenefit = async (benefit, performedBy) => {
    const res = await axios.post(
        `${ApiEndpoints.benefit.create}?performedBy=${performedBy}`,
        benefit,
        { headers }
    );
    return res.data;
};

export const updateBenefit = async (id, benefit, performedBy) => {
    const res = await axios.put(
        `${ApiEndpoints.benefit.update(id)}?performedBy=${performedBy}`,
        benefit,
        { headers }
    );
    return res.data;
};

export const deleteBenefit = async (id, performedBy) => {
    const res = await axios.delete(
        `${ApiEndpoints.benefit.delete(id)}?performedBy=${performedBy}`
    );
    return res.data;
};

// -------------------- BenefitItem --------------------
export const fetchAllBenefitItems = async () => {
    const res = await axios.get(ApiEndpoints.benefitItem.getAll);
    return res.data;
};

export const fetchBenefitItemById = async (id) => {
    const res = await axios.get(ApiEndpoints.benefitItem.getById(id));
    return res.data;
};

export const fetchBenefitItemsByBenefitId = async (benefitId) => {
    const res = await axios.get(ApiEndpoints.benefitItem.getByBenefitId(benefitId));
    return res.data;
};

export const createBenefitItem = async (item, performedBy) => {
    const res = await axios.post(
        `${ApiEndpoints.benefitItem.create}?performedBy=${performedBy}`,
        item,
        { headers }
    );
    return res.data;
};

export const updateBenefitItem = async (id, item, performedBy) => {
    const res = await axios.put(
        `${ApiEndpoints.benefitItem.update(id)}?performedBy=${performedBy}`,
        item,
        { headers }
    );
    return res.data;
};

export const deleteBenefitItem = async (id, performedBy) => {
    const res = await axios.delete(
        `${ApiEndpoints.benefitItem.delete(id)}?performedBy=${performedBy}`
    );
    return res.data;
};

// -------------------- EmployeeBenefit --------------------
export const fetchAllEmployeeBenefits = async () => {
    const res = await axios.get(ApiEndpoints.employeeBenefit.getAll);
    return res.data;
};

export const fetchEmployeeBenefitById = async (id) => {
    const res = await axios.get(ApiEndpoints.employeeBenefit.getById(id));
    return res.data;
};

export const createEmployeeBenefit = async (data) => {
    const res = await axios.post(ApiEndpoints.employeeBenefit.create, data, { headers });
    return res.data;
};

export const updateEmployeeBenefit = async (id, data) => {
    const res = await axios.put(ApiEndpoints.employeeBenefit.update(id), data, { headers });
    return res.data;
};

export const deleteEmployeeBenefit = async (id) => {
    const res = await axios.delete(ApiEndpoints.employeeBenefit.delete(id));
    return res.data;
};
