import axios from 'axios';
import ApiEndpoints from '../api/ApiEndpoints'; 

const contractService = {
  // Fetch all contracts
  getAllContracts: async () => {
    try {
      const response = await axios.get(ApiEndpoints.contract.getAll);
      return response.data;
    } catch (error) {
      throw new Error('Error fetching contracts: ' + error.message);
    }
  },

  // Fetch a single contract by ID
  getContractById: async (id) => {
    try {
      const response = await axios.get(ApiEndpoints.contract.getById(id));
      return response.data;
    } catch (error) {
      throw new Error('Error fetching contract: ' + error.message);
    }
  },

  // Create a new contract
  createContract: async (contractData) => {
    try {
      const response = await axios.post(ApiEndpoints.contract.create, contractData);
      return response.data;
    } catch (error) {
      throw new Error('Error creating contract: ' + error.message);
    }
  },

  // Update an existing contract
  updateContract: async (id, contractData) => {
    try {
      const response = await axios.put(ApiEndpoints.contract.update(id), contractData);
      return response.data;
    } catch (error) {
      throw new Error('Error updating contract: ' + error.message);
    }
  },

  // Delete a contract by ID
  deleteContract: async (id) => {
    try {
      await axios.delete(ApiEndpoints.contract.delete(id));
    } catch (error) {
      throw new Error('Error deleting contract: ' + error.message);
    }
  },

  // Fetch annexes for a specific contract
  getContractAnnexes: async (id) => {
    try {
      const response = await axios.get(ApiEndpoints.contract.getAnnexesByContractId(id));
      return response.data;
    } catch (error) {
      throw new Error('Error fetching annexes: ' + error.message);
    }
  }
};

export default contractService;
