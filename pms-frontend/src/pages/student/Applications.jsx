import EmptyState from "../../components/EmptyState";
import PageLoader from "../../components/PageLoader";
import StatusChip from "../../components/StatusChip";
import { useEffect, useState } from "react";
import ConfirmDialog from "../../components/ConfirmDialog";
import { Typography, Button, Card, CardContent, } from "@mui/material";


import StudentLayout from "../../layouts/StudentLayout";

import DataTable from "../../components/DataTable";

import {
  getMyApplications,
  withdrawApplication,
} from "../../api/applicationStudentApi";

function StudentApplications() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadApplications();
  }, []);

  const loadApplications = async () => {
    try {
      setLoading(true);

      const response = await getMyApplications();

      setApplications(response.data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };
  const [withdrawDialog, setWithdrawDialog] = useState(false);

  const [selectedApplication, setSelectedApplication] = useState(null);

  const handleWithdraw = async (applicationId) => {
    await withdrawApplication(applicationId);

    loadApplications();
  };

  const rows = applications.map((application) => ({
    ...application,
    id: application.applicationId,
  }));

  const columns = [
    {
      field: "jobTitle",
      headerName: "Job",
      flex: 1,
    },
    {
      field: "companyName",
      headerName: "Company",
      flex: 1,
    },
    {
      field: "status",
      headerName: "Status",
      width: 220,

      renderCell: (params) => <StatusChip status={params.row.status} />,
    },
    {
      field: "appliedAt",
      headerName: "Applied At",
      width: 220,
    },
    {
      field: "actions",
      headerName: "Actions",
      width: 150,

      renderCell: (params) => (
        <Button
          disabled={["SELECTED", "REJECTED", "WITHDRAWN"].includes(
            params.row.status,
          )}
          color="error"
          size="small"
          variant="outlined"
          onClick={() => {
            setSelectedApplication(params.row.applicationId);

            setWithdrawDialog(true);
          }}
        >
          Withdraw
        </Button>
      ),
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
    <>
      <StudentLayout>
        <Typography variant="h4" mb={3}>
          My Applications
        </Typography>
        {rows.length === 0 ? (
          <EmptyState message="You have not applied to any jobs yet." />
        ) : (
          <Card>
            <CardContent>
              <DataTable rows={rows} columns={columns} />
            </CardContent>
          </Card>
        )}
      </StudentLayout>
      <ConfirmDialog
        open={withdrawDialog}
        title="Withdraw Application"
        message="Are you sure you want to withdraw this application?"
        onCancel={() => setWithdrawDialog(false)}
        onConfirm={() => {
          setWithdrawDialog(false);

          handleWithdraw(selectedApplication);
        }}
      />
    </>
  );
}

export default StudentApplications;
