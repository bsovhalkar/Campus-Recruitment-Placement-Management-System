import { Box, Toolbar } from "@mui/material";
import { useState } from "react";
import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import DashboardIcon from "@mui/icons-material/Dashboard";
import PersonIcon from "@mui/icons-material/Person";
import WorkIcon from "@mui/icons-material/Work";
import DescriptionIcon from "@mui/icons-material/Description";
import AssessmentIcon from "@mui/icons-material/Assessment";
import UploadFileIcon from "@mui/icons-material/UploadFile";
import PsychologyIcon from "@mui/icons-material/Psychology";


function StudentLayout({ children }) {
  const [mobileOpen, setMobileOpen] = useState(false);
  const menuItems = [
    {
      label: "Dashboard",
      path: "/student/dashboard",
      icon: <DashboardIcon />,
    },
    {
      label: "Profile",
      path: "/student/profile",
      icon: <PersonIcon />,
    },
    {
      label: "Jobs",
      path: "/student/jobs",
      icon: <WorkIcon />,
    },
    {
      label: "Applications",
      path: "/student/applications",
      icon: <DescriptionIcon />,
    },
    {
      label: "Assessments",
      path: "/student/assessments",
      icon: <AssessmentIcon />,
    },
    {
      label: "Resume",
      path: "/student/resume",
      icon: <UploadFileIcon />,
    },
    {
      label: "Skills",
      path: "/student/skills",
      icon: <PsychologyIcon />,
    },
  ];

	return (
    <Box
      sx={{
        display: "flex",
      }}
    >
      <Navbar onMenuClick={() => setMobileOpen(true)}
      sx={{
        ml:100

      }}
      />

      <Sidebar
        menuItems={menuItems}
        mobileOpen={mobileOpen}
        onClose={() => setMobileOpen(false)}
      />

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
        }}
      >
        <Toolbar />

        {children}
      </Box>
    </Box>
  );
}

export default StudentLayout;
