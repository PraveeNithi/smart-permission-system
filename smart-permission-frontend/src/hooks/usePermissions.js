import { useState, useCallback } from 'react';
import { fetchRequests, applyPermission } from '../services/api';

export function usePermissions() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [permissions, setPermissions] = useState([]);

  const loadPermissions = useCallback(async (filters) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetchRequests(filters);
      setPermissions(response.data);
    } catch (err) {
      setError(err.message || 'Failed to fetch permissions');
    } finally {
      setLoading(false);
    }
  }, []);

  const submitPermission = async (data) => {
    setLoading(true);
    setError(null);
    try {
      const response = await applyPermission(data);
      setPermissions(prev => [...prev, response.data]);
      return response.data;
    } catch (err) {
      setError(err.message || 'Failed to submit permission');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return { permissions, loading, error, loadPermissions, submitPermission };
}
