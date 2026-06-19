import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { haveStudentProfile } from "../api/studentProfileApi";

const StudentProfileGuard = ({ children }) => {
  const [loading, setLoading] = useState(true);
  const [hasProfile, setHasProfile] = useState(false);

  useEffect(() => {
    checkProfile();
  }, []);

  const checkProfile = async () => {
    try {
      const response = await haveStudentProfile();
      setHasProfile(response.data);
    } catch (error) {
      setHasProfile(false);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!hasProfile) {
    return <Navigate to="/student/profile/create" replace />;
  }

  return children;
};

export default StudentProfileGuard;
