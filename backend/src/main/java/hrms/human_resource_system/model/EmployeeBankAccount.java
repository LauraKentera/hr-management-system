package main.java.hrms.human_resource_system.model;

public class EmployeeBankAccount {

    private int employeeId;
    private String bankName;
    private String accountNumber;
    private String iban;

    public EmployeeBankAccount() {}

    public EmployeeBankAccount(int employeeId, String bankName, String accountNumber, String iban) {
        this.employeeId = employeeId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.iban = iban;
    }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }
}

