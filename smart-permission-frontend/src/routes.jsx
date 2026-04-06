import { Routes, Route } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import ApplyPermission from './pages/ApplyPermission';
import ViewRequests from './pages/ViewRequests';
import History from './pages/History';

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />} />
      <Route path="/apply" element={<ApplyPermission />} />
      <Route path="/requests" element={<ViewRequests />} />
      <Route path="/history" element={<History />} />
    </Routes>
  );
}
