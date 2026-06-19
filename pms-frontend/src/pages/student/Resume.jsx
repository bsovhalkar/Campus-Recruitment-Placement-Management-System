import PageLoader from "../../components/PageLoader";
import { useEffect, useState } from "react";
import ConfirmDialog from "../../components/ConfirmDialog";
import { Typography, Button, Paper, Stack } from "@mui/material";

import StudentLayout from "../../layouts/StudentLayout";

import { getResume, getResumeStatus, uploadResume } from "../../api/resumeApi";

function StudentResume() {
  const [resume, setResume] = useState(null);

  const [status, setStatus] = useState("");

  const [file, setFile] = useState(null);

  const [uploadDialog, setUploadDialog] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, []);

const loadData = async () => {
  try {
    setLoading(true);

    const [resumeRes, statusRes] = await Promise.all([
      getResume().catch(() => null),
      getResumeStatus(),
    ]);

    if (resumeRes) {
      setResume(resumeRes.data);
    }

    setStatus(statusRes.data);
  } catch (error) {
    console.error(error);
  } finally {
    setLoading(false);
  }
};

  const handleUpload = async () => {
    if (!file) return;

    try {
      await uploadResume(file);

      alert("Resume Uploaded");

      loadData();
    } catch (error) {
      console.error(error);
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
        Resume
      </Typography>

      <Paper sx={{ p: 3 }}>
        <Stack spacing={3}>
          <Typography>Status: {status}</Typography>

          {resume && (
            <>
              <Typography>File: {resume.fileName}</Typography>

              <Typography>Uploaded: {resume.uploadDate}</Typography>
            </>
          )}

          <input
            type="file"
            accept=".pdf,.doc,.docx"
            onChange={(e) => setFile(e.target.files[0])}
          />

          <Button variant="contained" onClick={() => setUploadDialog(true)}>
            Upload Resume
          </Button>
        </Stack>
      </Paper>
      <ConfirmDialog
        open={uploadDialog}
        title="Upload Resume"
        message="Do you want to upload this resume?"
        onCancel={() => setUploadDialog(false)}
        onConfirm={() => {
          setUploadDialog(false);
          handleUpload();
        }}
      />
    </StudentLayout>
  );
}

export default StudentResume;
