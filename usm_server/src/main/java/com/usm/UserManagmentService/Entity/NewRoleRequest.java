package com.usm.UserManagmentService.Entity;

/**
 * It's a model class that represents a request to create a new role for a user.
 */
public class NewRoleRequest {

    private int userId;
    private String roleName;
    private String startDate;
    private String endDate;

    public NewRoleRequest() {
    }

    public NewRoleRequest(int userId, String roleName, String startDate, String endDate) {
        this.userId = userId;
        this.roleName = roleName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "NewRoleRequest{" +
                "userId=" + userId +
                ", roleName='" + roleName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
