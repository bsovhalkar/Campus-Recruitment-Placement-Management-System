import api from "./axios";

export const applyJob = async (jobId) => {
	const response = await api.post(`/jobs/applications/${jobId}/apply`);

	return response.data;
};



export const withdrawApplication = async (applicationId) => {
	const response = await api.put(
		`/jobs/applications/${applicationId}/withdraw`,
	);

	return response.data;
};

export const getMyApplications = async () => {
	const response = await api.get("/jobs/applications/me");

	return response.data;
};

export const getApplicationStatus = async (jobId) => {
	const response = await api.get(`/jobs/applications/${jobId}/status`);

	return response.data;
};

export const getApplicationDetails = async (applicationId) => {
	const response = await api.get(`/jobs/applications/details/${applicationId}`);

	return response.data;
};
