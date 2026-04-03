package service;

import dao.PermissionDAO;
import model.PermissionRequest;

import java.util.List;

public class PermissionService {

    private final PermissionDAO permissionDAO;
    private final RuleEngine ruleEngine;

    public PermissionService() {
        this.permissionDAO = new PermissionDAO();
        this.ruleEngine = new RuleEngine();
    }

    public PermissionRequest processRequest(PermissionRequest request) {
        // Analyze and determine state
        ruleEngine.evaluateRequest(request);
        
        // Store the request
        boolean success = permissionDAO.insertRequest(request);
        if (!success) {
            throw new RuntimeException("Failed to save the request to the database. See logs for details.");
        }
        return request;
    }

    public List<PermissionRequest> getFullHistory() {
        return permissionDAO.getAllRequests();
    }

    public List<PermissionRequest> getHistoryByEmployee(int employeeId) {
        return permissionDAO.getRequestsByEmployee(employeeId);
    }

    public List<PermissionRequest> getHistoryByStatus(String status) {
        return permissionDAO.getRequestsByStatus(status);
    }
}
