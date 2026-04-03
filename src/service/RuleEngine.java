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
            request.setDecisionReason("Medical leave is generally approved.");
            return;
        }

        // Check for Emergency leave
        if ("Emergency".equalsIgnoreCase(request.getLeaveType())) {
            if (request.getNumberOfDays() > 3) {
                request.setStatus("REVIEW");
                request.setDecisionReason("Emergency leave > 3 days requires review.");
            } else {
                request.setStatus("APPROVED");
                request.setDecisionReason("Emergency leave <= 3 days is approved.");
            }
            return;
        }
        
        // Casual Leave Checks
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

        // Check number of days for Casual
        if (request.getNumberOfDays() <= 2) {
            request.setStatus("APPROVED");
            request.setDecisionReason("Casual leave for 2 days or less is approved.");
        } else if (request.getNumberOfDays() == 3) {
            request.setStatus("REVIEW");
            request.setDecisionReason("Casual leave for exactly 3 days requires review.");
        } else {
            request.setStatus("REJECTED");
            request.setDecisionReason("Casual leave for more than 3 days is rejected.");
        }
    }

    private boolean isWeekend(Date date) {
        if (date == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }
}
