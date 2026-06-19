import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  IconButton,
  Avatar,
  Box,
  Chip,
} from "@mui/material";
import { useState } from "react";

import ConfirmDialog from "./ConfirmDialog";
import DarkModeIcon from "@mui/icons-material/DarkMode";
import LightModeIcon from "@mui/icons-material/LightMode";

import { useThemeMode } from "../context/ThemeContext";
import MenuIcon from "@mui/icons-material/Menu";


import { useNavigate } from "react-router-dom";


function Navbar({ onMenuClick }) {
  const navigate = useNavigate();
  const name = localStorage.getItem("name");

  const role = localStorage.getItem("role");
  const { mode, setMode } = useThemeMode();

  const [logoutOpen, setLogoutOpen] = useState(false);

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <AppBar
      position="fixed"
      sx={{
        zIndex: (theme) => theme.zIndex.drawer + 1,
      }}
    >
      <Toolbar>
        <IconButton
          color="inherit"
          edge="start"
          onClick={onMenuClick}
          sx={{
            mr: 2,
            display: {
              xs: "block",
              md: "none",
            },
          }}
        >
          <MenuIcon />
        </IconButton>

        <Typography
          variant="h6"
          sx={{
            flexGrow: 1,
            fontWeight: 600,
          }}
        >
          Placement Management System
        </Typography>

        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            gap: 2,
          }}
        >
          <IconButton
            color="inherit"
            onClick={() => setMode(mode === "light" ? "dark" : "light")}
          >
            {mode === "light" ? <DarkModeIcon /> : <LightModeIcon />}
          </IconButton>
          <Avatar>{name?.charAt(0)}</Avatar>

          <Box>
            <Typography variant="body2">{name}</Typography>

            <Chip label={role} size="small" color="secondary" />
          </Box>

          <Button color="inherit" onClick={() => setLogoutOpen(true)}>
            Logout
          </Button>
        </Box>
      </Toolbar>
      <ConfirmDialog
        open={logoutOpen}
        title="Logout"
        message="Are you sure you want to logout?"
        onCancel={() => setLogoutOpen(false)}
        onConfirm={logout}
      />
    </AppBar>
  );
}

export default Navbar;
