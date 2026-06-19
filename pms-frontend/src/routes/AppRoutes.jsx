import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "../pages/auth/Login";

import AdminDashboard from "../pages/admin/Dashboard";
import Students from "../pages/admin/Students";
import Companies from "../pages/admin/Companies";
import Jobs from "../pages/admin/Jobs";
import Applications from "../pages/admin/Applications";
import Shortlists from "../pages/admin/Shortlists";
import Assessments from "../pages/admin/Assessments";
import Analytics from "../pages/admin/Analytics";
import StudentDashboard from "../pages/student/Dashboard";
import StudentApplications from "../pages/student/Applications";
import StudentAssessments from "../pages/student/Assessments";
import StudentJobs from "../pages/student/Jobs";
import StudentProfile from "../pages/student/Profile";
import StudentResume from "../pages/student/Resume";
import StudentSkills from "../pages/student/Skills";
import CreateProfile from "../pages/student/CreateProfile";
import StudentProfileGuard from "../components/StudentProfileGuard";
import AdminLogin from "../pages/auth/AdminLogin";

import ProtectedRoute from "./ProtectedRoute";

function AppRoutes() {
	return (
    <BrowserRouter>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<Login />} />

        <Route path="/admin" element={<AdminLogin />} />

        {/* Admin Routes */}
        <Route
          path="/admin/dashboard"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/students"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <Students />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/companies"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <Companies />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/jobs"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <Jobs />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/applications"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <Applications />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/shortlists"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <Shortlists />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/assessments"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <Assessments />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/analytics"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <Analytics />
            </ProtectedRoute>
          }
        />

        {/* Student Routes */}
        <Route
          path="/student/dashboard"
          element={
            <ProtectedRoute allowedRole="STUDENT">
              <StudentProfileGuard>
                <StudentDashboard />
              </StudentProfileGuard>
            </ProtectedRoute>
          }
        />
        {/* <Route
          path="/student/dashboard"
          element={
            <ProtectedRoute allowedRole="STUDENT">
              <StudentProfileGuard>
              <StudentDashboard />
              </StudentProfileGuard>
            </ProtectedRoute>
          }
        /> */}

        <Route
          path="/student/profile"
          element={
            <ProtectedRoute allowedRole="STUDENT">
              <StudentProfileGuard>
                <StudentProfile />
              </StudentProfileGuard>
            </ProtectedRoute>
          }
        />
        <Route path="/student/profile/create" element={<CreateProfile />} />
        <Route
          path="/student/jobs"
          element={
            <ProtectedRoute allowedRole="STUDENT">
              <StudentProfileGuard>
                <StudentJobs />
              </StudentProfileGuard>
            </ProtectedRoute>
          }
        />

        <Route
          path="/student/applications"
          element={
            <ProtectedRoute allowedRole="STUDENT">
              <StudentProfileGuard>
                <StudentApplications />
              </StudentProfileGuard>
            </ProtectedRoute>
          }
        />

        <Route
          path="/student/assessments"
          element={
            <ProtectedRoute allowedRole="STUDENT">
              <StudentProfileGuard>
                <StudentAssessments />
              </StudentProfileGuard>
            </ProtectedRoute>
          }
        />

        <Route
          path="/student/resume"
          element={
            <ProtectedRoute allowedRole="STUDENT">
              <StudentProfileGuard>
                <StudentResume />
              </StudentProfileGuard>
            </ProtectedRoute>
          }
        />

        <Route
          path="/student/skills"
          element={
            <ProtectedRoute allowedRole="STUDENT">
              <StudentProfileGuard>
                <StudentSkills />
              </StudentProfileGuard>
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;
