import axios from "axios";
import ApiEndpoints from "../api/ApiEndpoints";

export const fetchEmployeesWithBenefits = async () => {
    const response = await axios.get(ApiEndpoints.benefit.getEmployeesWithBenefits);
    return response.data;
};

export const fetchBenefitTypes = async () => {
    const response = await axios.get(ApiEndpoints.benefit.getBenefitTypes);
    return response.data;
};

export const saveEmployeeBenefit = async (employeeId, benefitId, amount) => {
    await axios.post(ApiEndpoints.benefit.assignBenefit, { employeeId, benefitId, amount });
};

export const deleteEmployeeBenefit = async (employeeId, benefitId) => {
    await axios.delete(ApiEndpoints.benefit.removeBenefit, { data: { employeeId, benefitId } });
};