package main.java.hrms.human_resource_system.model;

import java.time.LocalDate;

public class Employee {
    private int id;
    private String PIN;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private LocalDate dateOfHire;
    private LocalDate dateOfDismissal;
    private String phoneNumber;
    private String email;
    private String address;
    private String gender;
    private Nationality nationality;
    private Department department;
    private Position position;
    private String employmentStatus;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String maritalStatus;
    private String employmentType;
    private Employee manager;
    private String taxId;
    private String bankAccountNumber;

    // Constructor
    public Employee(int id, String PIN, String lastName, String firstName, LocalDate birthDate,
                    LocalDate dateOfHire, LocalDate dateOfDismissal, String phoneNumber, String email,
                    String address, String gender, Nationality nationality, Department department,
                    Position position, String employmentStatus, String emergencyContactName,
                    String emergencyContactPhone, String maritalStatus, String employmentType,
                    Employee manager, String taxId, String bankAccountNumber) {
        this.id = id;
        this.PIN = PIN;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.dateOfHire = dateOfHire;
        this.dateOfDismissal = dateOfDismissal;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.nationality = nationality;
        this.department = department;
        this.position = position;
        this.employmentStatus = employmentStatus;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.maritalStatus = maritalStatus;
        this.employmentType = employmentType;
        this.manager = manager;
        this.taxId = taxId;
        this.bankAccountNumber = bankAccountNumber;
    }

    public Employee() {
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPIN() { return PIN; }
    public void setPIN(String PIN) { this.PIN = PIN; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public LocalDate getDateOfHire() { return dateOfHire; }
    public void setDateOfHire(LocalDate dateOfHire) { this.dateOfHire = dateOfHire; }

    public LocalDate getDateOfDismissal() { return dateOfDismissal; }
    public void setDateOfDismissal(LocalDate dateOfDismissal) { this.dateOfDismissal = dateOfDismissal; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Nationality getNationality() { return nationality; }
    public void setNationality(Nationality nationality) { this.nationality = nationality; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }

    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public Employee getManager() { return manager; }
    public void setManager(Employee manager) { this.manager = manager; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }
}
