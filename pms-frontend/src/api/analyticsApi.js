import api from "./axios";

export const getDashboardAnalytics = async () => {
    const response = await api.get("/admin/analytics/dashboard");
    return response.data;
};

export const getDepartmentAnalytics = async () => {
    const response = await api.get("/admin/analytics/departments");
    return response.data;
};

export const getSelectedStudents = async () => {
    const response = await api.get("/admin/analytics/selected");
    return response.data;
};

export const getRejectedStudents = async () => {
    const response = await api.get("/admin/analytics/rejected");
    return response.data;
};
