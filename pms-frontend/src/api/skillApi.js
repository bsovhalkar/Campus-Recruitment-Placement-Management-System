import api from "./axios";

export const getSkills = async () => {
	const response = await api.get("/skills/me");

	return response.data;
};

export const addSkill = async (data) => {
	const response = await api.post("/skills", data);

	return response.data;
};

export const updateSkill = async (id, data) => {
	const response = await api.put(`/skills/${id}`, data);

	return response.data;
};

export const deleteSkill = async (id) => {
	const response = await api.delete(`/skills/${id}`);

	return response.data;
};

export const getSkillStatus = async () => {
	const response = await api.get("/skills/status");

	return response.data;
};
