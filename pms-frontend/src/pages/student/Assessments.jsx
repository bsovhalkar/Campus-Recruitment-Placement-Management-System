import EmptyState from "../../components/EmptyState";
import PageLoader from "../../components/PageLoader";
import { useEffect, useState } from "react";
import StatusChip from "../../components/StatusChip";
import { Typography, Paper, Grid,Card,CardContent  } from "@mui/material";

import StudentLayout from "../../layouts/StudentLayout";
import DataTable from "../../components/DataTable";

import {
  getMyAssessments,
  getAssessmentDashboard,
} from "../../api/studentAssessmentApi";

function StudentAssessments() {
  const [assessments, setAssessments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [dashboard, setDashboard] = useState(null);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);

      const [assessmentRes, dashboardRes] = await Promise.all([
        getMyAssessments(),
        getAssessmentDashboard(),
      ]);

      setAssessments(assessmentRes.data);

      setDashboard(dashboardRes.data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const rows = assessments.map((assessment, index) => ({
    ...assessment,
    id: index + 1,
  }));

  const columns = [
    {
      field: "assessmentName",
      headerName: "Assessment",
      flex: 1,
    },
    {
      field: "assessmentType",
      headerName: "Type",
      width: 140,
    },
    {
      field: "jobName",
      headerName: "Job",
      flex: 1,
    },
    {
      field: "CompanyName",
      headerName: "Company",
      flex: 1,
    },
    {
      field: "assessmentDate",
      headerName: "Date",
      width: 140,
    },
    {
      field: "assessmentStatus",
      headerName: "Status",
      width: 180,

      renderCell: (params) => (
        <StatusChip status={params.row.assessmentStatus} />
      ),
    },
    {
      field: "score",
      headerName: "Score",
      width: 120,
    },
  ];
  if (loading) {
    return (
      <StudentLayout>
        <PageLoader />
      </StudentLayout>
    );
  }
  return (
    <StudentLayout>
      <Typography variant="h4" mb={3}>
        My Assessments
      </Typography>
      {dashboard && (
        <Grid container spacing={2} mb={3}>
          <Grid size={4}>
            <Paper sx={{ p: 2 }}>
              <Typography>Total</Typography>

              <Typography variant="h4">{dashboard.totalAssessments}</Typography>
            </Paper>
          </Grid>

          <Grid size={4}>
            <Paper sx={{ p: 2 }}>
              <Typography>Completed</Typography>

              <Typography variant="h4">{dashboard.completed}</Typography>
            </Paper>
          </Grid>

          <Grid size={4}>
            <Paper sx={{ p: 2 }}>
              <Typography>Upcoming</Typography>

              <Typography variant="h4">{dashboard.upcoming}</Typography>
            </Paper>
          </Grid>
        </Grid>
      )}
      ,
      {rows.length === 0 ? (
        <EmptyState message="You have not taken any assessments yet." />
      ) : (
        <Card>
          <CardContent>
            <DataTable rows={rows} columns={columns} />
          </CardContent>
        </Card>
      )}
    </StudentLayout>
  );
}

export default StudentAssessments;
