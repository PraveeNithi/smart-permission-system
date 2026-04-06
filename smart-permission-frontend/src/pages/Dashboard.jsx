import React, { useEffect } from 'react';
import { usePermissions } from '../hooks/usePermissions';
import Loader from '../components/Loader';
import { CheckCircle, XCircle, Clock, FileText } from 'lucide-react';

export default function Dashboard() {
  const { permissions, loading, loadPermissions } = usePermissions();

  useEffect(() => {
    loadPermissions();
  }, [loadPermissions]);

  if (loading) return <Loader text="Loading dashboard data..." />;

  const stats = {
    total: permissions.length,
    approved: permissions.filter(p => p.status === 'APPROVED').length,
    rejected: permissions.filter(p => p.status === 'REJECTED').length,
    review: permissions.filter(p => p.status === 'REVIEW').length,
  };

  const statCards = [
    { label: 'Total Requests', value: stats.total, icon: FileText, color: 'text-brown-600', bg: 'bg-brown-100' },
    { label: 'Approved', value: stats.approved, icon: CheckCircle, color: 'text-emerald-600', bg: 'bg-emerald-100' },
    { label: 'Rejected', value: stats.rejected, icon: XCircle, color: 'text-red-600', bg: 'bg-red-100' },
    { label: 'Pending Review', value: stats.review, icon: Clock, color: 'text-amber-600', bg: 'bg-amber-100' },
  ];

  return (
    <div className="space-y-6">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-brown-900 tracking-tight">Dashboard Overview</h1>
        <p className="text-brown-500 mt-1">Monitor the smart permission requests across your organization.</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {statCards.map((stat, idx) => {
          const Icon = stat.icon;
          return (
            <div key={idx} className="bg-white rounded-xl p-6 shadow-sm border border-sand-200 flex items-center gap-4 hover:shadow-md transition-shadow">
              <div className={`p-4 rounded-full ${stat.bg} ${stat.color}`}>
                <Icon size={24} strokeWidth={2.5} />
              </div>
              <div>
                <p className="text-sm font-semibold text-brown-500 uppercase tracking-wider">{stat.label}</p>
                <p className="text-3xl font-bold text-brown-900 leading-none mt-1">{stat.value}</p>
              </div>
            </div>
          );
        })}
      </div>

      <div className="mt-12 bg-white p-8 rounded-xl shadow-sm border border-sand-200 text-center">
        <h2 className="text-xl font-bold text-brown-800 mb-2">Welcome to SmartPermit</h2>
        <p className="text-brown-600 max-w-2xl mx-auto">
          The automated system uses intelligent rules to auto-approve regular requests and dynamically flags exceptions for manual review. Check the Apply page to see the logic in action!
        </p>
      </div>
    </div>
  );
}
