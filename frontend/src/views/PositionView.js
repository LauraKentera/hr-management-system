import React, { useState, useEffect } from 'react';
import PositionTree from '../components/PositionTree';
import { getPositions } from '../services/positionService';

const PositionView = () => {
  const [positions, setPositions] = useState([]);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    (async () => {
      const { data } = await getPositions();
      setPositions(data);
    })();
  }, []);

  return (
    <div>
      <h2>Positions</h2>
      <PositionTree positions={positions} onSelect={setSelected} />
      {selected && (
        <div className="details">
          <h4>Details</h4>
          <p><strong>ID:</strong> {selected.id}</p>
          <p><strong>Name:</strong> {selected.name}</p>
          <p><strong>Parent ID:</strong> {selected.parentId}</p>
        </div>
      )}
    </div>
  );
};

export default PositionView;
