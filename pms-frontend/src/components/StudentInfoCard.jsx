import { Card, CardContent, Typography } from "@mui/material";


function StudentInfoCard({ title, value, icon }) {
  return (
    <Card
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
        <Typography color="text.secondary">{title}</Typography>

        <Typography
          variant="h6"
          sx={{
            my: 1,
          }}
        >
          {value}
        </Typography>

        {icon}
      </CardContent>
    </Card>
  );
}

export default StudentInfoCard;