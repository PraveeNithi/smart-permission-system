package service;

import model.PermissionRequest;
import java.util.Calendar;
import java.util.Date;

public class RuleEngine {

    public void evaluateRequest(PermissionRequest request) {
        // Evaluate rules to determine status and reason
        
        // Check for Medical leave
        if ("Medical".equalsIgnoreCase(request.getLeaveType())) {
            request.setStatus("APPROVED");
            request.setDecisionReason("Medical leave is always approved.");
            return;
        }
        
        // Check past leave count
        if (request.getPastLeaveCount() > 10) {
            request.setStatus("REJECTED");
            request.setDecisionReason("Past leave count exceeds limit (10).");
            return;
        }
        
        if (isWeekend(request.getRequestDate())) {
            request.setStatus("REJECTED");
            request.setDecisionReason("Requests starting on weekends are rejected.");
            return;
        }

        // Check number of days
        if (request.getNumberOfDays() <= 1) {
            request.setStatus("APPROVED");
            request.setDecisionReason("Leave for 1 day or less is approved.");
            return;
        } else if (request.getNumberOfDays() > 3) {
            request.setStatus("REVIEW");
            request.setDecisionReason("Leave for more than 3 days requires review.");
            return;
        }

        // Otherwise -> REVIEW
        request.setStatus("REVIEW");
        request.setDecisionReason("Request falls under general review policy.");
    }

    private boolean isWeekend(Date date) {
        if (date == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }
}
