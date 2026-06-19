import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Grid,
  MenuItem,
} from "@mui/material";

import { createStudentProfile } from "../../api/studentProfileApi";

const CreateProfile = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    phone: "",
    department: "",
    cgpa: "",
    graduationYear: "",
    gender: "",
    address: "",
    activeBacklogs: 0,
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await createStudentProfile({
        ...formData,
        cgpa: Number(formData.cgpa),
        graduationYear: Number(formData.graduationYear),
        activeBacklogs: Number(formData.activeBacklogs),
      });

      navigate("/student/dashboard");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Container maxWidth="md" sx={{ mt: 5 }}>
      <Paper sx={{ p: 4 }}>
        <Typography variant="h4" gutterBottom>
          Create Student Profile
        </Typography>

        <form onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
              <TextField
                fullWidth
                label="Phone Number"
                name="phone"
                value={formData.phone}
                onChange={handleChange}
                required
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                fullWidth
                label="Department"
                name="department"
                value={formData.department}
                onChange={handleChange}
                required
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                fullWidth
                type="number"
                label="CGPA"
                name="cgpa"
                inputProps={{
                  min: 0,
                  max: 10,
                  step: 0.01,
                }}
                value={formData.cgpa}
                onChange={handleChange}
                required
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                fullWidth
                type="number"
                label="Graduation Year"
                name="graduationYear"
                value={formData.graduationYear}
                onChange={handleChange}
                required
              />
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                select
                fullWidth
                label="Gender"
                name="gender"
                value={formData.gender}
                onChange={handleChange}
                required
              >
                <MenuItem value="Male">Male</MenuItem>

                <MenuItem value="Female">Female</MenuItem>

                <MenuItem value="Other">Other</MenuItem>
              </TextField>
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                fullWidth
                type="number"
                label="Active Backlogs"
                name="activeBacklogs"
                value={formData.activeBacklogs}
                onChange={handleChange}
                required
              />
            </Grid>

            <Grid item xs={12}>
              <TextField
                fullWidth
                multiline
                rows={3}
                label="Address"
                name="address"
                value={formData.address}
                onChange={handleChange}
                required
              />
            </Grid>

            <Grid item xs={12}>
              <Button type="submit" variant="contained" fullWidth size="large">
                Create Profile
              </Button>
            </Grid>
          </Grid>
        </form>
      </Paper>
    </Container>
  );
};

export default CreateProfile;
