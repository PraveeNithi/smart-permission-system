import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

export const applyPermission = async (data) => {
  // Directly sends POST request to Java Backend
  const response = await api.post('/apply', data);
  return response;
};

export const fetchRequests = async (filters = {}) => {
  // Directly fetches from Java Backend
  const response = await api.get('/requests');
  let permissions = response.data;
  
  if (filters.employeeId) {
    permissions = permissions.filter(p => String(p.employeeId).includes(String(filters.employeeId)));
  }
  
  if (filters.status) {
    permissions = permissions.filter(p => p.status === filters.status);
  }
  
  // Wrap to match standard Axios response signature
  return { data: permissions };
};

export default api;
