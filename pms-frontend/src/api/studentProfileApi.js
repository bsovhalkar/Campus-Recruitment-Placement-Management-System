import api from "./axios";

export const createStudentProfile = async (data) => {
	const response = await api.post("/students", data);

	return response.data;
};

export const getMyProfile = async () => {
	const response = await api.get("/students/me");

	return response.data;
};

// export const createStudentProfile = async (data) => {
//   const response = await api.post("/students", data);

//   return response.data;
// };
export const updateMyProfile = async (data) => {
	const response = await api.put("/students/me", data);

	return response.data;
};

export const haveStudentProfile = async () => {
  const response = await api.get("/students/have-student-profile");

  return response.data;
};

export const getProfileStatus = async () => {
	const response = await api.get("/students/profile-status");

	return response.data;
};
