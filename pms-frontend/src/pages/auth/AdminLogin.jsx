import { toast } from "react-toastify";
import { useState } from "react";
import {
  Box,
  Button,
  Container,
  Paper,
  TextField,
  Typography,
} from "@mui/material";

import { loginUser } from "../../api/authApi";
import { saveUser } from "../../utils/auth";
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
        if (response.data.role === "ADMIN") {
          navigate("/admin/dashboard");
        } else {
          toast.error("Please use Student Login");
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
          Campus Recruitment & Placement System Admin Login
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
          <Button fullWidth onClick={() => navigate("/")}>
            Student Login
          </Button>
        </Box>
      </Paper>
    </Container>
  );
}

export default Login;
