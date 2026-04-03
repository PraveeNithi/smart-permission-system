CREATE TABLE IF NOT EXISTS permission_requests (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    employee_id INTEGER,
    employee_name TEXT,
    reason TEXT,
    request_date DATE,
    number_of_days INTEGER,
    leave_type TEXT,
    past_leave_count INTEGER,
    status TEXT,
    decision_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
