import api from "./axios";

export const uploadResume = async (file) => {
	const formData = new FormData();

	formData.append("file", file);

	const response = await api.post("/resumes/upload", formData, {
		headers: {
			"Content-Type": "multipart/form-data",
		},
	});

	return response.data;
};

export const getResume = async () => {
	const response = await api.get("/resumes/me");

	return response.data;
};

export const getResumeStatus = async () => {
	const response = await api.get("/resumes/status");

	return response.data;
};
