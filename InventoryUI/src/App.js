import { Route, Routes } from 'react-router';
import './App.css';
import Login from './auth/login';
import SupplierDashboard from './features/supplier/supplier-dashboard';
import Logout from './auth/logout';
import Admin from './features/admin/admin-dashboard';
import ExecutiveDashboard from './features/executive/executive-dashboard';
import CustomerDashboard from './features/customer/customer-dashboard';
import Cart from './features/customer/components/Cart';
import ManagerDashboard from './features/manager/manager-dashboard';
import CustomerSignUp from './auth/signup';
import CustomerOrders from './features/customer/components/Orders'


function App() {
  return (
    <div>
      <Routes>
        <Route path='/' element={<Login />}></Route>
        <Route path='/admin' element={<Admin />}></Route>
        <Route path='/join' element={<CustomerSignUp />}></Route>
        <Route path='/supplier' element={<SupplierDashboard />}></Route>
        <Route path='/executive' element={<ExecutiveDashboard />}></Route>
        <Route path='/customer' element={<CustomerDashboard />}></Route>
        <Route path='/logout' element={<Logout />}></Route>
        <Route path='/customer/cart' element={<Cart />}></Route>
        <Route path='/manager' element={<ManagerDashboard />}></Route>
        <Route path='/customer/orders' element={<CustomerOrders />}></Route>
      </Routes>
    </div>
  );
}

export default App;