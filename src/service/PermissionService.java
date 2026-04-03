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
        permissionDAO.insertRequest(request);
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
