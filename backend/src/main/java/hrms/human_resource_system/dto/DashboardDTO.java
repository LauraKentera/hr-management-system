package hrms.human_resource_system.dto;

public class DashboardDTO {

    private int attendance;
    private int lateArrivals;
    private int absents;
    private int leaveApplied;

    // Constructor, Getters, and Setters

    public DashboardDTO(int attendance, int lateArrivals, int absents, int leaveApplied) {
        this.attendance = attendance;
        this.lateArrivals = lateArrivals;
        this.absents = absents;
        this.leaveApplied = leaveApplied;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getLateArrivals() {
        return lateArrivals;
    }

    public void setLateArrivals(int lateArrivals) {
        this.lateArrivals = lateArrivals;
    }

    public int getAbsents() {
        return absents;
    }

    public void setAbsents(int absents) {
        this.absents = absents;
    }

    public int getLeaveApplied() {
        return leaveApplied;
    }

    public void setLeaveApplied(int leaveApplied) {
        this.leaveApplied = leaveApplied;
    }
}
