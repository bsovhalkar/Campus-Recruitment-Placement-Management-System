import api from "./axios";

export const getAssessments = async () => {
	const response = await api.get("/admin/assessments");

	return response.data;
};

export const getAssessmentsByJob = async (jobId) => {
	const response = await api.get(`/admin/assessments/job/${jobId}`);

	return response.data;
};
export const getAssessmentStudents = async (assessmentId) => {
  const response = await api.get(`/admin/assessments/${assessmentId}/students`);

  return response.data;
};

export const uploadAssessmentScores = async (assessmentId, scores) => {
  const response = await api.post(
    `/admin/assessments/${assessmentId}/scores`,
    scores,
  );

  return response.data;
};

export const downloadTemplate = async (assessmentId) => {
  const response = await api.get(
    `/admin/assessments/${assessmentId}/template`,
    {
      responseType: "blob",
    },
  );

  return response.data;
};

export const uploadScoresCsv = async (assessmentId, file) => {
  const formData = new FormData();

  formData.append("file", file);

  const response = await api.post(
    `/admin/assessments/${assessmentId}/scores/csv`,
    formData,
    {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    },
  );

  return response.data;
};

export const createAssessment = async (data) => {
	const response = await api.post("/admin/assessments", data);

	return response.data;
};

export const deleteAssessment = async (id) => {
	const response = await api.delete(`/admin/assessments/${id}`);

	return response.data;
};
