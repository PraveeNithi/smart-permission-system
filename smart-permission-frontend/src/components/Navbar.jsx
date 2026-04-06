import { Link, useLocation } from 'react-router-dom';
import { FileText, Clock, Home, PlusCircle } from 'lucide-react';

export default function Navbar() {
  const location = useLocation();

  const navItems = [
    { name: 'Dashboard', path: '/', icon: Home },
    { name: 'Apply', path: '/apply', icon: PlusCircle },
    { name: 'Requests', path: '/requests', icon: FileText },
    { name: 'History', path: '/history', icon: Clock },
  ];

  return (
    <header className="bg-white border-b border-sand-200 sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-6 h-16 flex items-center justify-between">
        <div className="flex items-center gap-2 text-brown-800">
          <div className="bg-brown-600 text-white p-2 rounded-lg">
            <FileText size={20} />
          </div>
          <span className="font-bold text-lg tracking-tight">SmartPermit</span>
        </div>
        
        <nav className="flex gap-1">
          {navItems.map((item) => {
            const Icon = item.icon;
            const isActive = location.pathname === item.path;
            return (
              <Link
                key={item.path}
                to={item.path}
                className={`flex items-center gap-2 px-4 py-2 rounded-md text-sm font-medium transition-colors ${
                  isActive 
                    ? 'bg-sand-100 text-brown-900' 
                    : 'text-brown-600 hover:bg-sand-50 hover:text-brown-800'
                }`}
              >
                <Icon size={16} />
                {item.name}
              </Link>
            );
          })}
        </nav>
      </div>
    </header>
  );
}
