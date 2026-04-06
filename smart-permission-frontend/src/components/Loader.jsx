import React from 'react';

export default function Loader({ text = 'Loading...' }) {
  return (
    <div className="flex flex-col items-center justify-center p-8 text-brown-500">
      <div className="w-8 h-8 relative mb-3">
        <div className="absolute inset-0 rounded-full border-2 border-sand-200"></div>
        <div className="absolute inset-0 rounded-full border-2 border-brown-600 border-t-transparent animate-spin"></div>
      </div>
      <p className="text-sm font-medium">{text}</p>
    </div>
  );
}
