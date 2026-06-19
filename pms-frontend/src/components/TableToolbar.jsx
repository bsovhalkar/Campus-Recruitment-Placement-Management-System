import { TextField, Box } from "@mui/material";

function TableToolbar({ search, setSearch, placeholder }) {
  return (
    <Box mb={2}>
      <TextField
        fullWidth
        size="small"
        label={placeholder}
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />
    </Box>
  );
}

export default TableToolbar;
