import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

// Xóa token rác nếu bị lưu sai từ trước
const staleToken = localStorage.getItem('accessToken');
if (!staleToken || staleToken === 'undefined' || staleToken === 'null') {
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
