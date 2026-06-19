import { useEffect, useState } from "react";
import StatusChip from "../../components/StatusChip";
import { Typography, MenuItem, Select ,Card, CardContent,} from "@mui/material";

import AdminLayout from "../../layouts/AdminLayout";
import DataTable from "../../components/DataTable";

import {
  getApplications,
  updateApplicationStatus,
} from "../../api/applicationApi";

import { APPLICATION_STATUSES } from "../../constants/applicationStatus";

function Applications() {
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    loadApplications();
  }, []);

  const loadApplications = async () => {
    try {
      const response = await getApplications();

      setApplications(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleStatusChange = async (applicationId, status) => {
    try {
      await updateApplicationStatus(applicationId, status);

      loadApplications();
    } catch (error) {
      console.error(error);
    }
  };

  const rows = applications.map((application) => ({
    ...application,
    id: application.applicationId,
  }));

  const columns = [
    {
      field: "applicationId",
      headerName: "ID",
      width: 80,
    },
    {
      field: "studentName",
      headerName: "Student",
      width: 180,
    },
    {
      field: "jobTitle",
      headerName: "Job",
      width: 180,
    },
    {
      field: "companyName",
      headerName: "Company",
      width: 180,
    },
    {
      field: "appliedAt",
      headerName: "Applied At",
      width: 220,
    },
    {
      field: "status",
      headerName: "Status",
      width: 260,

      renderCell: (params) => {
        const getStatusStyle = (status) => {
          switch (status) {
            case "APPLIED":
              return {
                bgcolor: "#e3f2fd",
                color: "#1565c0",
              };

            case "SHORTLISTED_FOR_TEST":
              return {
                bgcolor: "#fff3e0",
                color: "#ef6c00",
              };

            case "SELECTED":
              return {
                bgcolor: "#e8f5e9",
                color: "#2e7d32",
              };

            case "REJECTED":
              return {
                bgcolor: "#ffebee",
                color: "#c62828",
              };

            case "WITHDRAWN":
              return {
                bgcolor: "#f5f5f5",
                color: "#616161",
              };

            default:
              return {};
          }
        };

        return (
          <Select
            size="small"
            value={params.value}
            onChange={(e) =>
              handleStatusChange(params.row.applicationId, e.target.value)
            }
            sx={{
              width: 240,
              fontWeight: 600,
              borderRadius: 2,

              ...getStatusStyle(params.value),

              "& .MuiOutlinedInput-notchedOutline": {
                border: "none",
              },
            }}
          >
            {APPLICATION_STATUSES.map((status) => (
              <MenuItem key={status} value={status}>
                {status}
              </MenuItem>
            ))}
          </Select>
        );
      },
    },
  ];

  return (
    <AdminLayout>
      <Typography variant="h4" mb={3}>
        Applications
      </Typography>

      <Card>
        <CardContent>
          <DataTable rows={rows} columns={columns} />
        </CardContent>
      </Card>
    </AdminLayout>
  );
}

export default Applications;
