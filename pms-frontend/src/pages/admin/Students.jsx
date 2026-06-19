import { useEffect, useState } from "react";

import { Typography, Button} from "@mui/material";

import { Card, CardContent} from "@mui/material";

import AdminLayout from "../../layouts/AdminLayout";
import DataTable from "../../components/DataTable";

import StudentDialog from "../../components/StudentDialog";

import { getStudents, updateStudent } from "../../api/studentApi";

function Students() {
	const [students, setStudents] = useState([]);

	const [open, setOpen] = useState(false);

	const [student, setStudent] = useState({});

	useEffect(() => {
		loadStudents();
	}, []);

	const loadStudents = async () => {
		const response = await getStudents();

		setStudents(response.data);
	};

	const handleEdit = (row) => {
		setStudent(row);

		setOpen(true);
	};

	const handleSave = async () => {
		await updateStudent(student.studentId, {
			phone: student.phone,
			department: student.department,
			cgpa: student.cgpa,
			graduationYear: student.graduationYear,
			address: student.address,
			activeBacklogs: student.activeBacklogs,
		});

		setOpen(false);

		loadStudents();
	};

const rows = students.map((student) => ({
	...student,
	id: student.studentId,

	name: student.user?.name,

	email: student.user?.email,

	skillCount: student.skills?.length || 0,

	resumeUploaded: student.resume ? "Yes" : "No",
}));

	const columns = [
		{
		field: "studentId",
		headerName: "ID",
		width: 80,
		},
		{
		field: "name",
		headerName: "Name",
		width: 200,
		},
		{
		field: "email",
		headerName: "Email",
		width: 250,
		},
		{
		field: "department",
		headerName: "Department",
		width: 180,
		},
		{
		field: "cgpa",
		headerName: "CGPA",
		width: 100,
		},
		{
		field: "activeBacklogs",
		headerName: "Backlogs",
		width: 100,
		},
		{
		field: "placementStatus",
		headerName: "Placement",
		width: 150,
		},
		{
		field: "skillCount",
		headerName: "Skills",
		width: 100,
		},
		{
		field: "resumeUploaded",
		headerName: "Resume",
		width: 120,
		},
		{
		field: "actions",
		headerName: "Actions",
		width: 150,

		renderCell: (params) => (
        <Button
				variant="outlined"
				size="small"
        	onClick={() => handleEdit(params.row)}
        >
        	View
        </Button>
    	),
    },
	];

	return (
    <AdminLayout>
      <Typography variant="h4" mb={3}>
        Students
      </Typography>

      <Card>
        <CardContent>
          <DataTable rows={rows} columns={columns} />
        </CardContent>
      </Card>

      <StudentDialog
        open={open}
        onClose={() => setOpen(false)}
        student={student}
        setStudent={setStudent}
        onSave={handleSave}
      />
    </AdminLayout>
  );
}

export default Students;
