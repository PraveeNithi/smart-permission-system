import React, { useEffect, useState } from 'react';
import { usePermissions } from '../hooks/usePermissions';
import Loader from '../components/Loader';
import StatusBadge from '../components/StatusBadge';
import { formatDate } from '../utils/formatDate';

export default function ViewRequests() {
  const { permissions, loading, loadPermissions } = usePermissions();
  const [filter, setFilter] = useState('ALL');

  useEffect(() => {
    loadPermissions();
  }, [loadPermissions]);

  if (loading) return <Loader text="Loading Active Requests..." />;

  const filteredPermissions = filter === 'ALL' 
    ? permissions 
    : permissions.filter(p => p.status === filter);

  const sortedPermissions = [...filteredPermissions].reverse();

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-6">
        <div>
          <h1 className="text-3xl font-bold text-brown-900 tracking-tight">Active Requests</h1>
          <p className="text-brown-500 mt-1">Review the status of automated and pending requests.</p>
        </div>
        
        <div className="flex bg-white rounded-lg p-1 border border-sand-200 shadow-sm">
          {['ALL', 'APPROVED', 'REJECTED', 'REVIEW'].map(status => (
            <button
              key={status}
              onClick={() => setFilter(status)}
              className={`px-4 py-1.5 text-sm font-medium rounded-md transition-colors ${
                filter === status 
                  ? 'bg-brown-100 text-brown-900' 
                  : 'text-brown-500 hover:text-brown-800 hover:bg-sand-50'
              }`}
            >
              {status}
            </button>
          ))}
        </div>
      </div>

      <div className="bg-white border rounded-xl overflow-hidden shadow-sm border-sand-200">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-sand-50 border-b border-sand-200 text-brown-600 text-xs uppercase tracking-wider">
                <th className="p-4 font-semibold whitespace-nowrap">Employee</th>
                <th className="p-4 font-semibold whitespace-nowrap">Date</th>
                <th className="p-4 font-semibold">Reason</th>
                <th className="p-4 font-semibold whitespace-nowrap">Status</th>
                <th className="p-4 font-semibold max-w-[200px]">Decision Logic</th>
              </tr>
            </thead>
            <tbody className="text-sm divide-y divide-sand-100">
              {sortedPermissions.length === 0 ? (
                <tr>
                  <td colSpan="5" className="p-8 text-center text-brown-500">No requests found matching this filter.</td>
                </tr>
              ) : (
                sortedPermissions.map((req) => (
                  <tr key={req.id} className="hover:bg-sand-50 transition-colors">
                    <td className="p-4 whitespace-nowrap">
                      <p className="font-semibold text-brown-900">{req.employeeName}</p>
                      <p className="text-xs text-brown-500">ID: {req.employeeId}</p>
                    </td>
                    <td className="p-4 text-brown-800 whitespace-nowrap">{formatDate(req.requestDate)}</td>
                    <td className="p-4 text-brown-800 min-w-[200px]">{req.reason}</td>
                    <td className="p-4 whitespace-nowrap">
                      <StatusBadge status={req.status} />
                    </td>
                    <td className="p-4 text-brown-600 text-xs italic">{req.decisionReason}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
