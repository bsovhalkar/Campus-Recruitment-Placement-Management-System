import api from "./axios";

export const getApplications = async () => {
	const response = await api.get("/admin/applications");
	return response.data;
};

export const getApplicationById = async (id) => {
	const response = await api.get(`/admin/applications/${id}`);
	return response.data;
};

export const getApplicationsByJob = async (jobId) => {
	const response = await api.get(`/admin/applications/jobs/${jobId}`);
	return response.data;
};

export const updateApplicationStatus = async (id, status) => {
	const response = await api.put(
		`/admin/applications/${id}/status?status=${status}`,
	);

	return response.data;
};
