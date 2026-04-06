import React, { useEffect, useState } from 'react';
import { usePermissions } from '../hooks/usePermissions';
import Loader from '../components/Loader';
import PermissionCard from '../components/PermissionCard';
import { Search } from 'lucide-react';

export default function History() {
  const { permissions, loading, loadPermissions } = usePermissions();
  const [searchTerm, setSearchTerm] = useState('');
  const [statusFilter, setStatusFilter] = useState('');

  useEffect(() => {
    loadPermissions();
  }, [loadPermissions]);

  if (loading) return <Loader text="Loading History Data..." />;

  const filteredHistory = permissions.filter(p => {
    const matchesSearch = String(p.employeeId).includes(searchTerm) || p.employeeName.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus = statusFilter === '' || p.status === statusFilter;
    return matchesSearch && matchesStatus;
  }).reverse();

  return (
    <div className="space-y-6">
      <div className="mb-6">
        <h1 className="text-3xl font-bold text-brown-900 tracking-tight">Request History</h1>
        <p className="text-brown-500 mt-1">Detailed view of all historical permission queries.</p>
      </div>

      <div className="bg-white p-4 rounded-xl shadow-sm border border-sand-200 flex flex-col md:flex-row gap-4 mb-8">
        <div className="flex-1 relative">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-brown-400" size={18} />
          <input 
            type="text" 
            placeholder="Search by Employee ID or Name..." 
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full pl-10 pr-4 py-2 border border-sand-200 rounded-lg focus:ring-2 focus:ring-brown-500 outline-none"
          />
        </div>
        <select 
          value={statusFilter} 
          onChange={(e) => setStatusFilter(e.target.value)}
          className="border border-sand-200 rounded-lg px-4 py-2 focus:ring-2 focus:ring-brown-500 outline-none bg-white text-brown-800"
        >
          <option value="">All Statuses</option>
          <option value="APPROVED">Approved</option>
          <option value="REJECTED">Rejected</option>
          <option value="REVIEW">Review</option>
        </select>
      </div>

      {filteredHistory.length === 0 ? (
        <div className="text-center bg-white p-12 rounded-xl border border-sand-200 shadow-sm text-brown-500">
          No history records match your search criteria.
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredHistory.map(req => (
            <PermissionCard key={req.id} request={req} />
          ))}
        </div>
      )}
    </div>
  );
}
