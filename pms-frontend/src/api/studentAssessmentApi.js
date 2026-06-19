import api from "./axios";

export const getMyAssessments = async () => {
	const response = await api.get("/assessments/me");

	return response.data;
};

export const getUpcomingAssessments = async () => {
	const response = await api.get("/assessments/upcoming");

	return response.data;
};

export const getAssessmentDashboard = async () => {
	const response = await api.get("/assessments/dashboard");

	return response.data;
};

export const getAssessmentDetails = async (id) => {
	const response = await api.get(`/assessments/${id}`);

	return response.data;
};

export const getAssessmentScore = async (id) => {
	const response = await api.get(`/assessments/${id}/score`);

	return response.data;
};
