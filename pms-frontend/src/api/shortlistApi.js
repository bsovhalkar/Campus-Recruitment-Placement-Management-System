import api from "./axios";

export const generateShortlist = async (jobId) => {
	const response = await api.post(`/admin/shortlist/generate/${jobId}`);

	return response.data;
};

export const getShortlistByJob = async (jobId) => {
	const response = await api.get(`/admin/shortlist/${jobId}`);

	return response.data;
};
