import CsvUploadDialog from "../../components/CsvUploadDialog";
import UploadScoresDialog from "../../components/UploadScoresDialog";
import { useState, useEffect } from "react";
import StatusChip from "../../components/StatusChip";
import { Typography, Button, Stack, Card, CardContent } from "@mui/material";

import AddIcon from "@mui/icons-material/Add";

import AdminLayout from "../../layouts/AdminLayout";

import DataTable from "../../components/DataTable";

import AssessmentDialog from "../../components/AssessmentDialog";

import {
  getAssessments,
  createAssessment,
  deleteAssessment,
  getAssessmentStudents,
  uploadAssessmentScores,
  downloadTemplate,
  uploadScoresCsv,
} from "../../api/assessmentApi";

import { getJobs } from "../../api/jobApi";

function Assessments() {
  const [assessments, setAssessments] = useState([]);

  const [jobs, setJobs] = useState([]);

  const [assessment, setAssessment] = useState({});

  const [open, setOpen] = useState(false);

  const [selectedFile, setSelectedFile] = useState(null);

  const [scoreDialogOpen, setScoreDialogOpen] = useState(false);

  const [students, setStudents] = useState([]);

  const [selectedAssessmentId, setSelectedAssessmentId] = useState(null);

  
    const loadData = async () => {
      const [assessmentRes, jobsRes] = await Promise.all([
        getAssessments(),
        getJobs(),
      ]);
  
      setAssessments(assessmentRes.data);
  
      setJobs(jobsRes.data);
    };

  useEffect(() => {
    loadData();
  }, []);

  const handleSave = async () => {
    await createAssessment(assessment);

    setOpen(false);

    loadData();
  };

  const handleDelete = async (id) => {
    await deleteAssessment(id);

    loadData();
  };

  const handleUploadScores = async (assessmentId) => {
    const response = await getAssessmentStudents(assessmentId);

    setStudents(response.data);

    setSelectedAssessmentId(assessmentId);

    setScoreDialogOpen(true);
  };

  const handleSaveScores = async () => {
    const payload = students.map((student) => ({
      studentId: student.studentId,
      score: Number(student.score),
    }));

    await uploadAssessmentScores(selectedAssessmentId, payload);

    setScoreDialogOpen(false);
  };

  const handleDownloadTemplate = async (assessmentId) => {
    const blob = await downloadTemplate(assessmentId);

    const url = window.URL.createObjectURL(blob);

    const link = document.createElement("a");

    link.href = url;

    link.download = "scores_template.csv";

    link.click();

    window.URL.revokeObjectURL(url);
  };

  const handleCsvUpload = async (assessmentId, file) => {
    if (!file) return;

    await uploadScoresCsv(assessmentId, file);

    alert("Scores uploaded successfully");
  };

  const [csvOpen, setCsvOpen] = useState(false);

  const [selectedAssessment, setSelectedAssessment] = useState(null);

  const rows = assessments.map((assessment) => ({
    ...assessment,
    id: assessment.assessmentId,
  }));

  const columns = [
    {
      field: "assessmentId",
      headerName: "ID",
      width: 80,
    },
    {
      field: "assessmentName",
      headerName: "Name",
      width: 220,
    },
    {
      field: "assessmentType",
      headerName: "Type",
      width: 140,
    },
    {
      field: "jobTitle",
      headerName: "Job",
      width: 220,
    },
    {
      field: "assessmentDate",
      headerName: "Date",
      width: 140,
    },
    {
      field: "status",
      headerName: "Status",
      width: 220,

      renderCell: (params) => <StatusChip status={params.row.status} />,
    },
    {
      field: "actions",
      headerName: "Actions",
      width: 450,

      renderCell: (params) => (
        <Stack direction="row" spacing={1}>
          <Button
            size="small"
            variant="contained"
            onClick={() => handleUploadScores(params.row.assessmentId)}
          >
            Scores
          </Button>

          <Button
            size="small"
            variant="outlined"
            onClick={() => handleDownloadTemplate(params.row.assessmentId)}
          >
            Template
          </Button>

          <Button
            size="small"
            variant="outlined"
            onClick={() => {
              setSelectedAssessment(params.row.assessmentId);

              setCsvOpen(true);
            }}
          >
            CSV
          </Button>

          <Button
            color="error"
            size="small"
            variant="outlined"
            onClick={() => handleDelete(params.row.assessmentId)}
          >
            Delete
          </Button>
        </Stack>
      ),
    },
  ];

  return (
    <AdminLayout>
      <Stack direction="row" justifycontent="space-between" mb={3}>
        <Typography variant="h4">Assessments</Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          sx={{ ml: 10 }}
          onClick={() => setOpen(true)}
        >
          Schedule
        </Button>
      </Stack>

      <Card>
        <CardContent>
          <DataTable rows={rows} columns={columns} />
        </CardContent>
      </Card>

      <AssessmentDialog
        open={open}
        onClose={() => setOpen(false)}
        assessment={assessment}
        setAssessment={setAssessment}
        jobs={jobs}
        onSave={handleSave}
      />
      <UploadScoresDialog
        open={scoreDialogOpen}
        onClose={() => setScoreDialogOpen(false)}
        students={students}
        setStudents={setStudents}
        onSave={handleSaveScores}
      />
      <CsvUploadDialog
        open={csvOpen}
        onClose={() => setCsvOpen(false)}
        onUpload={async (file) => {
          await handleCsvUpload(selectedAssessment, file);

          setCsvOpen(false);
        }}
      />
    </AdminLayout>
  );
}

export default Assessments;
