package hrms.human_resource_system.service;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EmployeeBenefit;
import hrms.human_resource_system.repository.EmployeeBenefitDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeBenefitService {

    private final EmployeeBenefitDAO employeeBenefitDAO;

    public EmployeeBenefitService(EmployeeBenefitDAO employeeBenefitDAO) {
        this.employeeBenefitDAO = employeeBenefitDAO;
    }

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new DLException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public List<EmployeeBenefit> getAllEmployeeBenefits() {
        return wrap(employeeBenefitDAO::getAll);
    }

    public EmployeeBenefit getEmployeeBenefitById(int id) {
        return wrap(() -> employeeBenefitDAO.getById(id));
    }

    public EmployeeBenefit createEmployeeBenefit(EmployeeBenefit employeeBenefit) {
        return wrap(() -> {
            validateEmployeeBenefit(employeeBenefit);
            employeeBenefitDAO.insert(employeeBenefit);
            return employeeBenefit;
        });
    }

    public EmployeeBenefit updateEmployeeBenefit(EmployeeBenefit employeeBenefit) {
        return wrap(() -> {
            validateEmployeeBenefit(employeeBenefit);
            employeeBenefitDAO.update(employeeBenefit.getId(), employeeBenefit);
            return employeeBenefit;
        });
    }

    public void deleteEmployeeBenefit(int id) {
        wrap(() -> {
            employeeBenefitDAO.delete(id);
            return null;
        });
    }

    private void validateEmployeeBenefit(EmployeeBenefit employeeBenefit) {
        if (employeeBenefit.getEmployee() == null || employeeBenefit.getEmployee().getId() <= 0) {
            throw new IllegalArgumentException("Valid Employee is required.");
        }
        if (employeeBenefit.getBenefit() == null || employeeBenefit.getBenefit().getBenefitId() <= 0) {
            throw new IllegalArgumentException("Valid Benefit is required.");
        }
        if (employeeBenefit.getFromDate() == null) {
            throw new IllegalArgumentException("From Date is required.");
        }
        if (employeeBenefit.getToDate() != null && employeeBenefit.getFromDate().isAfter(employeeBenefit.getToDate())) {
            throw new IllegalArgumentException("From Date cannot be after To Date.");
        }
    }
}
