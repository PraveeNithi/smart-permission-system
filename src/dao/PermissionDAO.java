package dao;

import model.PermissionRequest;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAO {

    public boolean insertRequest(PermissionRequest request) {
        String sql = "INSERT INTO permission_requests (employee_id, employee_name, reason, request_date, " +
                     "number_of_days, leave_type, past_leave_count, status, decision_reason) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                     
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, request.getEmployeeId());
            pstmt.setString(2, request.getEmployeeName());
            pstmt.setString(3, request.getReason());
            pstmt.setDate(4, new java.sql.Date(request.getRequestDate().getTime()));
            pstmt.setInt(5, request.getNumberOfDays());
            pstmt.setString(6, request.getLeaveType());
            pstmt.setInt(7, request.getPastLeaveCount());
            pstmt.setString(8, request.getStatus());
            pstmt.setString(9, request.getDecisionReason());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PermissionRequest> getAllRequests() {
        return fetchRequests("SELECT * FROM permission_requests ORDER BY created_at DESC");
    }

    public List<PermissionRequest> getRequestsByEmployee(int employeeId) {
        String sql = "SELECT * FROM permission_requests WHERE employee_id = ? ORDER BY created_at DESC";
        List<PermissionRequest> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            list = mapResultSetToList(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<PermissionRequest> getRequestsByStatus(String status) {
        String sql = "SELECT * FROM permission_requests WHERE status = ? ORDER BY created_at DESC";
        List<PermissionRequest> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            list = mapResultSetToList(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<PermissionRequest> fetchRequests(String sql) {
        List<PermissionRequest> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            list = mapResultSetToList(rs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    private List<PermissionRequest> mapResultSetToList(ResultSet rs) throws SQLException {
        List<PermissionRequest> list = new ArrayList<>();
        while (rs.next()) {
            PermissionRequest req = new PermissionRequest();
            req.setId(rs.getInt("id"));
            req.setEmployeeId(rs.getInt("employee_id"));
            req.setEmployeeName(rs.getString("employee_name"));
            req.setReason(rs.getString("reason"));
            req.setRequestDate(rs.getDate("request_date"));
            req.setNumberOfDays(rs.getInt("number_of_days"));
            req.setLeaveType(rs.getString("leave_type"));
            req.setPastLeaveCount(rs.getInt("past_leave_count"));
            req.setStatus(rs.getString("status"));
            req.setDecisionReason(rs.getString("decision_reason"));
            req.setCreatedAt(rs.getTimestamp("created_at"));
            list.add(req);
        }
        return list;
    }
}
