import api from "./axios";

export const getStudents = async () => {
	const response = await api.get("/admin/students");

	return response.data;
};

export const getStudentById = async (id) => {
	const response = await api.get(`/admin/students/${id}`);

	return response.data;
};

export const updateStudent = async (id, data) => {
	const response = await api.put(`/admin/students/${id}`, data);

	return response.data;
};
