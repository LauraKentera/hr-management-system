import React, { useEffect, useState } from 'react';

const buildTree = positions => {
    const map = {};
    positions.forEach(p => map[p.id] = { ...p, children: [] });
    const roots = [];
    positions.forEach(p => {
        if (p.parentId) map[p.parentId]?.children.push(map[p.id]);
        else roots.push(map[p.id]);
    });
    return roots;
};

const PositionTree = ({ onSelect }) => {
    const [positions, setPositions] = useState([]);

    useEffect(() => {
        // Simulating position fetching
        const dummyPositions = [
            { id: 1, name: 'Engineering', parentId: null },
            { id: 2, name: 'Product Manager', parentId: null },
            { id: 3, name: 'HR Manager', parentId: null },
            { id: 4, name: 'Frontend Developer', parentId: 1 },
            { id: 5, name: 'Backend Developer', parentId: 1 },
            { id: 6, name: 'Recruiter', parentId: 3 },
        ];
        const tree = buildTree(dummyPositions);
        setPositions(tree);
    }, []);

    const renderNode = node => (
        <li key={node.id}>
      <span onClick={() => onSelect(node)} style={{ cursor: 'pointer' }}>
        {node.name}
      </span>
            {node.children.length > 0 && (
                <ul>
                    {node.children.map(renderNode)}
                </ul>
            )}
        </li>
    );

    return (
        <div>
            <h3>Positions</h3>
            <ul>
                {positions.map(renderNode)}
            </ul>
        </div>
    );
};

export default PositionTree;
