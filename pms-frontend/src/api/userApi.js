import api from "./axios";

export const getCurrentUser = async () => {
	const response = await api.get("/users/me");

	return response.data;
};

export const updateCurrentUser = async (data) => {
	const response = await api.put("/users/me", data);

	return response.data;
};

export const deleteCurrentUser = async () => {
	const response = await api.delete("/users/me");

	return response.data;
};
