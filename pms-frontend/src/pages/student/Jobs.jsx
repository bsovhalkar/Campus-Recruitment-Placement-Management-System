import PageLoader from "../../components/PageLoader";
import { useEffect, useState } from "react";
import ConfirmDialog from "../../components/ConfirmDialog";

import {
  Typography,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  Stack,
  Card,
  CardContent,
  Chip,
} from "@mui/material";

import DataTable from "../../components/DataTable";

import {
  applyJob,
  getApplicationStatus,
} from "../../api/applicationStudentApi";

import StudentLayout from "../../layouts/StudentLayout";
import EmptyState from "../../components/EmptyState";

import { getJobs, getJobById, checkEligibility } from "../../api/studentJobApi";

function StudentJobs() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  const [open, setOpen] = useState(false);
  const [job, setJob] = useState(null);

  const [eligible, setEligible] = useState(null);

  const [applyDialog, setApplyDialog] = useState(false);
  const [selectedJobId, setSelectedJobId] = useState(null);

  useEffect(() => {
    loadJobs();
  }, []);

  // const loadJobs = async () => {
  //   try {
  //     setLoading(true);

  //     const response = await getJobs();

  //     const jobsWithStatus = await Promise.all(
  //       response.data.map(async (job) => {
  //         try {
  //           const [statusRes, eligibilityRes] = await Promise.all([
  //             getApplicationStatus(job.jobId),
  //             checkEligibility(job.jobId),
  //           ]);

  //           return {
  //             ...job,
  //             applicationStatus: statusRes?.data?.data || "NOT_APPLIED",
  //             eligible: eligibilityRes?.data ?? false,
  //           };
  //         } catch (error) {
  //           return {
  //             ...job,
  //             applicationStatus: "NOT_APPLIED",
  //             eligible: false,
  //           };
  //         }
  //       }),
  //     );

  //     setJobs(jobsWithStatus);
  //   } catch (error) {
  //     console.error(error);
  //   } finally {
  //     setLoading(false);
  //   }
  // };
  const loadJobs = async () => {
    try {
      setLoading(true);

      const response = await getJobs();

      const jobsWithStatus = await Promise.all(
        response.data.map(async (job) => {
          try {
            const statusRes = await getApplicationStatus(job.jobId);

            return {
              ...job,
              applicationStatus: statusRes.data || "NOT_APPLIED",
            };
          } catch (error) {
            console.error(error);

            return {
              ...job,
              applicationStatus: "NOT_APPLIED",
            };
          }
        }),
      );

      setJobs(jobsWithStatus);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };







  
  const handleView = async (jobId) => {
    try {
      const [jobRes, eligibilityRes] = await Promise.all([
        getJobById(jobId),
        checkEligibility(jobId),
      ]);

      setJob(jobRes.data);
      setEligible(eligibilityRes.data);

      setOpen(true);
    } catch (error) {
      console.error(error);
    }
  };

  const handleApply = async (jobId) => {
    try {
      await applyJob(jobId);

      await loadJobs();

      alert("Applied Successfully");
    } catch (error) {
      console.error(error);
      alert("Failed to Apply");
    }
  };

  const rows = jobs.map((job) => ({
    ...job,
    id: job.jobId,
  }));

  const columns = [
    {
      field: "title",
      headerName: "Title",
      flex: 1,
    },
    {
      field: "companyName",
      headerName: "Company",
      flex: 1,
    },
    {
      field: "packageOffered",
      headerName: "Package",
      width: 120,
    },
    {
      field: "location",
      headerName: "Location",
      width: 140,
    },
    {
      field: "applicationDeadline",
      headerName: "Deadline",
      width: 140,
    },
    {
      field: "actions",
      headerName: "Actions",
      width: 320,

      renderCell: (params) => {
  const status =
    params.row.applicationStatus || "NOT_APPLIED";

  return (
    <>
      <Button
        size="small"
        variant="outlined"
        onClick={() => handleView(params.row.jobId)}
      >
        View
      </Button>

      {(status === "NOT_APPLIED" ||
        status === "WITHDRAWN") ? (
        <Button
          size="small"
          variant="contained"
          color={
            status === "WITHDRAWN"
              ? "success"
              : "primary"
          }
          sx={{ ml: 1 }}
          onClick={() => {
            setSelectedJobId(
              params.row.jobId
            );
            setApplyDialog(true);
          }}
        >
          {status === "WITHDRAWN"
            ? "Apply Again"
            : "Apply"}
        </Button>
      ) : (
        <Button
          size="small"
          disabled
          sx={{ ml: 1 }}
        >
          {status.replaceAll("_", " ")}
        </Button>
      )}
    </>
  );
}
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
        Available Jobs
      </Typography>

      {jobs.length === 0 ? (
        <EmptyState message="No jobs available at the moment." />
      ) : (
        <Card>
          <CardContent>
            <DataTable rows={rows} columns={columns} />
          </CardContent>
        </Card>
      )}

      <Dialog
        open={open}
        onClose={() => setOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>Job Details</DialogTitle>

        <DialogContent>
          {job && (
            <Stack spacing={2} mt={1}>
              <Typography>
                <strong>Title:</strong> {job.title}
              </Typography>

              <Typography>
                <strong>Company:</strong> {job.companyName}
              </Typography>

              <Typography>
                <strong>Package:</strong> {job.packageOffered} LPA
              </Typography>

              <Typography>
                <strong>Minimum CGPA:</strong> {job.minimumCgpa}
              </Typography>

              <Typography>
                <strong>Vacancies:</strong> {job.vacancies}
              </Typography>

              <Typography>
                <strong>Location:</strong> {job.location}
              </Typography>

              <Typography>
                <strong>Eligible Departments:</strong> {job.eligibleDepartments}
              </Typography>

              <Typography>
                <strong>Description:</strong> {job.description}
              </Typography>

              <Typography color={eligible ? "success.main" : "error.main"}>
                {eligible ? "Eligible" : "Not Eligible"}
              </Typography>
            </Stack>
          )}
        </DialogContent>
      </Dialog>

      <ConfirmDialog
        open={applyDialog}
        title="Apply Job"
        message="Do you want to apply for this job?"
        onCancel={() => setApplyDialog(false)}
        onConfirm={() => {
          setApplyDialog(false);

          handleApply(selectedJobId);
        }}
      />
    </StudentLayout>
  );
}

export default StudentJobs;