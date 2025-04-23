import React, { useState } from 'react';
import ContractList from '../components/ContractList';
import ContractFormModal from '../components/ContractFormModal';
import ContractAnnexes from '../components/ContractAnnexes';

const ContractsView = () => {
  const [selected, setSelected] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [refreshKey, setRefreshKey] = useState(0);

  const onFormSuccess = () => setRefreshKey(k => k + 1);

  return (
    <div>
      <h2>Contracts</h2>
      <button onClick={() => { setSelected(null); setShowForm(true); }}>New Contract</button>
      <button onClick={() => selected && setShowForm(true)} disabled={!selected}>
        Edit Contract
      </button>

      <ContractList key={refreshKey} onSelect={setSelected} />

      {selected && <ContractAnnexes contractId={selected.id} />}

      <ContractFormModal
        show={showForm}
        onClose={() => setShowForm(false)}
        onSuccess={onFormSuccess}
        initialData={selected}
      />
    </div>
  );
};

export default ContractsView;
