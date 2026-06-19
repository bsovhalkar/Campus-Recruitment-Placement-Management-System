import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Paper,
} from "@mui/material";

function UploadScoresDialog({ open, onClose, students, setStudents, onSave }) {
  const handleScoreChange = (index, value) => {
    const updated = [...students];

    updated[index].score = value === "" ? "" : Number(value);

    setStudents(updated);
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
      <DialogTitle>Upload Scores</DialogTitle>

      <DialogContent>
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Student</TableCell>

                <TableCell>Score</TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {students.map((student, index) => (
                <TableRow key={student.studentId}>
                  <TableCell>{student.studentName}</TableCell>

                  <TableCell>
                    <TextField
                      type="number"
                      size="small"
                      value={student.score}
                      onChange={(e) => handleScoreChange(index, e.target.value)}
                      inputProps={{
                        min: 0,
                        max: 100,
                      }}
                    />
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </DialogContent>

      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>

        <Button variant="contained" onClick={onSave}>
          Save Scores
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default UploadScoresDialog;
