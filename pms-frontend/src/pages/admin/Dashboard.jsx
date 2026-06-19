import { useEffect, useState } from "react";

import { Grid, Typography, CircularProgress, Box ,Card,CardContent} from "@mui/material";

import {
	BarChart,
	Bar,
	XAxis,
	YAxis,
	Tooltip,
	ResponsiveContainer,
	PieChart,
	Pie,
	Cell,
	Legend,
} from "recharts";

import AdminLayout from "../../layouts/AdminLayout";
import StatCard from "../../components/StatCard";

import {
	getDashboardAnalytics,
	getDepartmentAnalytics,
	getSelectedStudents,
	getRejectedStudents,
} from "../../api/analyticsApi";

function Dashboard() {
	const [loading, setLoading] = useState(true);

	const [dashboard, setDashboard] = useState({});
	const [departments, setDepartments] = useState([]);
	const [selectedCount, setSelectedCount] = useState(0);
	const [rejectedCount, setRejectedCount] = useState(0);

	useEffect(() => {
		const loadData = async () => {
			try {
				const [dashboardRes, departmentRes, selectedRes, rejectedRes] =
					await Promise.all([
						getDashboardAnalytics(),
						getDepartmentAnalytics(),
						getSelectedStudents(),
						getRejectedStudents(),
					]);

				setDashboard(dashboardRes.data);
				setDepartments(departmentRes.data);

				setSelectedCount(selectedRes.data.length);
				setRejectedCount(rejectedRes.data.length);
			} catch (error) {
				console.error(error);
			} finally {
				setLoading(false);
			}
		};

		loadData();
	}, []);

	if (loading) {
		return (
			<AdminLayout>
				<Box sx={{ textAlign: "center" }}>
					<CircularProgress />
				</Box>
			</AdminLayout>
		);
	}

	const pieData = [
		{
			name: "Selected",
			value: selectedCount,
		},
		{
			name: "Rejected",
			value: rejectedCount,
		},
	];

	return (
    <AdminLayout>
      <Typography variant="h4" gutterBottom>
        Dashboard
      </Typography>

      <Card sx={{ mb: 3 }}>
        <CardContent>
          <Typography variant="h5">
            Welcome, {localStorage.getItem("name")}
          </Typography>

          <Typography color="text.secondary">
            Welcome, Admin User Manage students, jobs, companies and
            assessments.
          </Typography>
        </CardContent>
      </Card>

      <Grid container spacing={3}>
        <Grid item xs={12} md={3}>
          <StatCard title="Students" value={dashboard.totalStudents} />
        </Grid>

        <Grid item xs={12} md={3}>
          <StatCard title="Companies" value={dashboard.totalCompanies} />
        </Grid>

        <Grid item xs={12} md={3}>
          <StatCard title="Jobs" value={dashboard.totalJobs} />
        </Grid>

        <Grid item xs={12} md={3}>
          <StatCard title="Applications" value={dashboard.totalApplications} />
        </Grid>

        <Grid item xs={12} md={4}>
          <StatCard title="Assessments" value={dashboard.totalAssessments} />
        </Grid>

        <Grid item xs={12} md={4}>
          <StatCard title="Selected" value={dashboard.selectedStudents} />
        </Grid>

        <Grid item xs={12} md={4}>
          <StatCard
            title="Placement %"
            value={`${dashboard.placementPercentage}%`}
          />
        </Grid>
      </Grid>

      <Box
        sx={{
          mt: 8,
          mb: 4,
        }}
      >
        <Typography variant="h5" gutterBottom>
          Department Placement %
        </Typography>

        <ResponsiveContainer width="60%" height={350}>
          <BarChart data={departments}>
            <XAxis dataKey="department" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="placementPercentage" />
          </BarChart>
        </ResponsiveContainer>
      </Box>

      <Box mt={6}>
        <Typography variant="h5" gutterBottom>
          Selected vs Rejected
        </Typography>

        <ResponsiveContainer width="100%" height={350}>
          <PieChart>
            <Pie
              data={pieData}
              dataKey="value"
              nameKey="name"
              outerRadius={120}
              label
            >
              <Cell />
              <Cell />
            </Pie>

            <Legend />
            <Tooltip />
          </PieChart>
        </ResponsiveContainer>
      </Box>
    </AdminLayout>
  );
}

export default Dashboard;
