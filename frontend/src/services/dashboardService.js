import axios from "axios";
import ApiEndpoints from "../api/ApiEndpoints";

const DashboardService = {
    getOverview: () => axios.get(ApiEndpoints.dashboard.overview),
    getTotalEmployees: () => axios.get(ApiEndpoints.dashboard.totalEmployees),
    getActiveContracts: () => axios.get(ApiEndpoints.dashboard.activeContracts),
    getPendingBenefits: () => axios.get(ApiEndpoints.dashboard.pendingBenefits),
    getAbsencesToday: () => axios.get(ApiEndpoints.dashboard.absencesToday),
    getAverageSalary: () => axios.get(ApiEndpoints.dashboard.averageSalary),
    getRetentionRate: () => axios.get(ApiEndpoints.dashboard.retentionRate),
    getBenefitsUtilization: () => axios.get(ApiEndpoints.dashboard.benefitsUtilization),
    getContractExpirations: () => axios.get(ApiEndpoints.dashboard.contractExpirations),
    getNextPayCycle: () => axios.get(ApiEndpoints.dashboard.nextPayCycle),

    // Get the payroll distribution for each employee
    getPayrollDistribution: () => axios.get(ApiEndpoints.dashboard.payrollDistribution)
};

export default DashboardService;
