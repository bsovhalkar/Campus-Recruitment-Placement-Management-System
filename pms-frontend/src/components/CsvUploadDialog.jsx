import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
} from "@mui/material";

import { useState } from "react";

function CsvUploadDialog({ open, onClose, onUpload }) {
  const [file, setFile] = useState(null);

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Upload CSV</DialogTitle>

      <DialogContent>
        <input
          type="file"
          accept=".csv"
          onChange={(e) => setFile(e.target.files[0])}
        />
      </DialogContent>

      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>

        <Button variant="contained" onClick={() => onUpload(file)}>
          Upload
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default CsvUploadDialog;
