import React, { useEffect } from 'react';

const Sidebar = () => {
    useEffect(() => {
        console.log('Sidebar is mounted');  // You can log directly inside useEffect
    }, []); // Empty dependency array ensures this runs once, after mount

    return (
        <div>
            {/* Sidebar content */}
        </div>
    );
};

export default Sidebar;
