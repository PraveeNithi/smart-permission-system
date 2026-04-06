import React, { useState } from 'react';
import { usePermissions } from '../hooks/usePermissions';
import { useNavigate } from 'react-router-dom';
import Loader from '../components/Loader';

export default function ApplyPermission() {
  const { submitPermission, loading, error } = usePermissions();
  const navigate = useNavigate();
  
  const [formData, setFormData] = useState({
    employeeId: '',
    employeeName: '',
    reason: '',
    numberOfDays: 1,
    leaveType: 'Annual',
    pastLeaveCount: 0
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const dataToSubmit = {
        ...formData,
        employeeId: Number(formData.employeeId),
        numberOfDays: Number(formData.numberOfDays),
        pastLeaveCount: Number(formData.pastLeaveCount)
      };
      
      const result = await submitPermission(dataToSubmit);
      
      alert(`Request Submitted!\n\nAuto Decision: ${result.status}\nReason: ${result.decisionReason}`);
      navigate('/requests');
    } catch (err) {
      console.error(err);
    }
  };

  if (loading) return <Loader text="Processing auto-decision..." />;

  return (
    <div className="max-w-2xl mx-auto">
      <div className="mb-8 text-center">
        <h1 className="text-3xl font-bold text-brown-900 tracking-tight">Apply for Permission</h1>
        <p className="text-brown-500 mt-2">Fill in the details below. Our smart engine will evaluate it instantly.</p>
      </div>

      {error && <div className="bg-red-50 text-red-700 p-4 rounded-lg mb-6 border border-red-200">{error}</div>}

      <div className="bg-white p-8 rounded-xl shadow-sm border border-sand-200">
        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label className="block text-sm font-semibold text-brown-800 mb-1">Employee ID</label>
              <input required type="number" name="employeeId" value={formData.employeeId} onChange={handleChange} 
                className="w-full border border-sand-200 rounded-md p-2 focus:ring-2 focus:ring-brown-500 outline-none" />
            </div>
            <div>
              <label className="block text-sm font-semibold text-brown-800 mb-1">Employee Name</label>
              <input required type="text" name="employeeName" value={formData.employeeName} onChange={handleChange} 
                className="w-full border border-sand-200 rounded-md p-2 focus:ring-2 focus:ring-brown-500 outline-none" />
            </div>
          </div>

          <div>
            <label className="block text-sm font-semibold text-brown-800 mb-1">Reason for Leave</label>
            <textarea required name="reason" value={formData.reason} onChange={handleChange} rows="3"
              className="w-full border border-sand-200 rounded-md p-2 focus:ring-2 focus:ring-brown-500 outline-none" />
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div>
              <label className="block text-sm font-semibold text-brown-800 mb-1">Number of Days</label>
              <input required type="number" min="1" name="numberOfDays" value={formData.numberOfDays} onChange={handleChange} 
                className="w-full border border-sand-200 rounded-md p-2 focus:ring-2 focus:ring-brown-500 outline-none" />
            </div>
            <div>
              <label className="block text-sm font-semibold text-brown-800 mb-1">Leave Type</label>
              <select name="leaveType" value={formData.leaveType} onChange={handleChange} 
                className="w-full border border-sand-200 rounded-md p-2 focus:ring-2 focus:ring-brown-500 outline-none bg-white">
                <option value="Annual">Annual Leave</option>
                <option value="Sick">Sick Leave</option>
                <option value="Personal">Personal Leave</option>
              </select>
            </div>
            <div>
              <label className="block text-sm font-semibold text-brown-800 mb-1">Past Leave Count</label>
              <input required type="number" min="0" name="pastLeaveCount" value={formData.pastLeaveCount} onChange={handleChange} 
                className="w-full border border-sand-200 rounded-md p-2 focus:ring-2 focus:ring-brown-500 outline-none" />
            </div>
          </div>

          <div className="pt-4">
            <button type="submit" 
              className="w-full bg-brown-600 hover:bg-brown-800 text-white font-bold py-3 px-4 rounded-lg transition-colors shadow-sm text-lg">
              Submit Request
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
