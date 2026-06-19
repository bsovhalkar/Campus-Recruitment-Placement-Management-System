// import PageLoader from "../../components/PageLoader";
// import { useEffect, useState } from "react";

// import { Typography, TextField, Button, Stack, Paper } from "@mui/material";

// import StudentLayout from "../../layouts/StudentLayout";

// import AppSnackbar from "../../components/AppSnackbar";
// import useSnackbar from "../../hooks/useSnackbar";
// import ConfirmDialog from "../../components/ConfirmDialog";

// import { getMyProfile, updateMyProfile } from "../../api/studentProfileApi";

// function StudentProfile() {
//   const [profile, setProfile] = useState({});
//   const [originalProfile, setOriginalProfile] = useState({});
//   const [loading, setLoading] = useState(true);
//   const [isEditing, setIsEditing] = useState(false);
//   const [confirmOpen, setConfirmOpen] = useState(false);

//   const { snackbar, showSnackbar, closeSnackbar } = useSnackbar();

//   useEffect(() => {
//     loadProfile();
//   }, []);

//   const loadProfile = async () => {
//     try {
//       setLoading(true);

//       const response = await getMyProfile();

//       setProfile(response.data);
//       setOriginalProfile(response.data);
//     } catch (error) {
//       console.error(error);
//       showSnackbar("Failed to load profile", "error");
//     } finally {
//       setLoading(false);
//     }
//   };

//   const updateField = (field, value) => {
//     setProfile((prev) => ({
//       ...prev,
//       [field]: value,
//     }));
//   };

//   const handleSave = async () => {
//     try {
//       await updateMyProfile({
//         phone: profile.phone,
//         department: profile.department,
//         cgpa: profile.cgpa,
//         graduationYear: profile.graduationYear,
//         gender: profile.gender,
//         address: profile.address,
//         activeBacklogs: profile.activeBacklogs,
//       });

//       showSnackbar("Profile Updated Successfully", "success");

//       setOriginalProfile(profile);
//       setIsEditing(false);

//       loadProfile();
//     } catch (error) {
//       console.error(error);
//       showSnackbar("Failed to update profile", "error");
//     }
//   };

//   const handleCancel = () => {
//     setProfile(originalProfile);
//     setIsEditing(false);
//   };

//   if (loading) {
//     return (
//       <StudentLayout>
//         <PageLoader />
//       </StudentLayout>
//     );
//   }

//   return (
//     <>
//       <StudentLayout>
//         <Typography variant="h4" mb={3}>
//           My Profile
//         </Typography>

//         <Paper
//           sx={{
//             p: 3,
//             maxWidth: 700,
//           }}
//         >
//           <Stack spacing={2}>
//             <TextField label="Name" value={profile.name || ""} disabled />

//             <TextField label="Email" value={profile.email || ""} disabled />

//             <TextField
//               label="Phone"
//               value={profile.phone || ""}
//               disabled={!isEditing}
//               onChange={(e) => updateField("phone", e.target.value)}
//             />

//             <TextField
//               label="Department"
//               value={profile.department || ""}
//               disabled={!isEditing}
//               onChange={(e) => updateField("department", e.target.value)}
//             />

//             <TextField
//               label="CGPA"
//               value={profile.cgpa || ""}
//               disabled={!isEditing}
//               onChange={(e) => updateField("cgpa", e.target.value)}
//             />

//             <TextField
//               label="Graduation Year"
//               value={profile.graduationYear || ""}
//               disabled={!isEditing}
//               onChange={(e) => updateField("graduationYear", e.target.value)}
//             />

//             <TextField
//               label="Gender"
//               value={profile.gender || ""}
//               disabled={!isEditing}
//               onChange={(e) => updateField("gender", e.target.value)}
//             />

//             <TextField
//               label="Address"
//               value={profile.address || ""}
//               disabled={!isEditing}
//               onChange={(e) => updateField("address", e.target.value)}
//             />

//             <TextField
//               label="Active Backlogs"
//               value={profile.activeBacklogs || 0}
//               disabled={!isEditing}
//               onChange={(e) => updateField("activeBacklogs", e.target.value)}
//             />

//             <TextField
//               label="Placement Status"
//               value={profile.placementStatus || ""}
//               disabled
//             />

//             {!isEditing ? (
//               <Button variant="contained" onClick={() => setIsEditing(true)}>
//                 Edit Profile
//               </Button>
//             ) : (
//               <Stack direction="row" spacing={2}>
//                 <Button
//                   variant="contained"
//                   onClick={() => setConfirmOpen(true)}
//                 >
//                   Save Profile
//                 </Button>

//                 <Button variant="outlined" color="error" onClick={handleCancel}>
//                   Cancel
//                 </Button>
//               </Stack>
//             )}
//           </Stack>
//         </Paper>
//       </StudentLayout>

//       <AppSnackbar
//         open={snackbar.open}
//         message={snackbar.message}
//         severity={snackbar.severity}
//         onClose={closeSnackbar}
//       />

//       <ConfirmDialog
//         open={confirmOpen}
//         title="Update Profile"
//         message="Are you sure you want to save profile changes?"
//         onCancel={() => setConfirmOpen(false)}
//         onConfirm={() => {
//           setConfirmOpen(false);
//           handleSave();
//         }}
//       />
//     </>
//   );
// }

// export default StudentProfile;

import { useEffect, useState } from "react";

import {
  Typography,
  Button,
  Grid,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Stack,
} from "@mui/material";

import StudentLayout from "../../layouts/StudentLayout";

import PageLoader from "../../components/PageLoader";
import StudentInfoCard from "../../components/StudentInfoCard";

import AppSnackbar from "../../components/AppSnackbar";
import useSnackbar from "../../hooks/useSnackbar";

import ConfirmDialog from "../../components/ConfirmDialog";

import { getMyProfile, updateMyProfile } from "../../api/studentProfileApi";

function StudentProfile() {
  const [profile, setProfile] = useState({});
  const [editProfile, setEditProfile] = useState({});

  const [loading, setLoading] = useState(true);

  const [editOpen, setEditOpen] = useState(false);

  const [confirmOpen, setConfirmOpen] = useState(false);

  const { snackbar, showSnackbar, closeSnackbar } = useSnackbar();

  useEffect(() => {
    loadProfile();
  }, []);

  const loadProfile = async () => {
    try {
      setLoading(true);

      const response = await getMyProfile();

      setProfile(response.data);
      setEditProfile(response.data);
    } catch (error) {
      console.error(error);
      showSnackbar("Failed to load profile", "error");
    } finally {
      setLoading(false);
    }
  };

  const updateField = (field, value) => {
    setEditProfile({
      ...editProfile,
      [field]: value,
    });
  };

  const handleSave = async () => {
    try {
      await updateMyProfile({
        phone: editProfile.phone,
        department: editProfile.department,
        cgpa: editProfile.cgpa,
        graduationYear: editProfile.graduationYear,
        gender: editProfile.gender,
        address: editProfile.address,
        activeBacklogs: editProfile.activeBacklogs,
      });

      showSnackbar("Profile Updated Successfully", "success");

      setEditOpen(false);

      loadProfile();
    } catch (error) {
      console.error(error);

      showSnackbar("Failed to update profile", "error");
    }
  };

  if (loading) {
    return (
      <StudentLayout>
        <PageLoader />
      </StudentLayout>
    );
  }

  return (
    <>
      <StudentLayout>
        <Stack
          direction="row"
          justifycontent="space-between"
          alignitems="center"
          mb={3}
        >
          <Typography variant="h4">My Profile</Typography>

          <Button variant="contained" onClick={() => setEditOpen(true)} sx={{ml:5}}>
            Edit Profile
          </Button>
        </Stack>

        <Grid container spacing={2}>
          <Grid item xs={12} md={6}>
            <StudentInfoCard title="Name" value={profile.name} />
          </Grid>

          <Grid item xs={12} md={6}>
            <StudentInfoCard title="Email" value={profile.email} />
          </Grid>

          <Grid item xs={12} md={6}>
            <StudentInfoCard title="Phone" value={profile.phone} />
          </Grid>

          <Grid item xs={12} md={6}>
            <StudentInfoCard title="Department" value={profile.department} />
          </Grid>

          <Grid item xs={12} md={6}>
            <StudentInfoCard title="CGPA" value={profile.cgpa} />
          </Grid>

          <Grid item xs={12} md={6}>
            <StudentInfoCard title="Graduation Year" value={profile.graduationYear} />
          </Grid>

          <Grid item xs={12} md={6}>
            <StudentInfoCard title="Gender" value={profile.gender} />
          </Grid>

          <Grid item xs={12} md={6}>
            <StudentInfoCard title="Address" value={profile.address} />
          </Grid>

          <Grid item xs={12} md={6}>
            <StudentInfoCard title="Active Backlogs" value={profile.activeBacklogs} />
          </Grid>

          <Grid item xs={12} md={6}>
            <StudentInfoCard
              title="Placement Status"
              value={profile.placementStatus}
            />
          </Grid>
        </Grid>
      </StudentLayout>

      <Dialog
        open={editOpen}
        onClose={() => setEditOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>Edit Profile</DialogTitle>

        <DialogContent>
          <Stack spacing={2} mt={1}>
            <TextField
              label="Phone"
              value={editProfile.phone || ""}
              onChange={(e) => updateField("phone", e.target.value)}
            />

            <TextField
              label="Department"
              value={editProfile.department || ""}
              onChange={(e) => updateField("department", e.target.value)}
            />

            <TextField
              label="CGPA"
              value={editProfile.cgpa || ""}
              onChange={(e) => updateField("cgpa", e.target.value)}
            />

            <TextField
              label="Graduation Year"
              value={editProfile.graduationYear || ""}
              onChange={(e) => updateField("graduationYear", e.target.value)}
            />

            <TextField
              label="Gender"
              value={editProfile.gender || ""}
              onChange={(e) => updateField("gender", e.target.value)}
            />

            <TextField
              label="Address"
              value={editProfile.address || ""}
              onChange={(e) => updateField("address", e.target.value)}
            />

            <TextField
              label="Active Backlogs"
              value={editProfile.activeBacklogs || 0}
              onChange={(e) => updateField("activeBacklogs", e.target.value)}
            />
          </Stack>
        </DialogContent>

        <DialogActions>
          <Button onClick={() => setEditOpen(false)}>Cancel</Button>

          <Button variant="contained" onClick={() => setConfirmOpen(true)}>
            Save
          </Button>
        </DialogActions>
      </Dialog>

      <ConfirmDialog
        open={confirmOpen}
        title="Update Profile"
        message="Are you sure you want to save profile changes?"
        onCancel={() => setConfirmOpen(false)}
        onConfirm={() => {
          setConfirmOpen(false);
          handleSave();
        }}
      />

      <AppSnackbar
        open={snackbar.open}
        message={snackbar.message}
        severity={snackbar.severity}
        onClose={closeSnackbar}
      />
    </>
  );
}

export default StudentProfile;