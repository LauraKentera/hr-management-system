import React from 'react';

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

const PositionTree = ({ positions, onSelect }) => {
  const tree = buildTree(positions);

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
        {tree.map(renderNode)}
      </ul>
    </div>
  );
};

export default PositionTree;
