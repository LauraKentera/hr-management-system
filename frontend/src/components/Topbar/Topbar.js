import React, { useEffect } from 'react';

const Topbar = () => {
    // You can log the mount here instead of using useRef
    useEffect(() => {
        console.log('Topbar is mounted');
    }, []); // This will run once when the component mounts

    return (
        <header>
            {/* Topbar content */}
        </header>
    );
};

export default Topbar;
