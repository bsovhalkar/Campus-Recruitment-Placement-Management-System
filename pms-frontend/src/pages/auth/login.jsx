import { useState } from "react";
import {
  Box,
  Button,
  Container,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import { toast } from "react-toastify";

import { loginUser } from "../../api/authApi";
import { saveUser } from "../../utils/auth";
import { haveStudentProfile } from "../../api/studentProfileApi";
import { useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await loginUser(form);

      if (response.success) {
        saveUser(response.data);

        if (response.data.role === "STUDENT") {
          const profileResponse = await haveStudentProfile();
          if (profileResponse.data) {
            navigate("/student/dashboard");
          } else {
            navigate("/student/profile/create");
          }

        } 
        else {
          toast.error("Please use Admin Login");
          return;
        }
      }
    } catch (error) {
      alert(error?.response?.data?.message || "Login Failed");
    }
  };

  return (
    <Container maxWidth="sm">
      <Paper
        elevation={3}
        sx={{
          mt: 10,
          p: 4,
        }}
      >
        <Typography variant="h4" gutterBottom align="center">
          Campus Recruitment & Placement System Student Login
        </Typography>

        <Box component="form" onSubmit={handleLogin}>
          <TextField
            fullWidth
            margin="normal"
            label="Email"
            name="email"
            value={form.email}
            onChange={handleChange}
          />

          <TextField
            fullWidth
            margin="normal"
            label="Password"
            type="password"
            name="password"
            value={form.password}
            onChange={handleChange}
          />

          <Button fullWidth type="submit" variant="contained" sx={{ mt: 2 }}>
            Login
          </Button>
        </Box>
          <Button fullWidth onClick={() => navigate("/admin")}>
            Admin Login
          </Button>
      </Paper>
    </Container>
  );
}

export default Login;
