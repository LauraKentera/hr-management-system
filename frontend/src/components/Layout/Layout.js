import React from 'react';
import Sidebar from '../Sidebar/Sidebar';
import Topbar from '../Topbar/Topbar';

const Layout = ({ children }) => {
    return (
        <div>
            <Topbar />
            <Sidebar />
            <main>{children}</main>
        </div>
    );
};

export default Layout;
