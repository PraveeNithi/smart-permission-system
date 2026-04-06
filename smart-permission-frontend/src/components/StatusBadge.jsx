import React from 'react';

export default function StatusBadge({ status }) {
  const styles = {
    APPROVED: 'bg-emerald-100 text-emerald-800 border-emerald-200',
    REJECTED: 'bg-red-100 text-red-800 border-red-200',
    REVIEW: 'bg-amber-100 text-amber-800 border-amber-200'
  };

  const defaultStyle = 'bg-gray-100 text-gray-800 border-gray-200';

  return (
    <span className={`px-2.5 py-1 rounded-full text-xs font-semibold border ${styles[status] || defaultStyle}`}>
      {status}
    </span>
  );
}
