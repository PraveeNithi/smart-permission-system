import React from 'react';
import StatusBadge from './StatusBadge';
import { formatDate } from '../utils/formatDate';

export default function PermissionCard({ request }) {
  return (
    <div className="bg-white rounded-xl shadow-sm border border-sand-200 p-5 hover:shadow-md transition-shadow">
      <div className="flex justify-between items-start mb-4">
        <div>
          <h3 className="font-semibold text-brown-900 text-lg">{request.employeeName}</h3>
          <p className="text-sm text-brown-500">ID: {request.employeeId}</p>
        </div>
        <StatusBadge status={request.status} />
      </div>
      
      <div className="grid grid-cols-2 gap-y-3 mb-4 text-sm">
        <div>
          <span className="block text-brown-500 text-xs uppercase tracking-wider font-semibold mb-0.5">Date</span>
          <span className="text-brown-800 font-medium">{formatDate(request.requestDate)}</span>
        </div>
        <div>
          <span className="block text-brown-500 text-xs uppercase tracking-wider font-semibold mb-0.5">Days</span>
          <span className="text-brown-800 font-medium">{request.numberOfDays} ({request.leaveType})</span>
        </div>
      </div>
      
      <div className="mb-4">
        <span className="block text-brown-500 text-xs uppercase tracking-wider font-semibold mb-1">Reason</span>
        <p className="text-sm text-brown-800 bg-sand-50 p-2.5 rounded-md border border-sand-100">{request.reason}</p>
      </div>
      
      {request.decisionReason && (
        <div className="bg-sand-50 p-3 rounded-lg border border-sand-200">
          <span className="text-xs font-semibold uppercase tracking-wider text-brown-600 block mb-1">Auto Decision:</span>
          <p className="text-sm text-brown-800 italic">{request.decisionReason}</p>
        </div>
      )}
    </div>
  );
}
