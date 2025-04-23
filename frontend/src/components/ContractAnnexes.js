import React, { useState, useEffect } from 'react';
import { getAnnexes, uploadAnnex } from '../services/contractService';

const ContractAnnexes = ({ contractId }) => {
  const [annexes, setAnnexes] = useState([]);
  const [file, setFile] = useState(null);

  useEffect(() => {
    if (contractId) (async () => {
      const { data } = await getAnnexes(contractId);
      setAnnexes(data);
    })();
  }, [contractId]);

  const handleUpload = async () => {
    if (!file) return;
    await uploadAnnex(contractId, file);
    setFile(null);
    const { data } = await getAnnexes(contractId);
    setAnnexes(data);
  };

  return (
    <div>
      <h4>Annexes</h4>
      <ul>
        {annexes.map(a => (
          <li key={a.id}>
            <a href={a.url} target="_blank" rel="noopener noreferrer">
              {a.filename}
            </a>
          </li>
        ))}
      </ul>
      <input type="file" onChange={e => setFile(e.target.files[0])} />
      <button onClick={handleUpload} disabled={!file}>Upload</button>
    </div>
  );
};

export default ContractAnnexes;
