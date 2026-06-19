import api from "./axios";

export const getJobs = async () => {
	const response = await api.get("/admin/jobs");
	return response.data;
};

export const getJobById = async (id) => {
	const response = await api.get(`/admin/jobs/${id}`);
	return response.data;
};

export const createJob = async (data) => {
	const response = await api.post("/admin/jobs", data);
	return response.data;
};

export const updateJob = async (id, data) => {
	const response = await api.put(`/admin/jobs/${id}`, data);
	return response.data;
};

export const deleteJob = async (id) => {
	const response = await api.delete(`/admin/jobs/${id}`);
	return response.data;
};

export const getJobStats = async (id) => {
	const response = await api.get(`/admin/jobs/${id}/stats`);
	return response.data;
};
