import { useEffect, useState } from "react";
import PageLoader from "../../components/PageLoader";
import { Typography, Paper, Stack } from "@mui/material";

import {
  Card,
  CardContent,
  LinearProgress,
  Box,
} from "@mui/material";

import Grid from "@mui/material/Grid";

import StatCard from "../../components/StatCard";

import StudentLayout from "../../layouts/StudentLayout";

import { getProfileStatus } from "../../api/studentProfileApi";
import { getAssessmentDashboard } from "../../api/studentAssessmentApi";

function StudentDashboard() {
	const [status, setStatus] = useState(null);
	const [assessmentDashboard, setAssessmentDashboard] = useState(null);
  const [loading, setLoading] = useState(true);
	useEffect(() => {
		loadStatus();
	}, []);
	
  const loadStatus = async () => {
    try {
      setLoading(true);

      const [profileRes, assessmentRes] = await Promise.all([
        getProfileStatus(),
        getAssessmentDashboard(),
      ]);

      setStatus(profileRes.data);
      setAssessmentDashboard(assessmentRes.data);
    } finally {
      setLoading(false);
    }
  };
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
        Student Dashboard
      </Typography>

      <Card sx={{ mb: 3 }}>
        <CardContent>
          <Typography variant="h5">
            Welcome,
            {localStorage.getItem("name")}
          </Typography>

          <Typography color="text.secondary">
            Track jobs, applications and assessments from one place.
          </Typography>
        </CardContent>
      </Card>
      <Grid container spacing={3}>
        <Card
          size={{ xs: 12, md: 3 }}
          sx={{
            height: "100%",
            transition: "0.3s",
            cursor: "pointer",

            "&:hover": {
              transform: "translateY(-4px)",
              boxShadow: 6,
            },
          }}
        >
          <CardContent>
            <Typography variant="h6" gutterBottom>
              Profile Completion
            </Typography>

            <Box
              sx={{
                display: "flex",
                alignItems: "flex-start",
                gap: 3,
              }}
            >
              <Box sx={{ flex: 1 }}>
                <Box
                  sx={{
                    display: "flex",
                    alignItems: "center",
                    gap: 2,
                  }}
                >
                  <Box
                    sx={{
                      flex: 1,
                      minWidth: 200,
                    }}
                  >
                    <LinearProgress
                      variant="determinate"
                      value={status?.completionPercentage ?? 0}
                    />
                  </Box>

                  <Typography>
                    {`${status?.completionPercentage ?? 0}%`}
                  </Typography>
                </Box>
              </Box>

              <Box sx={{ minWidth: 180 }}>
                <Typography variant="subtitle2" gutterBottom>
                  Missing Information
                </Typography>

                {status?.missingFields?.length ? (
                  status.missingFields.map((item) => (
                    <Typography key={item} variant="body2" color="error">
                      • {item}
                    </Typography>
                  ))
                ) : (
                  <Typography variant="body2" color="success.main">
                    Profile Complete
                  </Typography>
                )}
              </Box>
            </Box>
          </CardContent>
        </Card>

        <Grid size={{ xs: 12, md: 3 }}>
          <StatCard
            title="Total Assessments"
            value={assessmentDashboard?.totalAssessments}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 3 }}>
          <StatCard
            title="Upcoming Assessments"
            value={assessmentDashboard?.upcoming}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 3 }}>
          <StatCard
            title="Completed Assessments"
            value={assessmentDashboard?.completed}
          />
        </Grid>
      </Grid>
    </StudentLayout>
  );
}

export default StudentDashboard;
