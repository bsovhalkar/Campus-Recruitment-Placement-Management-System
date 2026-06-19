import { useState } from "react";
import { Box, Toolbar } from "@mui/material";

import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";

import DashboardIcon from "@mui/icons-material/Dashboard";
import PeopleIcon from "@mui/icons-material/People";
import BusinessIcon from "@mui/icons-material/Business";
import WorkIcon from "@mui/icons-material/Work";
import DescriptionIcon from "@mui/icons-material/Description";
import AssessmentIcon from "@mui/icons-material/Assessment";
// import AnalyticsIcon from "@mui/icons-material/Analytics";
import FormatListNumberedIcon from "@mui/icons-material/FormatListNumbered";

function AdminLayout({ children }) {
  const [mobileOpen, setMobileOpen] = useState(false);
const menuItems = [
	{
		label: "Dashboard",
		path: "/admin/dashboard",
		icon: <DashboardIcon />,
	},
	{
		label: "Students",
		path: "/admin/students",
		icon: <PeopleIcon />,
	},
	{
		label: "Companies",
		path: "/admin/companies",
		icon: <BusinessIcon />,
	},
	{
		label: "Jobs",
		path: "/admin/jobs",
		icon: <WorkIcon />,
	},
	{
		label: "Applications",
		path: "/admin/applications",
		icon: <DescriptionIcon />,
	},
	{
		label: "Shortlists",
		path: "/admin/shortlists",
		icon: <FormatListNumberedIcon />,
	},
	{
		label: "Assessments",
		path: "/admin/assessments",
		icon: <AssessmentIcon />,
	},
];

  return (
    <Box sx={{ display: "flex" }}>
      <Navbar onMenuClick={() => setMobileOpen(true)} />

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

export default AdminLayout;
