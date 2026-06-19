import {
  Drawer,
  List,
  ListItemButton,
  ListItemText,
  Toolbar,
  Typography,
  Box,
  Divider,
} from "@mui/material";

import { useNavigate, useLocation } from "react-router-dom";

const drawerWidth = 260;

function Sidebar({ menuItems, mobileOpen, onClose }) {
  const navigate = useNavigate();
  const location = useLocation();

  const drawerContent = (
    <>
      <Toolbar />
      <Box
        sx={{
          textAlign: "center",
          py: 2,
        }}
      >
        <Typography variant="h5" fontWeight="bold">
          🎓 PMS
        </Typography>

        <Typography variant="caption" color="text.secondary">
          Placement Portal
        </Typography>
      </Box>

      <Divider />
      <List sx={{ mt: 1 }}>
        {menuItems.map((item) => (
          <ListItemButton
            key={item.label}
            selected={location.pathname.startsWith(item.path)}
            onClick={() => {
              navigate(item.path);

              if (onClose) onClose();
            }}
            sx={{
              mx: 1,
              my: 0.5,
              borderRadius: 2,
              transition: "0.2s",

              "&:hover": {
                transform: "translateX(4px)",
              },

              "&.Mui-selected": {
                backgroundColor: "primary.main",
                color: "white",

                "&:hover": {
                  backgroundColor: "primary.dark",
                },

                "& .MuiSvgIcon-root": {
                  color: "white",
                },
              },
            }}
          >
            {item.icon}

            <ListItemText sx={{ ml: 2 }} primary={item.label} />
          </ListItemButton>
        ))}
      </List>
    </>
  );

  return (
    <>
      <Drawer
        variant="temporary"
        open={mobileOpen}
        onClose={onClose}
        sx={{
          display: {
            xs: "block",
            md: "none",
          },
          "& .MuiDrawer-paper": {
            width: drawerWidth,
          },
        }}
      >
        {drawerContent}
      </Drawer>

      <Drawer
        variant="permanent"
        sx={{
          display: {
            xs: "none",
            md: "block",
          },
          width: drawerWidth,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: drawerWidth,
            boxSizing: "border-box",
          },
        }}
      >
        {drawerContent}
      </Drawer>
    </>
  );
}

export default Sidebar;
