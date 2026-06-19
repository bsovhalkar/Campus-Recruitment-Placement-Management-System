import { Box, Typography } from "@mui/material";

function EmptyState({ message = "No Data Found" }) {
  return (
    <Box
      sx={{
        py: 8,
        textAlign: "center",
      }}
    >
      <Typography variant="h6">{message}</Typography>
    </Box>
  );
}

export default EmptyState;
