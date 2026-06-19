import api from "./axios";

export const getCompanies = async () => {
	const response = await api.get("/admin/companies");
	return response.data;
};

export const getCompanyById = async (id) => {
	const response = await api.get(`/admin/companies/${id}`);
	return response.data;
};

export const createCompany = async (data) => {
	const response = await api.post("/admin/companies", data);
	return response.data;
};

export const updateCompany = async (id, data) => {
	const response = await api.put(`/admin/companies/${id}`, data);
	return response.data;
};
