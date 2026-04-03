package model;

import java.util.Date;

public class PermissionRequest {
    private int id;
    private int employeeId;
    private String employeeName;
    private String reason;
    private Date requestDate;
    private int numberOfDays;
    private String leaveType;
    private int pastLeaveCount;
    private String status;
    private String decisionReason;
    private Date createdAt;

    // Constructors
    public PermissionRequest() {}

    public PermissionRequest(int employeeId, String employeeName, String reason, Date requestDate, 
                             int numberOfDays, String leaveType, int pastLeaveCount) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.reason = reason;
        this.requestDate = requestDate;
        this.numberOfDays = numberOfDays;
        this.leaveType = leaveType;
        this.pastLeaveCount = pastLeaveCount;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Date getRequestDate() { return requestDate; }
    public void setRequestDate(Date requestDate) { this.requestDate = requestDate; }

    public int getNumberOfDays() { return numberOfDays; }
    public void setNumberOfDays(int numberOfDays) { this.numberOfDays = numberOfDays; }

    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }

    public int getPastLeaveCount() { return pastLeaveCount; }
    public void setPastLeaveCount(int pastLeaveCount) { this.pastLeaveCount = pastLeaveCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDecisionReason() { return decisionReason; }
    public void setDecisionReason(String decisionReason) { this.decisionReason = decisionReason; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "PermissionRequest{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", requestDate=" + requestDate +
                ", numberOfDays=" + numberOfDays +
                ", leaveType='" + leaveType + '\'' +
                ", status='" + status + '\'' +
                ", decisionReason='" + decisionReason + '\'' +
                '}';
    }
}
